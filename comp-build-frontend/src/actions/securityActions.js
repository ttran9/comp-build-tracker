import axios from "axios";
import * as Constants from "../Constants";
import { GET_ERRORS, SET_CURRENT_USER, REQUEST_SUCCESS } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios
      .post(`${Constants.REGISTER_ENDPOINT}`, newUser)
      .then(response => {
        /*
         * dispatch an action to update the state with the success message.
         * note there are other objects inside of the response.data but we only need to view the message key/* value pair.
         */
        dispatch({
          type: REQUEST_SUCCESS,
          payload: response.data.message
        });
        /*
         * redirect the user to a page that informs them the password request was successful and to check his/* her email with instructions.
         * TODO: Test this later and push the REQUEST_SUCCESS_URL once this is working..?!?
         */
        history.push(`${Constants.ACCOUNT_ACTIVATED_URL}`);
      });
    // clearErrors(dispatch);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    // post to login endpoint with LoginRequest payload object
    const response = await axios.post(
      `${Constants.LOGIN_ENDPOINT}`,
      LoginRequest
    );
    /*
     * extract token from the response.data
     * note: there is also a success object only the token object is required.
     */
    const { token } = response.data;
    // store the token in the localStorage
    localStorage.setItem(`${Constants.JWT_TOKEN}`, token);

    // set our token in the header (Authorization: Bearer [token here])
    setJWTToken(token);

    // decode token on React
    const decoded = jwt_decode(token);

    // dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem(`${Constants.JWT_TOKEN}`);
  setJWTToken(false); // delete the Authorization header ("Bearer [token here]").
  dispatch({
    // this will clear the user object inside of the corresponding reducer (securityReducer).
    // this will also clear the token object as well.
    type: SET_CURRENT_USER,
    payload: {}
  });
};

export const confirmRegistration = (token, history) => async dispatch => {
  try {
    // make a post request with the token in order to activate the account.
    await axios
      .post(`${Constants.CONFIRM_REGISTRATION_ENDPOINT}/${token}`)
      .then(response => {
        dispatch({
          /*
           * dispatch an action to update the state with the success message.
           * just like in the createNewUser, only the message is needed in the component.
           */
          type: REQUEST_SUCCESS,
          payload: response.data.message
        });
        history.push(`${Constants.REQUEST_SUCCESS_URL}`);
      });
  } catch (error) {
    // if there is an error with the token confirmation this is expected to be caught here such as the token being expired or incorrectly entered.
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const requestPasswordChange = (history, username) => async dispatch => {
  try {
    /*
     * the idea here is to redirect the user to a page that informs them the password request was successful * and to check his/her email with instructions.
     */
    await axios
      .post(`${Constants.CHANGE_PASSWORD_ENDPOINT}/`, username)
      .then(response => {
        dispatch({
          type: REQUEST_SUCCESS,
          payload: response.data.message
        });
        // TODO: Test this later and push the REQUEST_SUCCESS_URL once this is working..?!?
        history.push(`${Constants.ACCOUNT_ACTIVATED_URL}`);
      });
  } catch (error) {
    // a potential error would be when the user enters an email address that doesn't exist.
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const changePassword = (
  token,
  history,
  passwordContents
) => async dispatch => {
  try {
    // passwordContents has a password and a confirmPassword key/value pair.
    await axios
      .post(`${Constants.CHANGE_PASSWORD_ENDPOINT}/${token}`, passwordContents)
      .then(response => {
        // dispatch an action to update the state with the success message.
        dispatch({
          type: REQUEST_SUCCESS,
          payload: response.data.message
        });
        // redirect to a page that will display that the password has been changed successfully.
        history.push(`${Constants.REQUEST_SUCCESS_URL}`);
      });
    // clearErrors(dispatch);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

const clearErrors = dispatch => {
  // helper function to clear errors after successful form submission.
  dispatch({
    type: GET_ERRORS,
    payload: {}
  });
};
