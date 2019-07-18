package tran.compbuildbackend.services.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.exceptions.request.UsernameRequestException;
import tran.compbuildbackend.exceptions.security.ChangePasswordTokenException;
import tran.compbuildbackend.exceptions.security.RequestChangePasswordException;
import tran.compbuildbackend.repositories.security.ChangePasswordTokenRepository;
import tran.compbuildbackend.repositories.security.EmailVerificationTokenRepository;
import tran.compbuildbackend.repositories.users.ApplicationUserRepository;

import static org.junit.Assert.*;
import static tran.compbuildbackend.constants.exception.ExceptionConstants.EXCEPTION_REQUEST_PASSWORD_CHANGE_FAILED;
import static tran.compbuildbackend.constants.exception.ExceptionConstants.EXCEPTION_USER_NAME_DOES_NOT_EXIST;
import static tran.compbuildbackend.constants.users.UserConstants.*;

@Profile({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUserServiceImplIntegrationTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private ApplicationUserService applicationUserService;

    @Before
    public void setup() {
        applicationUserService = new ApplicationUserServiceImpl(applicationUserRepository, bCryptPasswordEncoder,
                emailVerificationTokenRepository, changePasswordTokenRepository, eventPublisher);
    }

    @Test
    public void testPersistUserWithNoEmailToken() {
        /*
         * the below is testing the service to be able to create the user as well as send out an email while also creating
         * the EmailVerification token.
         */
        ApplicationUser user = createSampleTestUser(FULL_NAME_ONE, USER_NAME_DOES_NOT_EXIST_TWO, MODIFIED_PASSWORD);

        ApplicationUser userTwo = createSecondSampleTestUser(FULL_NAME_ONE, USER_NAME_ONE, MODIFIED_PASSWORD);

        ApplicationUser returnedUser = applicationUserService.persistUser(user, null);

        assertEquals(userTwo.getConfirmPassword(), returnedUser.getConfirmPassword());
        assertFalse(returnedUser.isEnabled());
        assertNull(returnedUser.getEmailVerificationToken()); // there is no email sent so no token is generated.
    }

    @Test(expected = GenericRequestException.class)
    public void testSendSuccessRegistrationEmailFail() {
        /*
         * tests the service method that attempts to send out an email but this wil thrown a custom exception
         * because there is no request.
         */
        ApplicationUser user = createSampleTestUser(FULL_NAME_ONE, USER_NAME_DOES_NOT_EXIST_TWO, MODIFIED_PASSWORD);
        applicationUserService.sendSuccessRegistrationEmail(user, null);
    }

    @Test
    public void testPersistUserWithSuccessfulEmail() {
        /*
         * the below is testing the service to be able to create the user but not send out the email so there is no token.
         */
        ApplicationUser user = createSampleTestUser(FULL_NAME_ONE, USER_NAME_TO_CREATE_NEW_USER, MODIFIED_PASSWORD);

        ApplicationUser userTwo = createSecondSampleTestUser(FULL_NAME_ONE, USER_NAME_ONE, MODIFIED_PASSWORD);

        ApplicationUser returnedUser = applicationUserService.persistUser(user, new MockHttpServletRequest());

        assertEquals(userTwo.getConfirmPassword(), returnedUser.getConfirmPassword());
        assertFalse(returnedUser.isEnabled());
        assertNotNull(returnedUser.getEmailVerificationToken());

    }

    private ApplicationUser createSampleTestUser(String fullName, String userName, String password) {
        ApplicationUser user = new ApplicationUser();
        user.setFullName(fullName);
        user.setUsername(userName);
        user.setPassword(password);
        user.setConfirmPassword(password);
        user.setEnabled(true); // if the user can set the user to be activated it is expected below that the created account is not enabled (activated).
        return user;
    }

    private ApplicationUser createSecondSampleTestUser(String fullName, String userName, String password) {
        ApplicationUser userTwo = new ApplicationUser();
        userTwo.setFullName(fullName);
        userTwo.setUsername(userName);
        userTwo.setPassword(password);
        userTwo.setConfirmPassword("");
        return userTwo;
    }

    @Test
    public void testEnableUser() {
        ApplicationUser user = applicationUserService.getUserByUserName(USER_NAME_TWO, EXCEPTION_USER_NAME_DOES_NOT_EXIST);
        assertFalse(user.isEnabled());

        applicationUserService.enableUser(user);
        assertTrue(user.isEnabled());
    }

    @Test(expected = UsernameRequestException.class)
    public void getUserByUserNameDoesNotExist() {
        applicationUserService.getUserByUserName(USER_NAME_FAIL_LOOK_UP, EXCEPTION_USER_NAME_DOES_NOT_EXIST);
    }

    @Test(expected = RequestChangePasswordException.class)
    public void getUserByUserNameCannotChangePassword() {
        applicationUserService.getUserByUserName(USER_NAME_FAIL_LOOK_UP, EXCEPTION_REQUEST_PASSWORD_CHANGE_FAILED);
    }

    @Test(expected = ChangePasswordTokenException.class)
    public void changePasswordWithoutToken() {
        ApplicationUser user = applicationUserService.getUserByUserName(USER_NAME_ONE, EXCEPTION_USER_NAME_DOES_NOT_EXIST);
        applicationUserService.changeUserPassword(USER_NAME_FAIL_LOOK_UP, user);
    }

    @Test(expected = GenericRequestException.class)
    public void sendPasswordChangeEmailFailure() {
        applicationUserService.sendPasswordChangeEmail(USER_NAME_ONE, null);
    }
 }
