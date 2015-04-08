package gatech.course.optimizer.service;

import gatech.course.optimizer.exception.WebServiceException;
import gatech.course.optimizer.model.User;
import gatech.course.optimizer.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by 204069126 on 3/17/15.
 */
@RestController
public class AuthenticationService {

    public static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public
    @ResponseBody
    User authenticate(@RequestParam("id") Long id,
                      @RequestParam("password") String password) {


        if (id == null || password == null) {
            throw new WebServiceException("Missing username or password", HttpStatus.BAD_REQUEST);
        }

        logger.info("Login attempt 'id='" + id + " 'password='" + password);
        User user = userRepo.findById(id);

        if (user == null) {
            throw new WebServiceException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new WebServiceException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostConstruct
    public void createSuperAdminUsers() {
        /*
            John Raffensperger [902302881]
            Pawel Drozdz [902880094]
            Jaclyn Adams [902922234]
            Joe Egan [903124450]
         */

        User john = new User(902302881L,"John","John","Raffensperger", User.Role.ADMIN);
        userRepo.save(john);

        User pawel = new User(902880094L,"Pawel","Pawel","Drozdz", User.Role.ADMIN);
        userRepo.save(pawel);

        User jaclyn = new User(902922234L,"Jaclyn","Jaclyn","Adams", User.Role.ADMIN);
        userRepo.save(jaclyn);

        User joe = new User(903124450L,"Joe","Joe","Egan", User.Role.ADMIN);
        userRepo.save(joe);
    }

}