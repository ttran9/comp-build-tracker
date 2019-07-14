import axios from "axios";
import * as Constants from "../Constants";

const setJWTToken = token => {
  if (token) {
    // token present so put it into the headers.
    axios.defaults.headers.common[`${Constants.AUTHORIZATION_HEADER}`] = token;
  } else {
    // clear the token
    delete axios.defaults.headers.common[`${Constants.AUTHORIZATION_HEADER}`];
  }
};

export default setJWTToken;
