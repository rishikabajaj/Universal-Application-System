package csci5308.fall21.appHub.service.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

public interface IApplicationService {

        /**
         *
         * @param file
         * @param applicationId
         * @param applicationRequirementId
         * @return
         * @throws IOException
         * @throws SQLException
         *
         * @author Monil Hitesh Andharia
         */
        public HttpStatus addRequirementFile(MultipartFile file, String applicationId, String applicationRequirementId,
                        String applicantId)
                        throws IOException, SQLException;

        /**
         *
         * @param title
         * @param description
         * @param applicationId
         * @return
         * @throws IOException
         *
         * @author Monil Hitesh Andharia
         */
        public HttpStatus addRequirementDetails(String title, String description, String applicationId,
                        String applicantId)
                        throws IOException;

        /**
         *
         * @param applicationId
         * @param id
         * @return
         * @throws SQLException
         *
         * @author Monil Hitesh Andharia
         */
        public ApplicationDocument getDocument(String applicationId, String id) throws SQLException;

        /**
         *
         * @param applicationId
         * @return
         * @throws SQLException
         *
         * @author Monil Hitesh Andharia
         */
        public Stream<ApplicationDocument> getAllDocuments(String applicationId) throws SQLException;

        public List<Application> getApplications(String applicantId) throws SQLException;

        public boolean applicationExists(String applicantId, String applicationId) throws SQLException;
}
