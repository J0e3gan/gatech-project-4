package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.ScheduleSolution;

import java.util.Date;

/**
 * Created by 204069126 on 4/21/15.
 */
public class ScheduleSolutionDTO {

    private Long id;
    private Long computedTime;
    private String triggeredReason;
    private boolean isOffline;

    public ScheduleSolutionDTO() {
    }

    public ScheduleSolutionDTO(ScheduleSolution scheduleSolution) {
        this.id = scheduleSolution.getId();
        if (scheduleSolution.getComputedTime() != null) {
            this.computedTime = scheduleSolution.getComputedTime().getTime();
        }
        this.triggeredReason = scheduleSolution.getTriggeredReason();
        this.isOffline = scheduleSolution.isOffline();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getComputedTime() {
        return new Date(computedTime);
    }

    public void setComputedTime(Date computedTime) {
        this.computedTime = computedTime.getTime();
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
}
