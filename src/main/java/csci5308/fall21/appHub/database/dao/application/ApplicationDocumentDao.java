package csci5308.fall21.appHub.database.dao.application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import csci5308.fall21.appHub.database.helper.DaoHelperWithBlobSupport;
import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;
import csci5308.fall21.appHub.util.Utility;

@Repository
public class ApplicationDocumentDao extends DaoHelperWithBlobSupport implements IApplicationDocumentDao {
    final String TABLE_NAME = "application_documents";

    public ApplicationDocumentDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    @Override
    public int addRequirementDocument(ApplicationDocument applicationDocument) throws SQLException {
        String query = "UPDATE " + TABLE_NAME
                + " SET data = ?, document_name = ?, type = ? WHERE application_id = ? AND id = ?";
        PreparedStatement preparedStatement = createPrepareStatement(query,
                new SerialBlob(applicationDocument.getData()),
                applicationDocument.getDocumentName(),
                applicationDocument.getType(),
                applicationDocument.getApplicationId(),
                applicationDocument.getId());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int addRequirementDetails(ApplicationDocument applicationDocument) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME
                + " (id, application_id, description, title) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicationDocument.getId(),
                applicationDocument.getApplicationId(),
                applicationDocument.getDescription(),
                applicationDocument.getTitle());
        return preparedStatement.executeUpdate();
    }

    @Override
    public ApplicationDocument getApplicationDocument(String applicationId, String id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id= ? AND application_id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, id, applicationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ApplicationDocument applicationDocument = null;
        while (resultSet.next()) {
            byte[] blobBytes = new byte[0];
            if (resultSet.getBlob("data") != null) {
                Utility.blobToBytes(resultSet.getBlob("data"));
            }

            applicationDocument = new ApplicationDocument(resultSet.getString("id"),
                    resultSet.getString("application_id"), resultSet.getString("title"),
                    resultSet.getString("description"), resultSet.getString("document_name"),
                    resultSet.getString("type"),
                    blobBytes);
        }
        return applicationDocument;
    }

    @Override
    public List<ApplicationDocument> getAllApplicationDocuments(String applicationId) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE application_id= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ApplicationDocument> users = new ArrayList<ApplicationDocument>();

        while (resultSet.next()) {
            byte[] blobBytes = new byte[0];
            if (resultSet.getBlob("data") != null) {
                Utility.blobToBytes(resultSet.getBlob("data"));
            }

            users.add(new ApplicationDocument(resultSet.getString("id"), resultSet.getString("application_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"), resultSet.getString("document_name"),
                    resultSet.getString("type"),
                    blobBytes));
        }
        return users;
    }

    /**
     *
     * @author Pallavi Cherukupalli
     */

    @Override
    public List<Application> getApps(String applicantId) throws SQLException {
        String tableName = "application";
        String query = "SELECT * FROM " + tableName
                + " WHERE user_id= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, applicantId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Application> applications = new ArrayList<Application>();

        while (resultSet.next()) {
            applications.add(new Application(resultSet.getString("id"), resultSet.getString("status"),
                    resultSet.getString("user_id"), resultSet.getString("program_name"),
                    resultSet.getString("university_name")));
        }
        return applications;
    }

}
