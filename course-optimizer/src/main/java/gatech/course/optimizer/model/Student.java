package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by 204069126 on 2/6/15.
 */

@Entity(name = "student")
public class Student extends User {


    @OneToMany(fetch= FetchType.EAGER)
    private List<Course> desiredCourses;

    private int seniority;

    @OneToMany(fetch= FetchType.EAGER)
    private List<Course> takenCourses;

    //@OneToMany(fetch= FetchType.EAGER)
    //private List<CourseOffering> recommendedCourses;

    public List<Course> getDesiredCourses() {
        return desiredCourses;
    }

    public void setDesiredCourses(List<Course> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public List<Course> getTakenCourses() {
        return takenCourses;
    }

    public void setTakenCourses(List<Course> takenCourses) {
        this.takenCourses = takenCourses;
    }


    /*
    public List<CourseOffering> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<CourseOffering> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }
    */
}
