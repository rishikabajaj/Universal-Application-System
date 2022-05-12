package csci5308.fall21.appHub.database.dao.user;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import csci5308.fall21.appHub.model.user.User;

public interface IUserDao {

     /**
      *
      * @param user
      * @return
      * @throws SQLException
      * @throws NoSuchAlgorithmException
      *
      * @author Monil Hitesh Andharia
      */
     public int addUser(User user) throws SQLException, NoSuchAlgorithmException;

     /**
      *
      * @param id
      * @return
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public int deleteUser(String id) throws SQLException;

     /**
      *
      * @param id
      * @return
      * @throws SQLException
      */
     public User getUser(String id) throws SQLException;

     /**
      * @return
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public List<User> getAllUsers() throws SQLException;

     /**
      * @param user
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public User updateUser(User user) throws SQLException;

     /**
      *
      * @param user
      * @return
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public User updateResetToken(User user) throws SQLException;

     /**
      *
      * @param user
      * @return
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public User updatePassword(User user) throws SQLException;

     /**
      *
      * @param email
      * @return
      * @throws SQLException
      *
      * @author Monil Hitesh Andharia
      */
     public boolean userExists(String email) throws SQLException;

}
