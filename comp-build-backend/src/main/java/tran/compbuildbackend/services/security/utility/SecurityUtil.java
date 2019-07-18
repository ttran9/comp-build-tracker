package tran.compbuildbackend.services.security.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tran.compbuildbackend.domain.security.VerificationToken;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.security.ChangePasswordTokenException;
import tran.compbuildbackend.exceptions.security.EmailVerificationTokenException;

import java.util.Calendar;

import static tran.compbuildbackend.constants.security.SecurityConstants.CHANGE_PASSWORD_TOKEN_TYPE;
import static tran.compbuildbackend.constants.security.SecurityConstants.EMAIL_VERIFICATION_TOKEN_TYPE;

public class SecurityUtil {

    /*
     * helper method to grab the user name from the logged in user.
     */
    public static String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null) {
                if(principal instanceof ApplicationUser) {
                    ApplicationUser user = (ApplicationUser) principal;
                    return user.getUsername();
                }
            }
        }
        return ""; // TODO: I may have to throw an exception if this empty string causes issues and handle it..
    }

    /**
     * @param token The token to be verified.
     * @param tokenType The type of custom exception to be thrown.
     * @return If the token is expired a custom exception is thrown, if the token has not expired return false.
     */
    public static boolean isTokenExpired(VerificationToken token, int tokenType) {
        // verify if the token is expired.
        Calendar calendar = Calendar.getInstance();
        if((token.getExpirationDate().getTime()-calendar.getTime().getTime())<=0) {
            switch(tokenType) {
                case CHANGE_PASSWORD_TOKEN_TYPE:
                    throw new ChangePasswordTokenException("token has expired, please request another token.");
                case EMAIL_VERIFICATION_TOKEN_TYPE:
                    throw new EmailVerificationTokenException("token has expired, please request another token.");
                default:
                    break;
            }
        }
        return false;
    }

}
