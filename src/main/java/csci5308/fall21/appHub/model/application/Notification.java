package csci5308.fall21.appHub.model.application;

import java.util.UUID;

public class Notification {
    private String id;
    private String title;
    private String description;
    private String applicationId;

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param applicationId
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public Notification(String id, String title, String description, String applicationId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.applicationId = applicationId;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}
