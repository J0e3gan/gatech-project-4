package gatech.course.optimizer.model;

import java.util.List;

/**
 * Created by 204069126 on 2/6/15.
 */
public class Student extends User {

    private List<Course> desiredCourses;
    private int seniority;
    private List<Course> takenCourses;
    private List<CourseOffering> recommendedCourses;
}
