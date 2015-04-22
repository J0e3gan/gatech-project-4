/**
 *
 */
package gatech.course.optimizer.model;

import java.util.Set;

/**
 * @author John Raffensperger
 */
public class Specialization {

    private Set<Course> requiredCourses;
    private Set<Course> coreCourses;
    private Set<Course> electiveCourses;
    private int numberOfCoreCoursesRequired;
    private int numberOfElectiveCoursesRequired;

    /**
     * @return the requiredCourses
     */
    public Set<Course> getRequiredCourses() {
        return requiredCourses;
    }

    /**
     * @return the coreCourses
     */
    public Set<Course> getCoreCourses() {
        return coreCourses;
    }

    /**
     * @return the electiveCourses
     */
    public Set<Course> getElectiveCourses() {
        return electiveCourses;
    }

    /**
     * @return the numberOfCoreCoursesRequired
     */
    public int getNumberOfCoreCoursesRequired() {
        return numberOfCoreCoursesRequired;
    }

    /**
     * @return the numberOfElectiveCoursesRequired
     */
    public int getNumberOfElectiveCoursesRequired() {
        return numberOfElectiveCoursesRequired;
    }

    /**
     * @param requiredCourses the requiredCourses to set
     */
    public void setRequiredCourses(Set<Course> requiredCourses) {
        this.requiredCourses = requiredCourses;
    }

    /**
     * @param coreCourses the coreCourses to set
     */
    public void setCoreCourses(Set<Course> coreCourses) {
        this.coreCourses = coreCourses;
    }

    /**
     * @param electiveCourses the electiveCourses to set
     */
    public void setElectiveCourses(Set<Course> electiveCourses) {
        this.electiveCourses = electiveCourses;
    }

    /**
     * @param numberOfCoreCoursesRequired the numberOfCoreCoursesRequired to set
     */
    public void setNumberOfCoreCoursesRequired(int numberOfCoreCoursesRequired) {
        this.numberOfCoreCoursesRequired = numberOfCoreCoursesRequired;
    }

    /**
     * @param numberOfElectiveCoursesRequired the numberOfElectiveCoursesRequired to set
     */
    public void setNumberOfElectiveCoursesRequired(int numberOfElectiveCoursesRequired) {
        this.numberOfElectiveCoursesRequired = numberOfElectiveCoursesRequired;
    }


}
