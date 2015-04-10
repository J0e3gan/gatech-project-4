package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface StudentRepo extends CrudRepository<Student, Long> {

    @Query("select s from student s order by s.firstName desc")
    public List<Student> getAllStudents();
}
