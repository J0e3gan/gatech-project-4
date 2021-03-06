package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.DesiredCourse;
import gatech.course.optimizer.model.Specialization;
import gatech.course.optimizer.model.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by 204069126 on 4/9/15.
 */
public class StudentDTO {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<DesiredCourse> desiredCourses;
    private int seniority;
    private String studentId;

    private Specialization chosenSpecialization;
    private List<TakenCourseDTO> takenCourses;
    private Map<Long, List<TakenCourseDTO>> recommendedCourses;

    public StudentDTO() {
    }

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.username = student.getUsername();
        this.password = student.getPassword();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.desiredCourses = student.getDesiredCourses();
        this.seniority = student.getSeniority();
        this.studentId = student.getStudentId();
    }

    public Student toStudent() {

        Student student = new Student(this.getUsername(), this.getPassword(), this.getFirstName(),
                this.getLastName(), this.getStudentId());
        student.setId(this.getId());
        student.setDesiredCourses(this.getDesiredCourses());
        student.setSeniority(this.getSeniority());
        return student;
    }

    public Map<Long, List<TakenCourseDTO>> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(Map<Long, List<TakenCourseDTO>> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<TakenCourseDTO> getTakenCourses() {
        return takenCourses;
    }

    public void setTakenCourses(List<TakenCourseDTO> takenCourses) {
        this.takenCourses = takenCourses;
    }

    public Specialization getChosenSpecialization() {
        return this.chosenSpecialization;
    }

    public void setChosenSpecialization(Specialization specialization) {
        this.chosenSpecialization = specialization;
    }
}
