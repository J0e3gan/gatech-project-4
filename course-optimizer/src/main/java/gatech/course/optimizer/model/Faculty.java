package gatech.course.optimizer.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
@Entity(name = "faculty")
public class Faculty extends User {


    @OneToMany
    private Set<Course> competencies;

    @OneToMany
    private List<Semester> availabiity;

    public Set<Course> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(Set<Course> competencies) {
        this.competencies = competencies;
    }

    public List<Semester> getAvailabiity() {
        return availabiity;
    }

    public void setAvailabiity(List<Semester> availabiity) {
        this.availabiity = availabiity;
    }
}
