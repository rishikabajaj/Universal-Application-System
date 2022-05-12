package csci5308.fall21.appHub.service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.database.dao.application.IApplicationDocumentDao;
import csci5308.fall21.appHub.model.ResponseFile;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

@SpringBootTest
public class ApplicationServiceTest {
    private final static String INVALID_ID = "1";
    private final static String VALID_ID = "3";
    private final static String APPLICANT_ID = UUID.randomUUID().toString();

    @Autowired
    IApplicationService applicationService;

    @MockBean
    IApplicationDocumentDao mockApplicationDocumentDao;

    @MockBean
    IUserDao userDao;

    ResponseFile responseFile;

    private User mockUserModel() {
        return new User("3", "Lorem", "Ipsum", "lorem@ipsum.com", "lorem@ipsum2021",
                "7827827822",
                "what is this qn?", "ans", "applicant", "", "");
    }

    private Application mockApplications(String id) {
        return new Application(id, "approved", "3", "MACS", "Dal");
    }

    private MockMultipartFile mockEmptyFile() {

        try {
            String fileContent = "";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "empty-file",
                    "empty-file.pdf",
                    MediaType.APPLICATION_PDF_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MockMultipartFile mockMultipartFile() {

        try {
            String fileContent = "This is content in file.";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "valid-file",
                    "valid-file.pdf",
                    MediaType.APPLICATION_PDF_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MockMultipartFile mockInvalidMultipartFileType() {

        try {
            String fileContent = "This is content in file.";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    "invalid-file-type",
                    "invalid-file-type.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    fileContent.getBytes());
            return mockMultipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ApplicationDocument mockApplicationRequirementDetails() {
        String id = UUID.randomUUID().toString();
        String applicationId = UUID.randomUUID().toString();
        String title = "Requirement 1";
        String description = "This is required for application evaluation";

        return new ApplicationDocument(id, applicationId, title, description);
    }

    @Test
    void applicationServiceWorks() throws IOException {
        assertNotNull(applicationService);
    }

    @Test
    void savingEmptyApplicationDocument() throws IOException, SQLException {
        MultipartFile emptyFile = mockEmptyFile();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();

        assertEquals(HttpStatus.EXPECTATION_FAILED,
                applicationService.addRequirementFile(emptyFile, applicationDocument.getApplicationId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void invalidFileTypeApplicationDocument() throws IOException, SQLException {
        MultipartFile invalidFileType = mockInvalidMultipartFileType();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
                applicationService.addRequirementFile(invalidFileType, applicationDocument.getApplicationId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void savingApplicationDocumentToDatabaseFailure() throws IOException,
            SQLException {
        MultipartFile validFile = mockMultipartFile();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));

        when(mockApplicationDocumentDao.getApps(anyString())).thenReturn(applications);

        when(mockApplicationDocumentDao.getApplicationDocument(anyString(), anyString()))
                .thenReturn(applicationDocument);
        when(mockApplicationDocumentDao.addRequirementDocument(any(ApplicationDocument.class))).thenReturn(0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                applicationService.addRequirementFile(validFile, applications.get(0).getId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void savingApplicationDocumentForInvalidDocumentId() throws IOException, SQLException {
        MultipartFile validFile = mockMultipartFile();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();

        when(mockApplicationDocumentDao.getApplicationDocument(anyString(), anyString()))
                .thenReturn(null);

        assertEquals(HttpStatus.NOT_FOUND,
                applicationService.addRequirementFile(validFile, applicationDocument.getApplicationId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void savingApplicationDocumentToDatabase() throws IOException, SQLException {
        MultipartFile validFile = mockMultipartFile();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));

        when(mockApplicationDocumentDao.getApps(anyString())).thenReturn(applications);

        when(mockApplicationDocumentDao.getApplicationDocument(anyString(), anyString()))
                .thenReturn(applicationDocument);
        when(mockApplicationDocumentDao.addRequirementDocument(any(ApplicationDocument.class))).thenReturn(1);

        assertEquals(HttpStatus.OK,
                applicationService.addRequirementFile(validFile, applications.get(0).getId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void savingApplicationDocumentToDatabaseException() throws IOException,
            SQLException {
        MultipartFile validFile = mockMultipartFile();
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));

        when(mockApplicationDocumentDao.getApps(anyString())).thenReturn(applications);

        when(mockApplicationDocumentDao.getApplicationDocument(anyString(), anyString()))
                .thenReturn(applicationDocument);
        when(mockApplicationDocumentDao.addRequirementDocument(any(ApplicationDocument.class)))
                .thenThrow(new SQLException());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                applicationService.addRequirementFile(validFile, applications.get(0).getId(),
                        applicationDocument.getId(), APPLICANT_ID));
    }

    @Test
    void testAddRequirementDetailsWork() throws SQLException, IOException {
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));

        when(mockApplicationDocumentDao.getApps(anyString())).thenReturn(applications);
        when(mockApplicationDocumentDao.addRequirementDetails(any(ApplicationDocument.class))).thenReturn(1);

        assertEquals(HttpStatus.OK,
                applicationService.addRequirementDetails(applicationDocument.getTitle(),
                        applicationDocument.getDescription(), applications.get(0).getId(), APPLICANT_ID));
    }

    @Test
    void testAddRequirementDetailsToDatabaseFailure() throws SQLException, IOException {
        ApplicationDocument applicationDocument = mockApplicationRequirementDetails();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));

        when(mockApplicationDocumentDao.getApps(anyString())).thenReturn(applications);
        when(mockApplicationDocumentDao.addRequirementDetails(any(ApplicationDocument.class))).thenReturn(0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                applicationService.addRequirementDetails(applicationDocument.getTitle(),
                        applicationDocument.getDescription(), applications.get(0).getId(), APPLICANT_ID));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     */
    @Test
    void getAllApplicationsWithInvalidId() throws SQLException {
        List<User> users = new ArrayList<>();
        User user = mockUserModel();
        users.add(user);
        when(userDao.getAllUsers()).thenReturn(users);
        assertEquals(null, applicationService.getApplications(INVALID_ID));
    }

    /**
     *
     * @author Pallavi Cherukupalli
     */
    @Test
    void getAllApplicationsWithValidId() throws SQLException {
        List<User> users = new ArrayList<>();
        List<Application> applications = new ArrayList<>();
        applications.add(mockApplications(VALID_ID));
        User user = mockUserModel();
        users.add(user);
        when(userDao.getAllUsers()).thenReturn(users);
        when(mockApplicationDocumentDao.getApps(VALID_ID)).thenReturn(applications);
        assertEquals(applications, applicationService.getApplications(VALID_ID));
    }

}
