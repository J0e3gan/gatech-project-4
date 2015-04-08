package gatech.course.optimizer.model;

import java.util.Set;

/**
 * Created by 204069126 on 2/6/15.
 */
//@Entity(name = "")
public class Course {

    private Long id;
    private String name;
    private String description;
    private String number;
    private Set<Course> prerequisites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
