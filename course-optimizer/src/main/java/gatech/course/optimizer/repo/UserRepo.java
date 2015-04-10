package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 204069126 on 4/7/15.
 */
//@Component
public interface UserRepo extends CrudRepository<User, Long> {

    public User findById(Long id);

    public User findByUsername(String username);
}
