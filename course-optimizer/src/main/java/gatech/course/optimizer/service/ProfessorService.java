package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Professor;
import gatech.course.optimizer.repo.ProfessorRepo;
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

    @RequestMapping(value = "/professor/create", method = RequestMethod.POST)
    public
    @ResponseBody
    Professor createProfessor(@RequestBody Professor professor) {
        logger.info("Creating professor: " + JSONObjectMapper.jsonify(professor));
        return professorRepo.save(professor);
    }

    @RequestMapping(value = "/professor/delete/{professorId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteProfessor(@PathVariable("professorId") Long id) {
        logger.info("Deleting professor for id='{}'", id);
        Professor prof = professorRepo.findOne(id);
        if (prof != null) {
            professorRepo.delete(prof);
            logger.info("Deleted professor for id='{}'", id);
        } else {
            logger.info("Professor for id='{}' not found.", id);
        }
    }
}