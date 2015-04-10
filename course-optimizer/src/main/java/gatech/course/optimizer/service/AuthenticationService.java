package gatech.course.optimizer.service;

import gatech.course.optimizer.exception.WebServiceException;
import gatech.course.optimizer.model.User;
import gatech.course.optimizer.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    User authenticate(@RequestParam("username") String username,
                      @RequestParam("password") String password) {


        if (username == null || password == null) {
            throw new WebServiceException("Missing username or password", HttpStatus.BAD_REQUEST);
        }

        logger.info("Login attempt 'username'" + username + " 'password='" + password);
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new WebServiceException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new WebServiceException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

}