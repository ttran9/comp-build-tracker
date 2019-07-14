package tran.compbuildbackend.constants.mapping;

public class MappingConstants {

    public static final String URLS_REGEX = "^\\/[a-zA-Z]+\\/{0,1}$";
    public static final String PB_UPT_REGEX = "\\/[a-zA-Z]+\\/[a-zA-Z0-9]{1,5}";

    // ApplicationUserController
    public static final String CONFIRM_REGISTRATION_URL = "/confirmRegistration/";
    public static final String CHANGE_PASSWORD_URL = "/changePassword/";
    public static final String USERS_API = "/api/users";
    public static final String REGISTER_URL = "/register";
    public static final String LOGIN_URL = "/login";
    public static final String TOKEN_PARAM = "{token}";
}
