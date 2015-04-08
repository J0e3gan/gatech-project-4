package gatech.course.optimizer.model;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 204069126 on 3/16/15.
 */

@Entity(name = "user")
public class User  {

    public enum Role {
        ADMIN,
        STUDENT,
        PROFESSOR,
        TA
    }

    public User() {}

    public User(Long id, String password, String name, String lastName, Role role) {
        this.id = id;
        this.password = password;
        this.firstName = name;
        this.lastName = lastName;
        this.role = role;
    }

    @Id
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
