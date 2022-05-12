/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.database.dao.application;

import java.sql.SQLException;
import java.util.List;

import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

public interface IApplicationDocumentDao {

    /**
     *
     * @param applicationDocument
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    public int addRequirementDocument(ApplicationDocument applicationDocument) throws SQLException;

    /**
     *
     * @param applicationDocument
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    public int addRequirementDetails(ApplicationDocument applicationDocument) throws SQLException;

    /**
     *
     * @param applicationId
     * @param id
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    public ApplicationDocument getApplicationDocument(String applicationId, String id) throws SQLException;

    /**
     *
     * @param applicationId
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    public List<ApplicationDocument> getAllApplicationDocuments(String applicationId) throws SQLException;

    /**
     *
     * @param applicantId
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    public List<Application> getApps(String applicantId) throws SQLException;

}
