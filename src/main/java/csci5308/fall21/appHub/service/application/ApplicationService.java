package csci5308.fall21.appHub.service.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.database.dao.application.IApplicationDocumentDao;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

@Service
public class ApplicationService implements IApplicationService {
    private final static List<String> ALLOWED_FILE_TYPES = new ArrayList<String>() {
        {
            add("image/jpg");
            add("image/jpeg");
            add("image/png");
            add("application/pdf");
        }
    };

    @Autowired
    private IApplicationDocumentDao applicationDocumentDao;

    @Autowired
    IUserDao userDao;

    @Override
    public ApplicationDocument getDocument(String applicationId, String id) throws SQLException {
        return applicationDocumentDao.getApplicationDocument(applicationId, id);
    }

    @Override
    public Stream<ApplicationDocument> getAllDocuments(String applicationId) throws SQLException {
        return applicationDocumentDao.getAllApplicationDocuments(applicationId).stream();
    }

    @Override
    public boolean applicationExists(String applicantId, String applicationId) throws SQLException {
        List<Application> applications = applicationDocumentDao.getApps(applicantId);
        Optional<Application> applicationExists = applications.stream()
                .filter(application -> application.getId().equalsIgnoreCase(applicationId))
                .findFirst();

        return applicationExists.isPresent();
    }

    @Override
    public List<Application> getApplications(String applicantId) throws SQLException {
        List<User> users = userDao.getAllUsers();
        Optional<User> applicantExists = users.stream().filter(appli -> appli.getId().equalsIgnoreCase(applicantId))
                .findFirst();
        if (applicantExists.isPresent()) {
            return applicationDocumentDao.getApps(applicantId);
        }
        return null;
    }

    @Override
    public HttpStatus addRequirementFile(MultipartFile file, String applicationId, String applicationRequirementId,
            String applicantId)
            throws IOException, SQLException {
        if (file.getSize() == 0) {
            return HttpStatus.EXPECTATION_FAILED;
        }
        if (!ALLOWED_FILE_TYPES.contains(file.getContentType())) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        ApplicationDocument applicationDocument = applicationDocumentDao.getApplicationDocument(applicationId,
                applicationRequirementId);

        if (applicationDocument == null) {
            return HttpStatus.NOT_FOUND;
        }

        applicationDocument.setDocumentName(fileName);
        applicationDocument.setType(file.getContentType());
        applicationDocument.setData(file.getBytes());

        try {
            if (!applicationExists(applicantId, applicationId)) {
                return HttpStatus.NOT_FOUND;
            }
            if (applicationDocumentDao.addRequirementDocument(applicationDocument) > 0) {
                return HttpStatus.OK;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public HttpStatus addRequirementDetails(String title, String description, String applicationId, String applicantId)
            throws IOException {
        String id = UUID.randomUUID().toString();
        ApplicationDocument applicationDocument = new ApplicationDocument(id, applicationId, title, description);

        try {
            if (!applicationExists(applicantId, applicationId)) {
                return HttpStatus.NOT_FOUND;
            }
            if (applicationDocumentDao.addRequirementDetails(applicationDocument) > 0) {
                return HttpStatus.OK;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}