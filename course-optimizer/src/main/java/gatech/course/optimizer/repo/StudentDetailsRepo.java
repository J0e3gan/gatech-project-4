package gatech.course.optimizer.repo;

import gatech.course.optimizer.dto.ScheduleSolutionDTO;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.dto.TakenCourseDTO;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.ScheduleSolution;
import gatech.course.optimizer.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 204069126 on 4/21/15.
 */
@Component
public class StudentDetailsRepo {


    @Autowired
    public StudentRecordRepo studentRecordRepo;

    @Autowired
    public StudentRepo studentRepo;

    @Autowired
    public CourseOfferingRepo courseOfferingRepo;

    @Autowired
    public ScheduleSolutionRepo scheduleSolutionRepo;

    public static Logger logger = LoggerFactory.getLogger(StudentDetailsRepo.class);


    @Transactional
    public StudentDTO getStudentDetails(String studentId) {
        Student student = studentRepo.findByStudentId(studentId);
        if (student != null) {
            StudentDTO studentDTO = new StudentDTO(student);

            List<TakenCourseDTO> takenCourses = new ArrayList<TakenCourseDTO>();
            List<CourseOffering> courseOfferings = courseOfferingRepo.findCoursesByStudent(student);
            for (CourseOffering courseOffering : courseOfferings) {
                String grade = studentRecordRepo.getGradeForStudent(courseOffering.getId(), student.getId());
                if (grade != null && grade.length() > 0) {
                    TakenCourseDTO takenCourseDTO = new TakenCourseDTO(courseOffering, grade);
                    takenCourses.add(takenCourseDTO);
                }
            }
            studentDTO.setTakenCourses(takenCourses);

            Map<Long, List<TakenCourseDTO>> recommendedCourses = new HashMap<Long, List<TakenCourseDTO>>();

            for (ScheduleSolution scheduleSolution : scheduleSolutionRepo.getAll()) {
                ScheduleSolutionDTO scheduleSolutionDTO = new ScheduleSolutionDTO(scheduleSolution);
                recommendedCourses.put(scheduleSolutionDTO.getComputedTime().getTime(), new ArrayList<TakenCourseDTO>());
                for (CourseOffering courseOffering : scheduleSolution.getSchedule()) {
                    if (courseOffering.getEnrolledStudents().contains(student)) {
                        recommendedCourses.get(scheduleSolutionDTO.getComputedTime().getTime()).add(new TakenCourseDTO(courseOffering, ""));
                    }
                }
            }
            studentDTO.setRecommendedCourses(recommendedCourses);

            return studentDTO;
        } else {
            logger.info("Student details for studentId='{}' not found.", studentId);
            return null;
        }
    }

}
