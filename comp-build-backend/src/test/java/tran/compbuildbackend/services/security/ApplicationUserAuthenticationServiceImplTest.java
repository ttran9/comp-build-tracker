package tran.compbuildbackend.services.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;
import tran.compbuildbackend.payload.email.JWTLoginSuccessResponse;
import tran.compbuildbackend.payload.email.LoginRequest;
import tran.compbuildbackend.security.JwtTokenProvider;

import static org.junit.Assert.*;
import static tran.compbuildbackend.constants.users.UserConstants.USER_NAME_ONE;
import static tran.compbuildbackend.constants.users.UserConstants.USER_PASSWORD;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUserAuthenticationServiceImplTest {

    private ApplicationUserAuthenticationService applicationUserAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    @Before
    public void setUp() {
        applicationUserAuthenticationService = new ApplicationUserAuthenticationServiceImpl();
        jwtTokenProvider = new JwtTokenProvider();
    }

    /*
     * This test will check for a successful authentication with no errors expected.
     */
    @Test
    public void testSuccessfulAuthentication() {
        LoginRequest loginRequest = new LoginRequest(USER_NAME_ONE, USER_PASSWORD);

        ResponseEntity<?> responseEntity = applicationUserAuthenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        JWTLoginSuccessResponse bodyContents = (JWTLoginSuccessResponse) responseEntity.getBody();
        assertNotNull(bodyContents.getToken());
        assertTrue(bodyContents.isSuccess());
    }

    /*
     * This test checks for an attempted authentication that uses the incorrect credentials.
     */
    @Test(expected = BadCredentialsException.class)
    public void testAuthenticationFailure() {
        LoginRequest loginRequest = new LoginRequest(USER_NAME_ONE, USER_PASSWORD + "1");
        applicationUserAuthenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
    }
}
