package csci5308.fall21.appHub.model.university;

import java.util.UUID;

public class Announcement {

    private String id;
    private String title;
    private String description;
    private String datetime;
    private String universityName;

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param datetime
     * @param universityName
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public Announcement(String id, String title, String description, String datetime, String universityName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.universityName = universityName;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}
