package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.Specialization;

import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
public class ScheduleInput {

    private Set<CourseOffering> requiredOfferings;
    private Set<Course> coursesThatCanBeOffered;
    private Set<StudentDTO> students;
    private Set<Faculty> professors;
    private Set<Faculty> teacherAssistants;
    private Set<Specialization> availableSpecializations;
    
    private int allowedClassesPerSemester;

    public int getAllowedClassesPerSemester() {
        return allowedClassesPerSemester;
    }

    public void setAllowedClassesPerSemester(int allowedClassesPerSemester) {
        this.allowedClassesPerSemester = allowedClassesPerSemester;
    }

    public Set<CourseOffering> getRequiredOfferings() {
        return requiredOfferings;
    }

    public void setRequiredOfferings(Set<CourseOffering> requiredOfferings) {
        this.requiredOfferings = requiredOfferings;
    }

    public Set<Course> getCoursesThatCanBeOffered() {
        return coursesThatCanBeOffered;
    }
    
    public Set<Specialization> getAvailableSpecializations(){
    	return this.availableSpecializations;
    }

    public void setCoursesThatCanBeOffered(Set<Course> coursesThatCanBeOffered) {
        this.coursesThatCanBeOffered = coursesThatCanBeOffered;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
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
    
    public void setAvailableSpecializations(Set<Specialization> specializations){
    	this.availableSpecializations = specializations;
    }
}
