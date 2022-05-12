package csci5308.fall21.appHub.service.application;

import csci5308.fall21.appHub.model.application.Notification;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.List;

public interface INotificationService {
    HttpStatus addNotificationDetails(Notification notification) throws SQLException;

    List<Notification> getNotificationDetailsByApplicationId(String applicationId);
}
