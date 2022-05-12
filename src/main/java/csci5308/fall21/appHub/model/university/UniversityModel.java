package csci5308.fall21.appHub.model.university;

public class UniversityModel {
    private static final UniversityModel INSTANCE = new UniversityModel();
    private String universityName;
    private String location;

    public static UniversityModel getInstance() {
        return INSTANCE;
    }

    public UniversityModel() {

    }

    public UniversityModel(String universityName, String location) {
        this.universityName = universityName;
        this.location = location;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
