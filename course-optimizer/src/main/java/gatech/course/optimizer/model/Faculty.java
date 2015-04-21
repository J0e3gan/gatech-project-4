package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
@Entity(name = "faculty")
public class Faculty extends User {


    @ManyToMany
    private Set<Course> competencies;

    @ManyToMany
    private List<Semester> availability;

    public Set<Course> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(Set<Course> competencies) {
        this.competencies = competencies;
    }

    public List<Semester> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Semester> availability) {
        this.availability = availability;
    }
}
