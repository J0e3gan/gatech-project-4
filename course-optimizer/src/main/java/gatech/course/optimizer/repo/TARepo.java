package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.TA;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Joe Egan on 4/14/15.
 */
public interface TARepo extends CrudRepository<TA, Long> {

    @Query("select t from ta t order by t.lastName, t.firstName")
    public List<TA> getAllTAs();

}
