package csci5308.fall21.appHub.controller.application;

import csci5308.fall21.appHub.model.application.Notification;
import csci5308.fall21.appHub.service.application.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class NotificationController {

    @Autowired
    INotificationService notificationService;

    /**
     * This will store the notification in the database
     * 
     * @param notification pass the notification object body
     * @return HttpStatus will be returned
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @PostMapping(path = "/api/notification")
    public ResponseEntity<Object> postNotification(@RequestBody Notification notification) throws SQLException {
        Map<String, String> response = new HashMap<>();
        notification.setId();
        HttpStatus status = notificationService.addNotificationDetails(notification);

        if (status == HttpStatus.OK) {
            response.put("message", "Notification added Successfully");
        } else if (status == HttpStatus.BAD_REQUEST) {
            response.put("message", "Failed: No notification added");
            return ResponseEntity.status(status).body(response);
        } else {
            response.put("message", "Error while processing");
        }
        return ResponseEntity.status(status).body(response);
    }

    /**
     * This will get the notification of particular id from the database
     * 
     * @param applicationId provide applicationId
     * @return List of the notification with HttpStatus
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @GetMapping(path = "/api/notification/{applicationId}")
    public ResponseEntity<Object> getAnnouncementById(@PathVariable(value = "applicationId") String applicationId) {

        List<Notification> notifications = notificationService.getNotificationDetailsByApplicationId(applicationId);
        Map<String, String> response = new HashMap<>();
        if (notifications.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No notification is available for this Id");
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(notifications);
            } catch (Exception ex) {
                response.put("message", "Error while fetching notifications");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

    }

}
