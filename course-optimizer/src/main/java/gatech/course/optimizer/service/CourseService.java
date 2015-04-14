package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.repo.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    List<Course> getAllCourses() { return courseRepo.getAllCourses(); }
}