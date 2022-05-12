package csci5308.fall21.appHub.database.dao.application;

import csci5308.fall21.appHub.database.helper.DaoHelper;
import csci5308.fall21.appHub.model.application.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationDao extends DaoHelper implements INotificationDao {
    final String TABLE_NAME = "notification";

    public NotificationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    /**
     * add notification to the database
     * 
     * @param notification
     * @return Httpstatus
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public HttpStatus addNotification(Notification notification) {
        try {
            String query = "INSERT INTO " + TABLE_NAME
                    + " (id, title, description, application_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = createPrepareStatement(query, notification.getId(),
                    notification.getTitle(),
                    notification.getDescription(), notification.getApplicationId());
            preparedStatement.executeUpdate();
            return HttpStatus.OK;
        } catch (SQLException e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    /**
     * get all the notification for particular applicationId
     * 
     * @param id
     * @return list of the notification
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @Override
    public List<Notification> getNotificationByApplicationId(String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE application_id= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Notification> notifications = new ArrayList<>();

        while (resultSet.next()) {
            notifications.add(new Notification(resultSet.getString("id"), resultSet.getString("title"),
                    resultSet.getString("description"), resultSet.getString("application_id")));
        }
        return notifications;
    }

}
