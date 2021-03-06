package gatech.course.optimizer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
@Entity(name = "course_offering")
public class CourseOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer crn;
    private int studentCapacity;

    @OneToOne
    private Course course;

    @OneToOne
    private Semester semester;

    @OneToOne
    private Faculty professor;

    //@OneToMany
    @ManyToMany
    private Set<Faculty> teacherAssistants;

    @ManyToMany
    private List<Student> enrolledStudents;

    @OneToMany
    private List<Student> studentsOnWaitList;


    public CourseOffering() {
    }

    public CourseOffering(String crn, Course course, Semester semester) {
        this.crn = Integer.valueOf(crn);
        this.course = course;
        this.semester = semester;
        this.teacherAssistants = new HashSet<Faculty>();
        this.enrolledStudents = new ArrayList<Student>();
        this.studentsOnWaitList = new ArrayList<Student>();
        this.studentCapacity = Integer.MAX_VALUE;
    }

    public void enrollStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCrn() {
        return crn;
    }

    public void setCrn(Integer crn) {
        this.crn = crn;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Faculty getProfessor() {
        return professor;
    }

    public void setProfessor(Faculty professor) {
        this.professor = professor;
    }

    public Set<Faculty> getTeacherAssistants() {
        return teacherAssistants;
    }

    public void setTeacherAssistants(Set<Faculty> teacherAssistants) {
        this.teacherAssistants = teacherAssistants;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    public void setStudentCapacity(int studentCapacity) {
        this.studentCapacity = studentCapacity;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public List<Student> getStudentsOnWaitList() {
        return studentsOnWaitList;
    }

    public void setStudentsOnWaitList(List<Student> studentsOnWaitList) {
        this.studentsOnWaitList = studentsOnWaitList;
    }

    public int getDemand() {
        return enrolledStudents.size() + studentsOnWaitList.size();
    }

}
