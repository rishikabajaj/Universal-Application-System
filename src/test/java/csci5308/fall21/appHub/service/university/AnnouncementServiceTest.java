package csci5308.fall21.appHub.service.university;

import csci5308.fall21.appHub.database.dao.university.IAnnouncementDao;
import csci5308.fall21.appHub.model.university.Announcement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {

    @Autowired
    IAnnouncementService announcementService;

    @Mock
    IAnnouncementDao mockAnnouncementDao;

    @InjectMocks
    AnnouncementServiceImpl mockAnnouncementService;

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

    private Announcement mockAnnouncementDetails() {
        return new Announcement("5", "TestTitle", "TestCase checking", "2021-10-10", "DALU");
    }

    @Test
    void testGetAnnouncementDetailsWorks() throws SQLException {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(mockAnnouncementDetails());
        announcements.add(mockAnnouncementDetails());

        when(mockAnnouncementDao.getAllAnnouncement()).thenReturn(announcements);
        List<Announcement> announcements1 = mockAnnouncementService.getAnnouncementsDetails();
        assertEquals(announcements, announcements1);
    }

    @Test
    void testGetAnnouncementDetailsExceptionWorks() throws SQLException {
        when(mockAnnouncementDao.getAllAnnouncement()).thenThrow(SQLException.class);
        List<Announcement> announcements1 = mockAnnouncementService.getAnnouncementsDetails();
        assertEquals(announcements1.size(), 0);
    }

    @Test
    void testGetAnnouncementByIdWorks() throws SQLException {
        String id = "5";
        Announcement announcement = mockAnnouncementDetails();
        announcement.setId(id);

        when(mockAnnouncementDao.getAnnouncementById(id)).thenReturn(announcement);
        Announcement fetchedAnnouncement = mockAnnouncementService.getAnnouncementDetailsById(id);

        assertEquals(id, fetchedAnnouncement.getId(), "Wrong announcement was returned");

    }

    @Test
    void testGetAnnouncementByIdExceptionWorks() throws SQLException {
        String id = "5";
        Announcement announcement = mockAnnouncementDetails();
        announcement.setId(id);

        when(mockAnnouncementDao.getAnnouncementById(id)).thenThrow(SQLException.class);
        Announcement fetchedAnnouncement = mockAnnouncementService.getAnnouncementDetailsById(id);

        assertNull(fetchedAnnouncement);

    }

    @Test
    void testGetAnnouncementByIdFails() {
        assertNull(mockAnnouncementService.getAnnouncementDetailsById("5"));
    }

    @Test
    void testAddAnnouncementDetailsWorks() throws SQLException {
        Announcement announcement = mockAnnouncementDetails();
        when(mockAnnouncementDao.addAnnouncement(announcement)).thenReturn(HttpStatus.OK);
        HttpStatus status = mockAnnouncementService.addAnnouncementDetails(announcement);
        assertEquals(HttpStatus.OK, status, "Add announcement Failed");
    }

    @Test
    void testAddAnnouncementDetailsFails() throws SQLException {
        Announcement announcement = mockAnnouncementDetails();
        announcement.setUniversityName("HI");
        when(mockAnnouncementDao.addAnnouncement(announcement)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        HttpStatus status = mockAnnouncementService.addAnnouncementDetails(announcement);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status, "Error while testing");
    }

    @Test
    void testDeleteAnnouncementByIdWorks() throws SQLException {
        String id = "5";
        Announcement announcement = mockAnnouncementDetails();
        announcement.setId(id);

        when(mockAnnouncementDao.deleteAnnouncementById(id)).thenReturn(HttpStatus.OK);
        HttpStatus status = mockAnnouncementService.deleteAnnouncementDetailsById(id);

        assertEquals(HttpStatus.OK, status, "announcement deletion failed.");
    }

    @Test
    void testDeleteAnnouncementByIdFails() throws SQLException {
        String id = "5";
        when(mockAnnouncementDao.deleteAnnouncementById(id)).thenReturn(HttpStatus.BAD_REQUEST);
        HttpStatus status = mockAnnouncementService.deleteAnnouncementDetailsById(id);
        assertEquals(HttpStatus.BAD_REQUEST, status, "Failed test case");
    }

}
