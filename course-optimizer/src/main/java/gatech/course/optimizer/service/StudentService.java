package gatech.course.optimizer.service;

import gatech.course.optimizer.dto.DesiredCourseDTO;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.dto.StudentUpdateRequest;
import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.DesiredCourse;
import gatech.course.optimizer.model.Student;
import gatech.course.optimizer.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
@RestController
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private DesiredCourseRepo desiredCourseRepo;

    @Autowired
    public StudentDetailsRepo studentDetailsRepo;

    @Autowired
    private ExecutionTrigger executionTrigger;


    // TODO: create enum
    public int NEW = 1;
    public int UPDATED = 2;
    public int NO_CHANGE = 3;

    public static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Student> getAllStudents() {
        logger.info("Getting all students");
        return studentRepo.getAllStudents();
    }

    @RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET)
    public
    @ResponseBody
    StudentDTO getStudentDetails(@PathVariable("studentId") String studentId) {
        logger.info("Getting student details for studentId='{}'", studentId);

        return studentDetailsRepo.getStudentDetails(studentId);
    }

    @RequestMapping(value = "/student/enroll", method = RequestMethod.POST)
    public
    @ResponseBody
    Student createStudent(@RequestBody Student student) {
        logger.info("Creating new student");
        return studentRepo.save(student);
    }


    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    public
    @ResponseBody
    StudentDTO updateStudent(@RequestBody StudentUpdateRequest studentUpdateRequest) {

        // TODO: simplify this method
        logger.info("Updating student " + studentUpdateRequest.getStudentId());
        Student student = studentRepo.findByStudentId(studentUpdateRequest.getStudentId());
        if (student != null) {
            List<DesiredCourse> desiredCourses = student.getDesiredCourses();

            // Delete desired courses removed from list
            for (int i = desiredCourses.size() - 1; i <= 0; i--) {
                if (isRemovedFromList(studentUpdateRequest.getDesiredCourses(), desiredCourses.get(i))) {
                    desiredCourseRepo.delete(desiredCourses.get(i));
                }
            }

            for (DesiredCourseDTO desiredCourseDTO : studentUpdateRequest.getDesiredCourses()) {
                int courseChangeStatus = isNewOrUpdated(desiredCourses, desiredCourseDTO);

                if (courseChangeStatus == NEW) {
                    logger.info("New desired course added : " + desiredCourseDTO.getCourseId() + " - " + desiredCourseDTO.getPriority());
                    Course course = courseRepo.findOne(desiredCourseDTO.getCourseId());
                    DesiredCourse newDesiredCourse = new DesiredCourse(student.getId(), course, desiredCourseDTO.getPriority());
                    newDesiredCourse = desiredCourseRepo.save(newDesiredCourse);
                    desiredCourses.add(newDesiredCourse);
                } else if (courseChangeStatus == UPDATED) {
                    logger.info("Priority updated for " + desiredCourseDTO.getCourseId() + " - " + desiredCourseDTO.getPriority());
                    for (DesiredCourse desiredCourse : desiredCourses) {
                        if (desiredCourse.getCourse().getId().longValue() == desiredCourseDTO.getCourseId().longValue()) {
                            desiredCourse.setPriority(desiredCourseDTO.getPriority());
                            desiredCourseRepo.save(desiredCourse);
                        }
                    }
                } else {
                    logger.info("No change to desired course " + desiredCourseDTO.getCourseId() + " - " + desiredCourseDTO.getPriority());
                }
            }
            student.setDesiredCourses(desiredCourses);
            studentRepo.save(student);


            // Trigger engine orchestration since student updated its desired courses
            executionTrigger.createScheduleSolution();

            // Return updated student
            return studentDetailsRepo.getStudentDetails(student.getStudentId());
        }

        logger.error("Student not found for id " + studentUpdateRequest.getStudentId());
        throw new RuntimeException("Student not found");
    }


    private int isNewOrUpdated(List<DesiredCourse> desiredCourses, DesiredCourseDTO desiredCourseDTO) {
        for (DesiredCourse desiredCourse : desiredCourses) {
            // Course was already on the list
            if (desiredCourse.getCourse().getId() == desiredCourseDTO.getCourseId()) {
                // Course was already on the list and it has the same priority
                if (desiredCourse.getPriority() == desiredCourseDTO.getPriority().intValue()) {
                    return NO_CHANGE; // NO CHANGE
                } else {
                    return UPDATED; // UPDATED PRIORITY
                }
            }
        }
        return NEW; // NEW
    }

    private boolean isRemovedFromList(List<DesiredCourseDTO> newDesiredCourses, DesiredCourse oldDesiredCourse) {

        for (DesiredCourseDTO desiredCourseDTO : newDesiredCourses) {
            if (desiredCourseDTO.getCourseId().longValue() == oldDesiredCourse.getId().longValue()) {
                return false;
            }
        }
        return true;
    }

}