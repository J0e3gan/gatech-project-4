package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.ScheduleSolution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface ScheduleSolutionRepo extends CrudRepository<ScheduleSolution, Long> {

    @Query("select s from schedule_solution s order by s.computedTime asc")
    public List<ScheduleSolution> getAll();
}
