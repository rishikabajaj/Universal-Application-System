package csci5308.fall21.appHub.service.application;

import csci5308.fall21.appHub.database.dao.application.INotificationDao;
import csci5308.fall21.appHub.model.application.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    INotificationDao notificationDao;

    /**
     * will call the method to add the notification to the database
     * 
     * @param notification
     * @return Httpstatus
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public HttpStatus addNotificationDetails(Notification notification) throws SQLException {
        return notificationDao.addNotification(notification);
    }

    /**
     * will call the method to get all the notifications for particular applicantion
     * to the database
     * 
     * @param applicationId
     * @return List of all the notifications
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    public List<Notification> getNotificationDetailsByApplicationId(String applicationId) {
        try {
            return notificationDao.getNotificationByApplicationId(applicationId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
