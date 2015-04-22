package gatech.course.optimizer.dto;

/**
 * Created by 204069126 on 4/21/15.
 */
public class DesiredCourseDTO {

    public Long courseId;
    public Integer priority;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
