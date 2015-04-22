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
    private static long CURRENT_SEMESTER_ID = 4L; // TODO: remove the hardcoded id usage

    @RequestMapping(value = "/offerings", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CourseOffering> getAllCourseOfferings() { // This is really get past course offerings now TODO: update
        logger.info("Getting all courses previous and newly scheduled");
        //return courseOfferingRepo.getAllCourseOfferings();
        return courseOfferingRepo.getCourseOfferingsUpToSemester(CURRENT_SEMESTER_ID);
    }

    @RequestMapping(value = "/offering/schedule", method = RequestMethod.POST)
    public
    @ResponseBody
    CourseOffering createOffering(@RequestBody CourseOffering courseOffering) {
        logger.info("Scheduling course: " + JSONObjectMapper.jsonify(courseOffering));
        return courseOfferingRepo.save(courseOffering);
    }

    @RequestMapping(value = "/offering/delete/{offeringId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteOffering(@PathVariable("offeringId") Long id) {
        logger.info("Deleting scheduled course with id='{}'", id);
        CourseOffering offering = courseOfferingRepo.findOne(id);
        if (offering != null) {
            courseOfferingRepo.delete(offering);
            logger.info("Deleted scheduled course with id='{}'", id);
        } else {
            logger.info("Scheduled course with id='{}' not found.", id);
        }
    }
}
