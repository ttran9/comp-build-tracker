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
export const BUILD_IDENTIFIER_PATH_VARIABLE = ":buildIdentifier";
export const USER_NAME_REQUEST = "username/";
export const USER_NAME_PATH_VARIABLE = ":username";
export const CONFIRM_DELETE_MESSAGE =
  "Are you sure? This will delete the computer build and all its data!";

// constants for all the computer details (purpose, notes, computer parts, etc).
export const UNIQUE_IDENTIFIER_PATH_VARIABLE = ":uniqueIdentifier";

// computer part constants.
export const COMPUTER_PART_URL = "/computerpart/";
export const COMPUTER_PART_API = `${API_URL}${COMPUTER_PART_URL}`;
export const CREATE_COMPUTER_PART_URL = "/addComputerPart";
export const UPDATE_COMPUTER_PART_URL = "/updateComputerPart/";

// build note constants.
export const BUILD_NOTE_URL = "/buildnote/";
export const BUILD_NOTE_API = `${API_URL}${BUILD_NOTE_URL}`;
export const CREATE_BUILD_NOTE_URL = "/addBuildNote";
export const UPDATE_BUILD_NOTE_URL = "/updateBuildNote/";

// direction constants.
export const DIRECTION_URL = "/direction/";
export const DIRECTION_API = `${API_URL}${DIRECTION_URL}`;
export const CREATE_DIRECTION_URL = "/addDirection";
export const UPDATE_DIRECTION_URL = "/updateDirection/";

// overclocking note constants.
export const OVERCLOCKING_NOTE_URL = "/overclockingnote/";
export const OVERCLOCKING_NOTE_API = `${API_URL}${OVERCLOCKING_NOTE_URL}`;
export const CREATE_OVERCLOCKING_NOTE_URL = "/addOverclockingNote";
export const UPDATE_OVERCLOCKING_NOTE_URL = "/updateOverclockingNote/";

// purpose constants.
export const PURPOSE_URL = "/purpose/";
export const PURPOSE_API = `${API_URL}${PURPOSE_URL}`;
export const CREATE_PURPOSE_URL = "/addPurpose";
export const UPDATE_PURPOSE_URL = "/updatePurpose/";
