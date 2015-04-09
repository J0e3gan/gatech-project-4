package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.model.ScheduleSolution;

/**
 * Created by 204069126 on 2/6/15.
 */
public class GurobiEngine implements EngineInterface {

    @Override
    public ScheduleSolution createScheduleSolution(ScheduleInput scheduleInput) {
        return new ScheduleSolution();
    }
}
