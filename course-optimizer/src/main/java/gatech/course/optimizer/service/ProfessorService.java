package gatech.course.optimizer.service;

import gatech.course.optimizer.dto.ProfessorDTO;
import gatech.course.optimizer.model.Professor;
import gatech.course.optimizer.repo.ProfessorRepo;
import gatech.course.optimizer.utils.JSONObjectMapper;
import org.modelmapper.ModelMapper;
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
    ProfessorDTO createProfessor(@RequestBody ProfessorDTO professorDTO) {
        logger.info("Creating professor: " + JSONObjectMapper.jsonify(professorDTO));

        ModelMapper modelMapper = new ModelMapper();
        Professor prof = modelMapper.map(professorDTO, Professor.class);
        Professor savedProf = professorRepo.save(prof);

        return modelMapper.map(savedProf, ProfessorDTO.class);
    }

    @RequestMapping(value = "/professor/delete/{professorId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteProfessor(@PathVariable("professorId") Long id) {
        logger.info("Deleting professor with id='{}'", id);

        Professor prof = professorRepo.findOne(id);
        if (prof != null) {
            professorRepo.delete(prof);
            logger.info("Deleted professor with id='{}'", id);
        } else {
            logger.info("Professor with id='{}' not found.", id);
        }
    }
}