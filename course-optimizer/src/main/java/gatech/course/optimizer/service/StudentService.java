package gatech.course.optimizer.service;

import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.dto.TakenCourseDTO;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Student;
import gatech.course.optimizer.repo.CourseOfferingRepo;
import gatech.course.optimizer.repo.StudentRecordRepo;
import gatech.course.optimizer.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
@RestController
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseOfferingRepo courseOfferingRepo;

    @Autowired
    private StudentRecordRepo studentRecordRepo;

    public static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Student> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    @RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET)
    public
    @ResponseBody
    StudentDTO getStudentDetails(@PathVariable("studentId") String studentId) {
        logger.info("Getting student details for studentId='{}'", studentId);
        Student student = studentRepo.findByStudentId(studentId);
        StudentDTO studentDTO = new StudentDTO(student);

        List<TakenCourseDTO> takenCourses = new ArrayList<TakenCourseDTO>();
        List<CourseOffering> courseOfferings = courseOfferingRepo.findCoursesByStudent(student);
        for (CourseOffering courseOffering : courseOfferings) {
            String grade = studentRecordRepo.getGradeForStudent(courseOffering.getId(), student.getId());
            TakenCourseDTO takenCourseDTO = new TakenCourseDTO(courseOffering, grade);
            takenCourses.add(takenCourseDTO);
        }
        studentDTO.setTakenCourses(takenCourses);

        return studentDTO;
    }

    @RequestMapping(value = "/student/enroll", method = RequestMethod.POST)
    public
    @ResponseBody
    Student createStudent(@RequestBody Student student) {

        logger.info("Creating new student");
        return studentRepo.save(student);
    }


    /*
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public
    @ResponseBody
    StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {

        logger.info("Creating new student");
        studentRepo.save(studentDTO.toStudent());
        studentDTO.ge
        return null;
    }
    */


}