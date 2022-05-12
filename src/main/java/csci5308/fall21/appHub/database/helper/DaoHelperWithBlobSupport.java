package csci5308.fall21.appHub.database.helper;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DaoHelperWithBlobSupport extends DaoHelper {

    public DaoHelperWithBlobSupport(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    /**
     * @param sql
     * @param stringParams
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPrepareStatement(String sql, Blob blobParam, String... stringParams)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        preparedStatement.setBlob(counter, blobParam);
        for (String param : stringParams) {
            counter++;
            preparedStatement.setString(counter, param);
        }
        return preparedStatement;
    }

}
