package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface CourseRepo extends CrudRepository<Course, Long> {

    @Query("select c from course c order by c.number asc")
    public List<Course> getAllCourses();

}
