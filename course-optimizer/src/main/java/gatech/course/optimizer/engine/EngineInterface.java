package gatech.course.optimizer.engine;

import gatech.course.optimizer.model.ScheduleInput;
import gatech.course.optimizer.model.ScheduleSolution;

/**
 * Created by 204069126 on 2/8/15.
 */
public interface EngineInterface {

    public ScheduleSolution createScheduleSolution(ScheduleInput scheduleInput);
}
