package csci5308.fall21.appHub.database.dao.application;

import csci5308.fall21.appHub.model.application.Notification;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.List;

public interface INotificationDao {

    HttpStatus addNotification(Notification notification) throws SQLException;

    List<Notification> getNotificationByApplicationId(String id) throws SQLException;
}
