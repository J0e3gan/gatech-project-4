package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Constraint;
import gatech.course.optimizer.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface ConstraintRepo extends CrudRepository<Constraint, Long> {

    @Query("select c from constraint c order by c.name asc")
    public List<Constraint> getAllConstraints();
}
