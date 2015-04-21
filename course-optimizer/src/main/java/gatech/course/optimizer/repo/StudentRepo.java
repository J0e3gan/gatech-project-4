package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface StudentRepo extends CrudRepository<Student, Long> {

    @Query("select s from student s order by s.firstName desc")
    public List<Student> getAllStudents();

    @Query("select s from student s where s.studentId = :studentId")
    public Student findByStudentId(@Param("studentId") String studentId);
}
