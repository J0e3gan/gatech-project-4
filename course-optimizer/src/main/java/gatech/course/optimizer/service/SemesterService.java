package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.repo.SemesterRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 204069126 on 4/21/15.
 */

@RestController
public class SemesterService {


    public static Logger logger = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterRepo semesterRepo;

    @RequestMapping(value = "/semesters", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Semester> getSemesters() {
        logger.info("Getting all semesters");
        return semesterRepo.getAllSemesters();
    }

    @RequestMapping(value = "/semester", method = RequestMethod.POST)
    public
    @ResponseBody
    Semester createOffering(@RequestBody Semester semester) {
        logger.info("Creating new semester");
        return semesterRepo.save(semester);
    }

}
