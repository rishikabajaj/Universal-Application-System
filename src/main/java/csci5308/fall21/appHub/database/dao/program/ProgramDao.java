package csci5308.fall21.appHub.database.dao.program;

import csci5308.fall21.appHub.database.helper.DaoHelper;
import csci5308.fall21.appHub.model.program.ProgramModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgramDao extends DaoHelper implements IProgramDao {
    final String TABLE_NAME = "program";

    public ProgramDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        super(jdbcTemplate, dataSource);
    }

    @Override
    public List<ProgramModel> getProgram(String university_name) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE university_name= ?";
        PreparedStatement preparedStatement = createPrepareStatement(query, university_name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProgramModel> program = new ArrayList<ProgramModel>();

        while (resultSet.next()) {
            program.add(new ProgramModel(resultSet.getString("program_name"), resultSet.getString("course"),
                    resultSet.getString("fees"), resultSet.getString("duration"),
                    resultSet.getString("application_fees"),
                    resultSet.getString("deadline"), resultSet.getString("requirements"),
                    resultSet.getString("university_name")));
        }
        return program;
    }

    @Override
    public List<ProgramModel> getAllPrograms() throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProgramModel> programs = new ArrayList<ProgramModel>();

        while (resultSet.next()) {
            programs.add(new ProgramModel(resultSet.getString("program_name"), resultSet.getString("course"),
                    resultSet.getString("fees"), resultSet.getString("duration"),
                    resultSet.getString("application_fees"),
                    resultSet.getString("deadline"), resultSet.getString("requirements"),
                    resultSet.getString("university_name")));
        }
        return programs;

    }

    @Override
    public int addProgramInfo(ProgramModel program) throws SQLException, NoSuchAlgorithmException {
        String query = "INSERT INTO " + TABLE_NAME
                + " (program_name,course,fees,duration,application_fees,deadline,requirements,university_name) VALUES (?, ?, ?, ?, ?, ?, ?,'DALU')";
        PreparedStatement preparedStatement = createPrepareStatement(query, program.getProgramName(),
                program.getCourse(), program.getFees(), program.getDuration(), program.getApplicationFees(),
                program.getDeadline(), program.getRequirements());
        return preparedStatement.executeUpdate();
    }

}