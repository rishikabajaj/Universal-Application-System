package csci5308.fall21.appHub.database.dao.university;

import csci5308.fall21.appHub.model.university.Announcement;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.List;

public interface IAnnouncementDao {

    HttpStatus addAnnouncement(Announcement announcement) throws SQLException;

    List<Announcement> getAllAnnouncement() throws SQLException;

    Announcement getAnnouncementById(String id) throws SQLException;

    HttpStatus deleteAnnouncementById(String id) throws SQLException;
}
