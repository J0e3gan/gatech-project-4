package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.Student;

import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
public class ScheduleInput {

    private Set<CourseOffering> requiredOfferings;
    private Set<Course> coursesThatCanBeOffered;
    private Set<Student> students;
    private Set<Faculty> professors;
    private Set<Faculty> teacherAssistants;

    public Set<CourseOffering> getRequiredOfferings() {
        return requiredOfferings;
    }

    public void setRequiredOfferings(Set<CourseOffering> requiredOfferings) {
        this.requiredOfferings = requiredOfferings;
    }

    public Set<Course> getCoursesThatCanBeOffered() {
        return coursesThatCanBeOffered;
    }

    public void setCoursesThatCanBeOffered(Set<Course> coursesThatCanBeOffered) {
        this.coursesThatCanBeOffered = coursesThatCanBeOffered;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Faculty> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Faculty> professors) {
        this.professors = professors;
    }

    public Set<Faculty> getTeacherAssistants() {
        return teacherAssistants;
    }

    public void setTeacherAssistants(Set<Faculty> teacherAssistants) {
        this.teacherAssistants = teacherAssistants;
    }
}
