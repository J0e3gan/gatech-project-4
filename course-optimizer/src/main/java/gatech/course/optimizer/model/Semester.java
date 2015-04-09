package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 204069126 on 2/6/15.
 */
@Entity(name = "semester")
public class Semester {

    public enum SemesterTerm {
        FALL,
        SPRING,
        SUMMER
    }

    @Id
    private Long id;
    private SemesterTerm term;
    private int year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemesterTerm getTerm() {
        return term;
    }

    public void setTerm(SemesterTerm term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
