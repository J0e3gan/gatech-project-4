package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Joe Egan on 4/14/15.
 */
public interface ProfessorRepo extends CrudRepository<Professor, Long> {

    @Query("select p from professor p order by p.lastName, p.firstName")
    public List<Professor> getAllProfessors();

}
