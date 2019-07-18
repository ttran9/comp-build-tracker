package tran.compbuildbackend.controllers.applicationusers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tran.compbuildbackend.controllers.utility.WebUtility;
import tran.compbuildbackend.domain.utility.ApplicationUserUtility;
import tran.compbuildbackend.exceptions.security.RequestChangePasswordExceptionResponse;
import tran.compbuildbackend.exceptions.security.UsernameDuplicateExceptionResponse;
import tran.compbuildbackend.payload.JWTLoginSuccessResponse;
import tran.compbuildbackend.payload.RequestSuccessfulResponse;

import java.net.URI;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;
import static tran.compbuildbackend.constants.fields.FieldConstants.*;
import static tran.compbuildbackend.constants.fields.FieldValueConstants.*;
import static tran.compbuildbackend.constants.mapping.MappingConstants.*;
import static tran.compbuildbackend.constants.messages.ResponseMessage.*;
import static tran.compbuildbackend.constants.tests.TestUtility.BASE_URL;
import static tran.compbuildbackend.constants.users.UserConstants.*;

@Profile({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationUserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSuccessfulRegistration() throws Exception {
        // testing a successful registration (happy path)
        String content = ApplicationUserUtility.getUserAsJson(SUCCESSFUL_USER_NAME, FULL_NAME, USER_PASSWORD, USER_PASSWORD);

        String registrationURL = BASE_URL + USERS_API + REGISTER_URL;

        RequestSuccessfulResponse contents = getContents(registrationURL, WebUtility.getEntity(content), HttpStatus.CREATED.value());
        assertNotNull(contents);
        String token = contents.getToken();

        assertNotNull(contents.getMessage());
        assertEquals(VERIFY_EMAIL_MESSAGE, contents.getMessage());
        assertNotNull(token);
        assertFalse(contents.isEnabled());

        // once we activate the account we need to check again if the account is enabled.
        String confirmEmail = BASE_URL + USERS_API + CONFIRM_REGISTRATION_URL + token;
        contents = getContents(confirmEmail, WebUtility.getEntity(content), HttpStatus.OK.value());
        assertNotNull(contents);
        assertEquals(SUCCESSFUL_ACCOUNT_ACTIVATION, contents.getMessage());
        assertTrue(contents.isEnabled());
    }

    private RequestSuccessfulResponse getContents(String url, HttpEntity<String> entity, int expectedHttpStatusCode) throws Exception{
        URI uri = new URI(url);
        ResponseEntity<RequestSuccessfulResponse> result = restTemplate.postForEntity(uri, entity, RequestSuccessfulResponse.class);
        assertNotNull(result);
        assertEquals(expectedHttpStatusCode, result.getStatusCode().value());
        return result.getBody();
    }

    @Test
    public void testUsernameExistsRegistration() throws Exception {
        // testing to see what happens when we are trying to registration a user that is already in the database.
        String content = ApplicationUserUtility.getUserAsJson(USER_NAME_ONE, FULL_NAME, USER_PASSWORD, USER_PASSWORD);

        URI uri = new URI(BASE_URL + USERS_API + REGISTER_URL);
        ResponseEntity<UsernameDuplicateExceptionResponse> result = restTemplate.postForEntity(uri, WebUtility.getEntity(content), UsernameDuplicateExceptionResponse.class);
        UsernameDuplicateExceptionResponse contents = result.getBody();

        assertNotNull(contents);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
        assertNotNull(contents.getUsername());
        assertEquals(USER_NAME_ERROR + USER_NAME_ONE + ALREADY_EXISTS_ERROR, contents.getUsername());
    }

    @Test
    public void testPasswordMismatch() throws Exception {
        // testing to see what happens if the password fields don't match.
        String content = ApplicationUserUtility.getUserAsJson(SUCCESSFUL_USER_NAME_TWO, FULL_NAME, USER_PASSWORD, USER_PASSWORD_TWO);

        String registrationURL = BASE_URL + USERS_API + REGISTER_URL;
        LinkedHashMap contents = getLinkedHashMapContents(registrationURL, WebUtility.getEntity(content), HttpStatus.BAD_REQUEST.value());

        assertNotNull(contents);
        assertNotNull(contents.get(CONFIRM_PASSWORD_FIELD));
        assertEquals(PASSWORD_MISMATCH_ERROR, contents.get(CONFIRM_PASSWORD_FIELD));
    }

    @Test
    public void testMultipleFieldErrors() throws Exception {
        /*
         * testing for the following errors.
         * 1) the password is too short (not at least 6 characters).
         * 2) no full name field entered.
         * 3) The passwords don't match.
         */
        String content = ApplicationUserUtility.getUserAsJson(USER_NAME_ONE, SHORT_PASSWORD, USER_PASSWORD_TWO);

        String registrationURL = BASE_URL + USERS_API + REGISTER_URL;
        LinkedHashMap contents = getLinkedHashMapContents(registrationURL, WebUtility.getEntity(content), HttpStatus.BAD_REQUEST.value());

        assertNotNull(contents);
        assertNotNull(contents.get(CONFIRM_PASSWORD_FIELD));
        assertEquals(PASSWORD_MISMATCH_ERROR, contents.get(CONFIRM_PASSWORD_FIELD));
        assertNotNull(contents.get(PASSWORD_FIELD));
        assertEquals(SHORT_PASSWORD_ERROR, contents.get(PASSWORD_FIELD));
        assertNotNull(contents.get(FULL_NAME_FIELD));
        assertEquals(FULL_NAME_MISSING_ERROR, contents.get(FULL_NAME_FIELD));
    }

    @Test
    public void testValidLogin() throws Exception {
        /*
         * tests the happy path for logging in. this is done by logging in as a user that was already boot strapped into
         * the application's test database.
         */
        loginHelper(USER_NAME_ONE, USER_PASSWORD);
}

    @Test
    public void testInvalidLogin() throws Exception {
        // tests when the incorrect credentials are entered for an existing user.
        String content = ApplicationUserUtility.getLoginRequestAsJson(USER_NAME_ONE, USER_PASSWORD_TWO);
        String loginURL = BASE_URL + USERS_API + LOGIN_URL;

        LinkedHashMap contents = getLinkedHashMapContents(loginURL, WebUtility.getEntity(content), HttpStatus.BAD_REQUEST.value());

        assertNotNull(contents);
        assertEquals(INVALID_USERNAME_ERROR, contents.get(USER_NAME_FIELD));
        assertEquals(INVALID_PASSWORD_ERROR, contents.get(PASSWORD_FIELD));
    }

    @Test
    public void testInvalidLoginWithNonExistingUser() throws Exception {
        /*
         * tests the case where a user name that does not exist is attemping to log in.
         * this will just return a generic "invalid username, invalid password." in the response.
         */
        String content = ApplicationUserUtility.getLoginRequestAsJson(USER_NAME_DOES_NOT_EXIST, USER_PASSWORD);
        String loginURL = BASE_URL + USERS_API + LOGIN_URL;

        LinkedHashMap contents = getLinkedHashMapContents(loginURL, WebUtility.getEntity(content), HttpStatus.BAD_REQUEST.value());

        assertNotNull(contents);
        assertEquals(INVALID_USERNAME_ERROR, contents.get(USER_NAME_FIELD));
        assertEquals(INVALID_PASSWORD_ERROR, contents.get(PASSWORD_FIELD));
    }

    private LinkedHashMap getLinkedHashMapContents(String url, HttpEntity<String> entity, int expectedHttpStatusCode) throws Exception{
        URI uri = new URI(url);
        ResponseEntity<LinkedHashMap> result = restTemplate.postForEntity(uri, entity, LinkedHashMap.class);
        assertNotNull(result);
        assertEquals(expectedHttpStatusCode, result.getStatusCode().value());
        return result.getBody();
    }

    @Test
    public void testChangePassword() throws Exception {
        /*
         * tests the happy path for the user requesting to change the password on the account.
         * this test will also attempt to log in after the password is changed.
         */
        String content = ApplicationUserUtility.getInitialPasswordChangeRequestAsJson(USER_NAME_ONE);
        String changePasswordURL = BASE_URL + USERS_API + CHANGE_PASSWORD_URL;

        // request for the password change.
        RequestSuccessfulResponse contents = getContents(changePasswordURL, WebUtility.getEntity(content), HttpStatus.OK.value());

        assertNotNull(contents);
        assertEquals(CHANGE_PASSWORD, contents.getMessage());
        assertNotNull(contents.getToken());

        // change the password.
        String passwordChangeRequestContent = ApplicationUserUtility.getPasswordChangeRequestAsJson(MODIFIED_PASSWORD, MODIFIED_PASSWORD);
        String processChangePasswordURL = BASE_URL + USERS_API + CHANGE_PASSWORD_URL + contents.getToken();
        RequestSuccessfulResponse response = getContents(processChangePasswordURL, WebUtility.getEntity(passwordChangeRequestContent), HttpStatus.OK.value());

        assertNotNull(response);
        assertEquals(PASSWORD_CHANGED, response.getMessage());

        // attempt to log in with the new password.
        loginHelper(USER_NAME_ONE, MODIFIED_PASSWORD);
    }

    private void loginHelper(String userName, String password) throws Exception {
        String loginContent = ApplicationUserUtility.getLoginRequestAsJson(userName, password);
        String loginURL = BASE_URL + USERS_API + LOGIN_URL;
        URI uri = new URI(loginURL);

        ResponseEntity<JWTLoginSuccessResponse> result = restTemplate.postForEntity(uri, WebUtility.getEntity(loginContent), JWTLoginSuccessResponse.class);
        JWTLoginSuccessResponse loginResponse = result.getBody();

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getToken());
        assertTrue(loginResponse.isSuccess());
    }

    @Test
    public void testChangePasswordWithNonExistingUsername() throws Exception {
        String content = ApplicationUserUtility.getInitialPasswordChangeRequestAsJson(USER_NAME_DOES_NOT_EXIST);
        String changePasswordURL = BASE_URL + USERS_API + CHANGE_PASSWORD_URL;

        URI uri = new URI(changePasswordURL);
        ResponseEntity<RequestChangePasswordExceptionResponse> result = restTemplate.postForEntity(uri,
                WebUtility.getEntity(content), RequestChangePasswordExceptionResponse.class);

        RequestChangePasswordExceptionResponse contents = result.getBody();

        assertNotNull(contents);
        assertEquals(PASSWORD_CANNOT_BE_CHANGED_FOR_INVALID_USER, contents.getUsername());
    }

}
