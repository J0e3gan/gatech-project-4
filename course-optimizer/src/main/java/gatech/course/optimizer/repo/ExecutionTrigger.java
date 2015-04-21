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
        logger.info("Schedule Input : \n"+JSONObjectMapper.jsonify(scheduleInput));
        //scheduleInput.setAvailableSpecializations();but

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
