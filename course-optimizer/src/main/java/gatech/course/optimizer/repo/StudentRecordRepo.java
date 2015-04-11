package gatech.course.optimizer.repo;

import gatech.course.optimizer.model.StudentRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by 204069126 on 4/9/15.
 */
public interface StudentRecordRepo extends CrudRepository<StudentRecord, Long> {


    @Query("select s.grade from student_record s where s.courseOfferingDBId = :courseOfferingDBId and s.studentDBId = :studentDBId")
    public String getGradeForStudent(@Param("courseOfferingDBId") Long courseOfferingDBId, @Param("studentDBId") Long studentDBId);
}
