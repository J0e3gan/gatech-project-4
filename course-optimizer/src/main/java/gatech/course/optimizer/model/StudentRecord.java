package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by 204069126 on 4/9/15.
 */
@Entity(name = "student_record")
public class StudentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long courseOfferingDBId;
    private Long studentDBId;
    private String grade;

    // Extra - not really needed
    private String courseShortName;
    private Integer crn;
    private String studentId;


    public StudentRecord() {
    }

    public StudentRecord(Long courseOfferingDBId, Long studentDBId, String grade) {
        this.courseOfferingDBId = courseOfferingDBId;
        this.studentDBId = studentDBId;
        this.grade = grade;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseOfferingDBId() {
        return courseOfferingDBId;
    }

    public void setCourseOfferingDBId(Long courseOfferingDBId) {
        this.courseOfferingDBId = courseOfferingDBId;
    }

    public Long getStudentDBId() {
        return studentDBId;
    }

    public void setStudentDBId(Long studentDBId) {
        this.studentDBId = studentDBId;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public Integer getCrn() {
        return crn;
    }

    public void setCrn(Integer crn) {
        this.crn = crn;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
