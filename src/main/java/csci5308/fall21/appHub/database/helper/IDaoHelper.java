package csci5308.fall21.appHub.database.helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IDaoHelper {
    public PreparedStatement createPrepareStatement(String sql, String... params) throws SQLException;
}
