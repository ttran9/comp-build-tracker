export const HOME_URL = "/";

// user constants
export const REGISTER_URL = "/register";
export const LOGIN_URL = "/login";
export const LOGOUT_URL = "/logout";
export const ACCOUNT_ACTIVATED_URL = "/accountActivated";
export const CONFIRM_REGISTRATION_URL = "/confirmRegistration";
export const REQUEST_SUCCESS_URL = "/requestSuccess";
export const CHANGE_PASSWORD_URL = "/changePassword";
export const API_URL = "/api";
export const USERS_URL = "/users";
export const REGISTER_ENDPOINT = `${API_URL}${USERS_URL}${REGISTER_URL}`;
export const LOGIN_ENDPOINT = `${API_URL}${USERS_URL}${LOGIN_URL}`;
export const CONFIRM_REGISTRATION_ENDPOINT = `${API_URL}${USERS_URL}${CONFIRM_REGISTRATION_URL}`;
export const CHANGE_PASSWORD_ENDPOINT = `${API_URL}${USERS_URL}${CHANGE_PASSWORD_URL}`;

// security constants.
export const AUTHORIZATION_HEADER = "Authorization";
export const JWT_TOKEN = "jwtToken";
export const TOKEN_PATH_VARIABLE = ":token";

// computerbuild constants.
export const COMPUTER_BUILD_URL = "/computerbuild/";
export const COMPUTER_BUILD_API = `${API_URL}${COMPUTER_BUILD_URL}`;
export const CREATE_COMPUTER_BUILD_URL = "/addComputerBuild";
export const BUILD_IDENTIFIER_VARIABLE = ":buildIdentifier";
export const USER_NAME_REQUEST = "username/";
export const USER_NAME_PATH_VARIABLE = ":username";
export const CONFIRM_DELETE_MESSAGE =
  "Are you sure? This will delete the computer build and all its data!";
