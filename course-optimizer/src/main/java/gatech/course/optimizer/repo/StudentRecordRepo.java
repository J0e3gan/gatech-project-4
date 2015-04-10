package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.StudentRecord;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface StudentRecordRepo extends CrudRepository<StudentRecord, Long> {
}
