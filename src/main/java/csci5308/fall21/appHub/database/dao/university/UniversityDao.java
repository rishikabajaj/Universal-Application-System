package csci5308.fall21.appHub.database.dao.university;

import csci5308.fall21.appHub.database.helper.DaoHelper;
import csci5308.fall21.appHub.model.university.UniversityModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UniversityDao extends DaoHelper implements IUniversityDao {
    final String TABLE_NAME = "university";

    public UniversityDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    @Override
    public List<UniversityModel> getAllUniversities() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UniversityModel> universities = new ArrayList<UniversityModel>();
        while (resultSet.next()) {
            universities
                    .add(new UniversityModel(resultSet.getString("university_name"), resultSet.getString("location")));
        }
        return universities;
    }

    @Override
    public List<UniversityModel> getUniversity(String user_id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id= ? LIMIT 1";
        PreparedStatement preparedStatement = createPrepareStatement(query, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UniversityModel> university = new ArrayList<>();
        while (resultSet.next()) {
            university
                    .add(new UniversityModel(resultSet.getString("university_name"), resultSet.getString("location")));
        }
        return university;

    }

}