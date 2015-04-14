package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Constraint;
import gatech.course.optimizer.repo.ConstraintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 204069126 on 4/14/15.
 */
@RestController
public class ConstraintService {

    @Autowired
    private ConstraintRepo constraintRepo;

    @RequestMapping(value = "/constraints", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Constraint> getConstraints() {
        return constraintRepo.getAllConstraints();
    }

    @RequestMapping(value = "/constraint/create", method = RequestMethod.POST)
    public
    @ResponseBody Constraint createConstraint(@RequestBody Constraint constraint){
        return constraintRepo.save(constraint);
    }
}
