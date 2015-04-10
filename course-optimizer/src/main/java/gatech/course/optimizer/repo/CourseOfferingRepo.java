package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface CourseOfferingRepo extends CrudRepository<CourseOffering, Long> {

//@Query("SELECT w FROM Twentyfour w JOIN FETCH w.members m WHERE ((m = :member AND w.owner = :owner) OR (m = :owner AND w.owner = :member)) AND w.type = 'SINGLE'")

    //@Query("select co from course_offering co where :student in co.enrolledStudents")
    @Query("select co from course_offering co JOIN FETCH co.enrolledStudents es WHERE (es = :student)")
    public List<CourseOffering> findCoursesByStudent(@Param("student") Student student);
}
