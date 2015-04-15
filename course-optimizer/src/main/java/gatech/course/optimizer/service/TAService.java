package gatech.course.optimizer.service;

import gatech.course.optimizer.model.TA;
import gatech.course.optimizer.repo.TARepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Joe Egan on 4/14/15.
 */
@RestController
public class TAService {
    @Autowired
    private TARepo taRepo;

    public static Logger logger = LoggerFactory.getLogger(TAService.class);

    @RequestMapping(value = "/tas", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TA> getAllTAs() {
        logger.info("Getting all TAs");
        return taRepo.getAllTAs();
    }
}