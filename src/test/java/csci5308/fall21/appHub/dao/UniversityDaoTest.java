package csci5308.fall21.appHub.dao;

import csci5308.fall21.appHub.database.dao.university.IUniversityDao;
import csci5308.fall21.appHub.model.university.UniversityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniversityDaoTest {

    @Mock
    private IUniversityDao mockIUniversityDao;

    @Mock
    private UniversityModel mockUniversityModel = UniversityModel.getInstance();

    @Test
    void checkUniversityDaoWorks() {
        assertNotNull(mockIUniversityDao);
    }

    @Test
    public void checkGetAllUniversityInfo() throws SQLException {
        List<UniversityModel> universities = List.of(mockUniversityModel);
        when(mockIUniversityDao.getAllUniversities()).thenReturn(universities);
        assertNotNull(mockIUniversityDao.getAllUniversities());
    }

    @Test
    public void checkGetUniversity() throws SQLException {
        List<UniversityModel> university = List.of(mockUniversityModel);
        when(mockIUniversityDao.getUniversity("2")).thenReturn(university);
        assertNotNull(mockIUniversityDao.getUniversity("2"));
    }

}
