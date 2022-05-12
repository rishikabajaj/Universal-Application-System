package csci5308.fall21.appHub.service.user;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;

import csci5308.fall21.appHub.model.user.User;

public interface IUserService {

    /**
     *
     * @return
     *
     * @author Monil Hitesh Andharia
     */
    public User getUser();

    /**
     *
     * @author Monil Hitesh Andharia
     *
     * @param email
     * @param securityAnswer
     * @return
     *
     * @author Monil Hitesh Andharia
     */
    public HttpStatus generateResetToken(String email, String securityAnswer);

    /**
     *
     * @param email
     * @param newPassword
     * @param resetPasswordToken
     * @return
     *
     * @author Monil Hitesh Andharia
     */
    public HttpStatus resetPassword(String email, String newPassword, String resetPasswordToken);

    /**
     *
     * @param email
     * @param password
     * @return
     *
     * @author Monil Hitesh Andharia
     */
    public HttpStatus userLogin(String email, String password);

    /**
     *
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     *
     * @author Monil Hitesh Andharia
     */
    public HttpStatus userRegistration(User user) throws NoSuchAlgorithmException;
}
