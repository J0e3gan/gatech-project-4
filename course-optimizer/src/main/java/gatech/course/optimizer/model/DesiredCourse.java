package gatech.course.optimizer.model;

import javax.persistence.*;

/**
 * Created by 204069126 on 4/20/15.
 */
@Entity(name = "desired_course")
public class DesiredCourse {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Course course;
    private int priority;
    private Long studentId;

    public DesiredCourse() {}

    public DesiredCourse(Long studentId, Course course, int priority) {
        this.studentId = studentId;
        this.course = course;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
