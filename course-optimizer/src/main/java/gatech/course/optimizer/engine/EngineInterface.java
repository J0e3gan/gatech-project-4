package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.model.ScheduleSolution;

/**
 * Created by 204069126 on 2/8/15.
 */
public interface EngineInterface {

	/**
	 * 
	 * @param scheduleInput
	 * @return null is model is infeasible based on input
	 */
    public ScheduleSolution createScheduleSolution(ScheduleInput scheduleInput);
}
