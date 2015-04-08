package gatech.course.optimizer.model;

/**
 * Created by 204069126 on 2/6/15.
 */
public class Semester {

    public enum SemesterTerm {
        FALL,
        SPRING,
        SUMMER
    }

    private SemesterTerm term;
    private int year;
}
