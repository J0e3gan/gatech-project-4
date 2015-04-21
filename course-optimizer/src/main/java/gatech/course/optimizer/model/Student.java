package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 2/6/15.
 */

@Entity(name = "student")
public class Student extends User {

    @OneToMany(fetch = FetchType.EAGER)
    private List<DesiredCourse> desiredCourses;

    private int seniority;
    private String studentId;

    public Student() {
    }

    public Student(String username, String password, String firstName, String lastName, String id) {
        this.studentId = id;
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<DesiredCourse> getDesiredCourses() {
        return desiredCourses;
    }

    public void setDesiredCourses(List<DesiredCourse> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }
}
