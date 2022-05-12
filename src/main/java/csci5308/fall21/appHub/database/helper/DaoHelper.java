package csci5308.fall21.appHub.database.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import csci5308.fall21.appHub.database.DatabaseConnection;
import csci5308.fall21.appHub.database.IDatabaseConnection;

public abstract class DaoHelper extends JdbcDaoSupport implements IDaoHelper {

    public static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public DaoHelper(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            IDatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPrepareStatement(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int counter = 1;
        for (String param : params) {
            preparedStatement.setString(counter, param);
            counter++;
        }
        return preparedStatement;
    }

}
