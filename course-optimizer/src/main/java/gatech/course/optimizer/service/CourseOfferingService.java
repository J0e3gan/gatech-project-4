package gatech.course.optimizer.service;

import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.repo.CourseOfferingRepo;
import gatech.course.optimizer.utils.JSONObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 204069126 on 4/14/15.
 */
@RestController
public class CourseOfferingService {

    @Autowired
    private CourseOfferingRepo courseOfferingRepo;

    public static Logger logger = LoggerFactory.getLogger(CourseOfferingService.class);

    @RequestMapping(value = "/offerings", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CourseOffering> getAllCourseOfferings() {
        logger.info("Getting all courses previous and newly scheduled");
        return courseOfferingRepo.getAllCourseOfferings();
    }

    @RequestMapping(value = "/offering/schedule", method = RequestMethod.POST)
    public
    @ResponseBody
    CourseOffering createOffering(@RequestBody CourseOffering courseOffering) {
        logger.info("Scheduling course: "+ JSONObjectMapper.jsonify(courseOffering));
        return courseOfferingRepo.save(courseOffering);
    }
}
