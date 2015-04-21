package gatech.course.optimizer.service;

import gatech.course.optimizer.dto.ScheduleSolutionDTO;
import gatech.course.optimizer.model.ScheduleSolution;
import gatech.course.optimizer.repo.ScheduleSolutionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 204069126 on 4/21/15.
 */
public class ScheduleSolutionService {

    @Autowired
    private ScheduleSolutionRepo scheduleSolutionRepo;

    public static Logger logger = LoggerFactory.getLogger(ScheduleSolutionRepo.class);


    @RequestMapping(value = "/solutions", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ScheduleSolutionDTO> getAllSolutions() {
        logger.info("Getting all solutions");
        List<ScheduleSolution> solutions = scheduleSolutionRepo.getAll();
        List<ScheduleSolutionDTO> solutionDTOList = new ArrayList<ScheduleSolutionDTO>();
        for (ScheduleSolution scheduleSolution : solutions) {
            solutionDTOList.add(new ScheduleSolutionDTO(scheduleSolution));
        }
        return solutionDTOList;
    }


}
