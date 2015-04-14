package gatech.course.optimizer.conf;

import gatech.course.optimizer.model.Constraint;
import gatech.course.optimizer.model.User;
import gatech.course.optimizer.repo.ConstraintRepo;
import gatech.course.optimizer.repo.UserRepo;
import gatech.course.optimizer.utils.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Created by 204069126 on 4/9/15.
 */
@Component
public class OptimizerConfiguration {

    public static Logger logger = LoggerFactory.getLogger(OptimizerConfiguration.class);

    @Autowired
    private DataLoader loader;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConstraintRepo constraintRepo;

    @Value("${load.from.csv}")
    private boolean loadFromCSV;

    public boolean isLoadFromCSV() {
        return loadFromCSV;
    }

    public void setLoadFromCSV(boolean loadFromCSV) {
        this.loadFromCSV = loadFromCSV;
    }


    @PostConstruct
    public void loadData() {
        if (loadFromCSV == true) {
            createSuperAdminUsers();
            logger.info("Loading data from CSV files into the database ...");
            InputStream courseFile = this.getClass().getResourceAsStream("/courses.csv");
            InputStream studentsFile = this.getClass().getResourceAsStream("/OMS-CS-CourseGrades_Final_Subset.csv");
            loader.loadData(studentsFile,courseFile);

            // Create sample constraints
            constraintRepo.save(new Constraint("MAX_COURSE_PER_SEMSESTER","Student can only take a certain amount of courses per semester", "3"));
            constraintRepo.save(new Constraint("SMARTNESS_LEVEL","Students must be smart to attend GA-Tech", "IQ of at least 120"));

        } else {
            logger.info("Loading data from CSV files skipped");
        }
    }


    public void createSuperAdminUsers() {
        User john = new User("John", "John", "John", "Raffensperger", User.Role.ADMIN);
        userRepo.save(john);

        User pawel = new User("Pawel", "Pawel", "Pawel", "Drozdz", User.Role.ADMIN);
        userRepo.save(pawel);

        User jaclyn = new User("Jaclyn", "Jaclyn", "Jaclyn", "Adams", User.Role.ADMIN);
        userRepo.save(jaclyn);

        User joe = new User("Joe", "Joe", "Joe", "Egan", User.Role.ADMIN);
        userRepo.save(joe);
    }

}
