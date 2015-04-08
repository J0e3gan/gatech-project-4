package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by 204069126 on 4/7/15.
 */
@Component
public interface UserRepo extends CrudRepository<User,Long> {

    public User findById(Long id);
}
