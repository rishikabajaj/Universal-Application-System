package csci5308.fall21.appHub.dao;

import csci5308.fall21.appHub.database.dao.program.IProgramDao;
import csci5308.fall21.appHub.model.program.ProgramModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramDaoTest {

    @Mock
    private IProgramDao mockIProgramDao;

    @Mock
    private ProgramModel mockProgramModel = ProgramModel.getInstance();

    @Test
    void programDaoWorks() {
        assertNotNull(mockIProgramDao);
    }

    @Test
    public void checkAddProgramInfo() throws NoSuchAlgorithmException, SQLException {
        when(mockIProgramDao.addProgramInfo(mockProgramModel)).thenReturn(1);
        Assertions.assertEquals(1, mockIProgramDao.addProgramInfo(mockProgramModel));
    }

    @Test
    public void checkGetAllPrograms() throws NoSuchAlgorithmException, SQLException {
        List<ProgramModel> programs = List.of(mockProgramModel);
        when(mockIProgramDao.getAllPrograms()).thenReturn(programs);
        assertNotNull(mockIProgramDao.getAllPrograms());
    }

    @Test
    public void checkGetProgram() throws SQLException {
        List<ProgramModel> program = List.of(mockProgramModel);
        when(mockIProgramDao.getProgram("DALU")).thenReturn(program);
        assertNotNull(mockIProgramDao.getProgram("DALU"));
    }

}
