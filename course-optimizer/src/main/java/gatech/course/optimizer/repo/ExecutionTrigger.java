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

import java.util.Date;
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

    @Autowired
    public SemesterRepo semesterRepo;

    @Autowired
    public StudentDetailsRepo studentDetailsRepo;

    @Autowired
    public CourseOfferingRepo courseOfferingRepo;

    @Autowired
    public ScheduleSolutionRepo scheduleSolutionRepo;

    public static Logger logger = LoggerFactory.getLogger(ExecutionTrigger.class);

    public ScheduleInput prepareScheduleInput(Set<CourseOffering> requiredOfferings, Semester semester) {

        logger.info("Gathering information for the execution engine");
        ScheduleInput scheduleInput = new ScheduleInput();
        scheduleInput.setMaxCourseCapacity(200);
        scheduleInput.setAllowedClassesPerSemester(3);
        scheduleInput.setCoursesThatCanBeOffered(courseRepo.getCourseSet());

        List<Student> studentList = studentRepo.getAllStudents();
        Set<StudentDTO> studentDTOSet = new HashSet<StudentDTO>();

        for(Student student : studentList){
            studentDTOSet.add(studentDetailsRepo.getStudentDetails(student.getStudentId()));
        }

        scheduleInput.setStudents(studentDTOSet);
        scheduleInput.setRequiredOfferings(requiredOfferings);
        scheduleInput.setSemesterToSchedule(semester);


        Set<Faculty> tasWithNullCompetencies = new HashSet<Faculty>();
        for(Faculty faculty : facultyRepo.getTASet()) {
            faculty.setCompetencies(null);
            faculty.setAvailability(null);
            tasWithNullCompetencies.add(faculty);
        }
        scheduleInput.setTeacherAssistants(tasWithNullCompetencies);


        Set<Faculty> professorsWithNullCompetencies = new HashSet<Faculty>();
        for(Faculty faculty : facultyRepo.getProfessorsSet()){
            faculty.setCompetencies(null);
            faculty.setAvailability(null);
            professorsWithNullCompetencies.add(faculty);
        }
        scheduleInput.setProfessors(professorsWithNullCompetencies);

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
        return scheduleInput;
    }

    @Transactional
    public void createScheduleSolution(){
        logger.info("Creating schedule solution");
        Semester semester = new Semester("2015", "FALL");
        ScheduleInput scheduleInput = prepareScheduleInput(new HashSet<CourseOffering>(),semester);
        ScheduleSolution scheduleSolution =  engineInterface.createScheduleSolution(scheduleInput);
        scheduleSolution.setComputedTime(new Date());
        scheduleSolution.setTriggeredReason("Initial schedule solution");
        scheduleSolution.setOffline(false);
        //logger.info("Solution : "+JSONObjectMapper.jsonify(scheduleSolution));
        logger.info("Done running course optimization engine, solution scheduled "+scheduleSolution.getSchedule().size() +" courses");
        logger.info("Persisting solution ....");
        for(CourseOffering courseOffering : scheduleSolution.getSchedule()) {
            semesterRepo.save(courseOffering.getSemester());
            courseOfferingRepo.save(courseOffering);
        }
        scheduleSolutionRepo.save(scheduleSolution);
    }

    
}
