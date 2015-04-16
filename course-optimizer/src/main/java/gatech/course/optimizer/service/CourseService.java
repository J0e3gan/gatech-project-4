package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.repo.CourseOfferingRepo;
import gatech.course.optimizer.repo.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Joe Egan on 4/12/15.
 */
@RestController
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    public static Logger logger = LoggerFactory.getLogger(CourseService.class);

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Course> getAllCourses() {
        logger.info("Getting all courses");
        return courseRepo.getAllCourses();
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public
    @ResponseBody
    Course getCourse(@PathVariable("courseId") Long courseId) {
        logger.info("Getting course with id='{}'", courseId);
        return courseRepo.getCourse(courseId);
    }

    @RequestMapping(value = "/course/number/{courseNumber}", method = RequestMethod.GET)
    public
    @ResponseBody
    Course getCourseByNumber(@PathVariable("courseNumber") String courseNumber) {
        logger.info("Getting course with number='{}'", courseNumber);
        return courseRepo.getCourseByNumber(courseNumber);
    }


}