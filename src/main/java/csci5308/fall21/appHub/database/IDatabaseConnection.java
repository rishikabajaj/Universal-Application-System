package csci5308.fall21.appHub.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IDatabaseConnection {
    public Connection getConnection(JdbcTemplate jdbcTemplate) throws SQLException;
}
