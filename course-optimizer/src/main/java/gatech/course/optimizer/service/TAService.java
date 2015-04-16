package gatech.course.optimizer.service;

import gatech.course.optimizer.model.TA;
import gatech.course.optimizer.repo.TARepo;
import gatech.course.optimizer.utils.JSONObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

//    TODO: Wire createTA up and smoke test it.
//    @RequestMapping(value = "/ta/create", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    TA createTA(@RequestBody TA ta) {
//        logger.info("Creating TA: " + JSONObjectMapper.jsonify(ta));
//        return taRepo.save(ta);
//    }

    @RequestMapping(value = "/ta/delete/{taId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteTA(@PathVariable("taId") Long id) {
        logger.info("Deleting TA for id='{}'", id);
        TA ta = taRepo.findOne(id);
        if (ta != null) {
            taRepo.delete(ta);
            logger.info("Deleted TA for id='{}'", id);
        } else {
            logger.info("TA for id='{}' not found.", id);
        }
    }
}