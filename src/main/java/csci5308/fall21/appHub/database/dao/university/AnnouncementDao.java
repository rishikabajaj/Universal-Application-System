package csci5308.fall21.appHub.database.dao.university;

import csci5308.fall21.appHub.database.helper.DaoHelper;
import csci5308.fall21.appHub.model.university.Announcement;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Repository
public class AnnouncementDao extends DaoHelper implements IAnnouncementDao {
    final String TABLE_NAME = "announcement";

    public AnnouncementDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    /**
     * add announcement to the database
     * 
     * @param announcement
     * @return Httpstatus
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public HttpStatus addAnnouncement(Announcement announcement) throws SQLException {
        try {
            String query = "INSERT INTO " + TABLE_NAME
                    + " (id, title, datetime, description, university_name) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = createPrepareStatement(query, announcement.getId(),
                    announcement.getTitle(),
                    announcement.getDatetime(), announcement.getDescription(), announcement.getUniversityName());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.BAD_REQUEST;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    /**
     * Get All the Announcement from database
     * 
     * @return
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public List<Announcement> getAllAnnouncement() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Announcement> announcements = new ArrayList<Announcement>();

        while (resultSet.next()) {
            announcements.add(new Announcement(resultSet.getString("id"), resultSet.getString("title"),
                    resultSet.getString("datetime"),
                    resultSet.getString("description"), resultSet.getString("university_name")));
        }
        return announcements;
    }

    /**
     * Get announcement with particular id
     * 
     * @param id
     * @return
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public Announcement getAnnouncementById(String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Announcement announcement = null;
        while (resultSet.next()) {
            announcement = new Announcement(resultSet.getString("id"), resultSet.getString("title"),
                    resultSet.getString("datetime"),
                    resultSet.getString("description"), resultSet.getString("university_name"));
        }
        return announcement;
    }

    /**
     * Delete the announcement by particular id
     * 
     * @param id
     * @return
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public HttpStatus deleteAnnouncementById(String id) throws SQLException {
        try {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
            PreparedStatement preparedStatement = createPrepareStatement(query, id);
            int count = preparedStatement.executeUpdate();
            if (count == 0) {
                return HttpStatus.BAD_REQUEST;
            } else {
                return HttpStatus.OK;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

}
