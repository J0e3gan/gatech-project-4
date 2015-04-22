package gatech.course.optimizer.dto;

import java.util.List;

/**
 * Created by 204069126 on 4/21/15.
 */
public class StudentUpdateRequest {

    String studentId;
    List<DesiredCourseDTO> desiredCourses;

    public StudentUpdateRequest() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<DesiredCourseDTO> getDesiredCourses() {
        return desiredCourses;
    }

    public void setDesiredCourses(List<DesiredCourseDTO> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }
}
