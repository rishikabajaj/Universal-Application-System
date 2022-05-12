package csci5308.fall21.appHub.service.application;

import csci5308.fall21.appHub.database.dao.application.INotificationDao;
import csci5308.fall21.appHub.model.application.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Autowired
    INotificationService notificationService;

    @Mock
    INotificationDao mockNotificationDao;

    @InjectMocks
    NotificationServiceImpl mockNotificationService;

    Notification notification;

    @BeforeEach
    public void setup() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void teardown() {
        RequestContextHolder.resetRequestAttributes();
    }

    private Notification mockNotificationDetails() {
        return new Notification("50", "TestTitle", "TestCase checking", "5");
    }

    @Test
    void testAddNotificationDetailsWorks() throws SQLException {
        Notification notification = mockNotificationDetails();
        when(mockNotificationDao.addNotification(notification)).thenReturn(HttpStatus.OK);
        HttpStatus status = mockNotificationService.addNotificationDetails(notification);
        assertEquals(HttpStatus.OK, status, "Add notification Failed");
    }

    @Test
    void testAddNotificationDetailsFails() throws SQLException {
        Notification notification = mockNotificationDetails();
        notification.setApplicationId("55");
        when(mockNotificationDao.addNotification(notification)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        HttpStatus status = mockNotificationService.addNotificationDetails(notification);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status, "Error while testing");
    }

    @Test
    void testGetNotificationByApplicationIdWorks() throws SQLException {
        String id = "5";
        List<Notification> notifications = new ArrayList<>();
        notification = mockNotificationDetails();
        notification.setApplicationId(id);
        notifications.add(notification);
        notifications.add(notification);

        when(mockNotificationDao.getNotificationByApplicationId(id)).thenReturn(notifications);
        List<Notification> fetchNotifications = mockNotificationService.getNotificationDetailsByApplicationId(id);
        assertEquals(fetchNotifications.size(), notifications.size(), "Wrong notifications is returned");

    }

    @Test
    void testGetNotificationByApplicationIdExceptionWorks() throws SQLException {
        String id = "5";
        when(mockNotificationDao.getNotificationByApplicationId(id)).thenThrow(SQLException.class);
        List<Notification> fetchNotifications = mockNotificationService.getNotificationDetailsByApplicationId(id);
        assertEquals(fetchNotifications.size(), 0);

    }

}
