package tran.compbuildbackend.domain.utility;

import com.google.gson.Gson;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.payload.InitialPasswordChangeRequest;
import tran.compbuildbackend.payload.LoginRequest;
import tran.compbuildbackend.payload.PasswordChangeRequest;

public class ApplicationUserUtility {

    public static String getUserAsJson(String userName, String fullName, String password, String confirmPassword) {
        ApplicationUser user = new ApplicationUser(userName, fullName, password, confirmPassword);
        return new Gson().toJson(user);
    }

    public static String getUserAsJson(String userName, String password, String confirmPassword) {
        ApplicationUser user = new ApplicationUser(userName, password, confirmPassword);
        return new Gson().toJson(user);
    }

    public static String getLoginRequestAsJson(String userName, String password) {
        LoginRequest loginRequest = new LoginRequest(userName, password);
        return new Gson().toJson(loginRequest);
    }

    public static String getInitialPasswordChangeRequestAsJson(String userName) {
        InitialPasswordChangeRequest passwordChangeRequest = new InitialPasswordChangeRequest(userName);
        return new Gson().toJson(passwordChangeRequest);
    }

    public static String getPasswordChangeRequestAsJson(String password, String confirmPassword) {
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(password, confirmPassword);
        return new Gson().toJson(passwordChangeRequest);
    }


}
