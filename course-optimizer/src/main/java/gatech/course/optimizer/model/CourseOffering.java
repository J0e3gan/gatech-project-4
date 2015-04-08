package gatech.course.optimizer.model;

import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
public class CourseOffering {

    private Long id;
    private Course course;
    private Semester semester;
    private Faculty professor;
    private Set<Faculty> teacherAssistants;
    private int studentCapacity;
    private List<Student> enrolledStudents;
    private List<Student> studentsOnWaitList;

    public int getDemand() {
        return enrolledStudents.size() + studentsOnWaitList.size();
    }

}
