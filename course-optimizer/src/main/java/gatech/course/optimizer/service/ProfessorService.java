package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Professor;
import gatech.course.optimizer.repo.ProfessorRepo;
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
public class ProfessorService {
    @Autowired
    private ProfessorRepo professorRepo;

    public static Logger logger = LoggerFactory.getLogger(ProfessorService.class);

    @RequestMapping(value = "/professors", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Professor> getAllProfessors() {
        logger.info("Getting all professors");
        return professorRepo.getAllProfessors();
    }
}