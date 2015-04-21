package gatech.course.optimizer.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by 204069126 on 3/16/15.
 */
@Entity(name = "schedule_solution")
public class ScheduleSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date computedTime;
    private String triggeredReason;
    private boolean isOffline;

    @OneToMany
    private Set<CourseOffering> schedule;

    @OneToOne
    private Semester semester;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getComputedTime() {
        return computedTime;
    }

    public void setComputedTime(Date computedTime) {
        this.computedTime = computedTime;
    }

    public String getTriggeredReason() {
        return triggeredReason;
    }

    public void setTriggeredReason(String triggeredReason) {
        this.triggeredReason = triggeredReason;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean isOffline) {
        this.isOffline = isOffline;
    }

    public Set<CourseOffering> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<CourseOffering> schedule) {
        this.schedule = schedule;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
