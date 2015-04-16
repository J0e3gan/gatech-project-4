package gatech.course.optimizer.dto;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.Professor;
import gatech.course.optimizer.model.Semester;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

/**
 * Created by Joe Egan on 4/15/15.
 */
public class ProfessorDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Course> competencies;
    private List<Semester> availability;

    public ProfessorDTO() {
    }

    public static ProfessorDTO fromProfessor(Professor professor) {
        ModelMapper modelMapper = new ModelMapper();
        ProfessorDTO dto = modelMapper.map(professor, ProfessorDTO.class);
        return dto;
    }

    public Professor toProfessor() {
        ModelMapper modelMapper = new ModelMapper();
        Professor professor = modelMapper.map(this, Professor.class);
        return professor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Course> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(Set<Course> competencies) {
        this.competencies = competencies;
    }

    public List<Semester> getAvailability() {
        return this.availability;
    }

    public void setAvailability(List<Semester> availability) {
        this.availability = availability;
    }
}
