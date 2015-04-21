package gatech.course.optimizer.repo;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.engine.EngineInterface;
import gatech.course.optimizer.model.*;
import gatech.course.optimizer.utils.JSONObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 4/21/15.
 */
@Component
public class ExecutionTrigger {


    @Autowired
    public EngineInterface engineInterface;

    @Autowired
    public CourseRepo courseRepo;

    @Autowired
    public StudentRepo studentRepo;

    @Autowired
    public FacultyRepo facultyRepo;

    @Autowired StudentDetailsRepo studentDetailsRepo;


    public static Logger logger = LoggerFactory.getLogger(ExecutionTrigger.class);

    public ScheduleInput prepareScheduleInput(Set<CourseOffering> requiredOfferings, Semester semester) {

        logger.info("Gathering information for the execution engine");
        ScheduleInput scheduleInput = new ScheduleInput();
        scheduleInput.setMaxCourseCapacity(200);
        scheduleInput.setAllowedClassesPerSemester(2);
        scheduleInput.setCoursesThatCanBeOffered(courseRepo.getCourseSet());

        List<Student> studentList = studentRepo.getAllStudents();
        Set<StudentDTO> studentDTOSet = new HashSet<StudentDTO>();

        for(Student student : studentList){
            studentDTOSet.add(studentDetailsRepo.getStudentDetails(student.getStudentId()));
        }

        scheduleInput.setStudents(studentDTOSet);
        scheduleInput.setRequiredOfferings(requiredOfferings);
        scheduleInput.setSemesterToSchedule(semester);
        scheduleInput.setTeacherAssistants(facultyRepo.getTASet());
        scheduleInput.setProfessors(facultyRepo.getProfessorsSet());

        logger.info("Schedule Input Info :");
        logger.info("Number of professors : "+scheduleInput.getProfessors().size());
        logger.info("Number of TAs : " + scheduleInput.getTeacherAssistants().size());
        logger.info("Number of students : " + scheduleInput.getStudents().size());
        logger.info("Number of courses that can be offered : "+scheduleInput.getCoursesThatCanBeOffered().size());
        logger.info("Allowed classes per semester : "+scheduleInput.getAllowedClassesPerSemester());
        logger.info("Max course capacity : "+scheduleInput.getMaxCourseCapacity());
        logger.info("Semester to schedule : "+ JSONObjectMapper.jsonify(scheduleInput.getSemesterToSchedule()));
        logger.info("Required offerings size : " + scheduleInput.getRequiredOfferings().size());
        logger.info("Number of courses required to graduate : " + scheduleInput.getNumberOfCoursesRequiredToGraduate());



        //logger.info("Schedule Input : \n"+JSONObjectMapper.jsonify(scheduleInput));
        //scheduleInput.setAvailableSpecializations();but


        /*
        testInput.setAllowedClassesPerSemester( 1 );
		testInput.setAvailableSpecializations( new HashSet<Specialization>() { { this.add(specialization1); } } );
		testInput.setCoursesThatCanBeOffered( new HashSet<Course>(){ { this.add( cs6010 ); this.add(cs6290); this.add( cs6300 ); this.add( cs6310 ); } } );
		testInput.setProfessors( new HashSet<Faculty>() { { this.add(prof1); this.add( prof2 ); } } );
		testInput.setRequiredOfferings( new HashSet<CourseOffering>(){ { this.add(offering); } } );
		testInput.setSemesterToSchedule( startSemester );
		testInput.setStudents( new HashSet<StudentDTO>() { { this.add(student1); this.add(student2); } } );
		testInput.setTeacherAssistants( new HashSet<Faculty>() { { this.add(ta1); this.add( ta2 ); } } );
		testInput.setMaxCourseCapacity( 200 );
		testInput.setNumberOfCoursesRequiredToGraduate( 3 );
         */

        return scheduleInput;

    }

    @Transactional
    public void createScheduleSolution(){
        logger.info("Creating schedule solution");
        Semester semester = new Semester("2014", "FALL");
        ScheduleInput scheduleInput = prepareScheduleInput(new HashSet<CourseOffering>(),semester);
        ScheduleSolution scheduleSolution =  engineInterface.createScheduleSolution(scheduleInput);
        logger.info("Done running course optimization engine, solution : \n"+ JSONObjectMapper.jsonify(scheduleSolution));
        // TODO: persist the solution
    }

    
}
