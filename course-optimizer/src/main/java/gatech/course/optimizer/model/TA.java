package gatech.course.optimizer.model;

import gatech.course.optimizer.model.User.Role;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * Created by Joe Egan on 4/13/15.
 */
@Entity(name = "ta")
public class TA extends Faculty {
    public TA() {
        setRole(Role.TA);
    }

    public TA(String firstName, String lastName) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
    }

    public TA(String username, String password, String firstName, String lastName) {
        this(firstName, lastName);
        setUsername(username);
        setPassword(password);
    }
}