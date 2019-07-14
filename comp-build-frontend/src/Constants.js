export const HOME_URL = "/";
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

export const AUTHORIZATION_HEADER = "Authorization";
export const JWT_TOKEN = "jwtToken";
