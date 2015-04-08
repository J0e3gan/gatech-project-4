package gatech.course.optimizer.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by 204069126 on 3/16/15.
 */
public class ScheduleSolution  {

    private Long id;
    private Date computedTime;
    private String triggeredReason;
    private boolean isOffline;
    private Set<CourseOffering> schedule;
    private Semester semester;


}
