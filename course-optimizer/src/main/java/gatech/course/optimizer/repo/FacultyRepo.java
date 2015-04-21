package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.Faculty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 4/21/15.
 */
public interface FacultyRepo extends CrudRepository<Faculty, Long> {

    //   ADMIN,STUDENT,PROFESSOR,TA
    @Query("select f from faculty f where f.role = 3 order by f.lastName, f.firstName")
    public List<Faculty> getAllProfessors();

    @Query("select f from faculty f where f.role = 3 order by f.lastName, f.firstName")
    public Set<Faculty> getProfessorsSet();

    @Query("select f from faculty f where f.role = 4 order by f.lastName, f.firstName")
    public List<Faculty> getAllTAs();

    @Query("select f from faculty f where f.role = 4 order by f.lastName, f.firstName")
    public Set<Faculty> getTASet();

}
