package gatech.course.optimizer.model;

import javax.persistence.Entity;

/**
 * Created by Joe Egan on 4/13/15.
 */
@Entity(name = "professor")
public class Professor extends Faculty {
    public Professor() {
        setRole(Role.PROFESSOR);
    }

    public Professor(String firstName, String lastName) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Professor(String username, String password, String firstName, String lastName) {
        this(firstName, lastName);
        setUsername(username);
        setPassword(password);
    }
}