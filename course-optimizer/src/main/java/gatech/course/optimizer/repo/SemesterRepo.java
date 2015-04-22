package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface SemesterRepo extends CrudRepository<Semester, Long> {

    @Query("select s from semester s order by s.year, s.term")
    public List<Semester> getAllSemesters();
}
