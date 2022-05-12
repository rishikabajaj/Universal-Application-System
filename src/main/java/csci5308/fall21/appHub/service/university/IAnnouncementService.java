package csci5308.fall21.appHub.service.university;

import csci5308.fall21.appHub.model.university.Announcement;
import org.springframework.http.HttpStatus;
import java.sql.SQLException;
import java.util.List;

public interface IAnnouncementService {
    List<Announcement> getAnnouncementsDetails();

    Announcement getAnnouncementDetailsById(String announcementId);

    HttpStatus addAnnouncementDetails(Announcement announcement) throws SQLException;

    HttpStatus deleteAnnouncementDetailsById(String announcementId) throws SQLException;
}
