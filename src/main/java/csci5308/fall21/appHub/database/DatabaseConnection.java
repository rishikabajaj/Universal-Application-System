package csci5308.fall21.appHub.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseConnection implements IDatabaseConnection {

    /**
     *
     * @param jdbcTemplate
     * @return
     * @throws SQLException
     *
     * @author Monil Hitesh Andharia
     */
    @Override
    public Connection getConnection(JdbcTemplate jdbcTemplate) throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

}
