package gatech.course.optimizer.model;

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

    public ScheduleInput(List<Faculty> fac, List<Course> courses, List<Student> students) {
    }
}
