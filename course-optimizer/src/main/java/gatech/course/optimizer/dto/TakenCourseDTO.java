package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.Semester;

import java.util.Set;

/**
 * Created by 204069126 on 4/9/15.
 */
public class TakenCourseDTO {


    private Integer crn;
    private Course course;
    private Semester semester;
    private Faculty professor;
    private Set<Faculty> teacherAssistants;
    private String grade;

    public TakenCourseDTO() {
    }

    public TakenCourseDTO(CourseOffering courseOffering, String grade) {
        this.crn = courseOffering.getCrn();
        this.course = courseOffering.getCourse();
        this.semester = courseOffering.getSemester();
        this.professor = courseOffering.getProfessor();
        this.teacherAssistants = courseOffering.getTeacherAssistants();
        this.grade = grade;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
