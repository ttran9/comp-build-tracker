package tran.compbuildbackend.controllers.utility;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import tran.compbuildbackend.payload.email.LoginRequest;
import tran.compbuildbackend.security.JwtTokenProvider;
import tran.compbuildbackend.services.security.ApplicationUserAuthenticationService;

public class WebUtility {

    /**
     * @param content The content to be set as JSON content.
     * @return Returns an HttpEntity object that can be used to make api calls with the entered content as JSON
     */
    public static HttpEntity<String> getEntity(String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(content, headers);
    }

    /**
     * @param content The content to be set as JSON content.
     * @param token The token to be put into the Authorization header.
     * @return Returns an HttpEntity object that can be used to make api calls with the entered content as JSON and
     * with a token inside of the Authorization header.
     */
    public static HttpEntity<String> getEntityWithToken(String content, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(content, headers);
    }

    /**
     * A helper method used in our tests in order to log the user in. This will add the authenticated user to Spring's
     * security context.
     * @param applicationUserAuthenticationService The authentication service to assist with authenticating credentials.
     * @param authenticationManager The authenticationManager to perform the authentication with the credentials.
     * @param jwtTokenProvider The jwtTokenProvider to produce a JWT token upon successful authentication.
     * @param loginRequest The payload object holding the user's credentials.
     */
    public static void logUserIn(ApplicationUserAuthenticationService applicationUserAuthenticationService, AuthenticationManager authenticationManager,
                            JwtTokenProvider jwtTokenProvider, LoginRequest loginRequest) {
        applicationUserAuthenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
    }
}
