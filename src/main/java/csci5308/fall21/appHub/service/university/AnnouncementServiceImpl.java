package csci5308.fall21.appHub.service.university;

import csci5308.fall21.appHub.database.dao.university.IAnnouncementDao;
import csci5308.fall21.appHub.model.university.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements IAnnouncementService {

    @Autowired
    IAnnouncementDao announcementDao;

    /**
     * will call the method to get all the announcements from the database
     * 
     * @return List of announcement
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public List<Announcement> getAnnouncementsDetails() {
        try {
            return announcementDao.getAllAnnouncement();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * will call the method to get the announcement by Id from the database
     * 
     * @param announcementId
     * @return announcement object
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public Announcement getAnnouncementDetailsById(String announcementId) {
        try {
            return announcementDao.getAnnouncementById(announcementId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * will call the method to add the announcement to the database
     * 
     * @param announcement pass the body of announcement
     * @return Httpstatus
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public HttpStatus addAnnouncementDetails(Announcement announcement) throws SQLException {
        return announcementDao.addAnnouncement(announcement);
    }

    /**
     * will call the method to delete the announcement from the database
     * 
     * @param announcementId pass the announcementId
     * @return HttpStatus
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public HttpStatus deleteAnnouncementDetailsById(String announcementId) throws SQLException {
        return announcementDao.deleteAnnouncementById(announcementId);
    }

}
