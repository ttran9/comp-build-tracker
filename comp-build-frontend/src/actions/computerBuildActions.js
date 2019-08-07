import axios from "axios";
import {
  GET_ERRORS,
  GET_COMPUTERBUILDS,
  GET_COMPUTERBUILD,
  DELETE_COMPUTERBUILD
} from "./types";
import * as Constants from "../Constants";
import { clearErrors } from "../errorUtils/clearErrors";

export const createComputerBuild = (
  computerBuild,
  history
) => async dispatch => {
  try {
    await axios.post(`${Constants.COMPUTER_BUILD_API}`, computerBuild);
    // go back home where the new created computer build will be displayed.
    history.push(Constants.HOME_URL);
    clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteComputerBuildByIdentifier = buildIdentifier => async dispatch => {
  if (window.confirm(Constants.CONFIRM_DELETE_MESSAGE)) {
    await axios.delete(`${Constants.COMPUTER_BUILD_API}${buildIdentifier}`);
    dispatch({
      // dispatch an action where we pass in the id into the payload to filter out that computerbuild.
      type: DELETE_COMPUTERBUILD,
      payload: buildIdentifier
    });
  }
  // there is no need to send the user back home as the user will already be at the home page while performing this operation.
};

export const getComputerBuildByBuildIdentifier = buildIdentifier => async dispatch => {
  const response = await axios.get(
    `${Constants.COMPUTER_BUILD_API}${buildIdentifier}`
  );
  dispatch({
    type: GET_COMPUTERBUILD,
    payload: response.data
  });
};

export const getComputerBuilds = () => async dispatch => {
  const response = await axios.get(Constants.COMPUTER_BUILD_API);
  dispatch({
    type: GET_COMPUTERBUILDS,
    payload: response.data
  });
  // no need for a catch block because if there are no computer builds then the user is already at the home page.
};

export const getComputerBuildsByUsername = userName => async dispatch => {
  const response = await axios.get(
    `${Constants.COMPUTER_BUILD_API}${USER_NAME_REQUEST}${userName}`
  );
  /*
   * TODO (after I get everything wired up....): check the size of the response's data (the list of computer builds)
   - if there are none then there needs to be a message on the home page stating there are no computer builds and an option to go back to the home page without the username path variable (so there is no action being invoked to look for computer builds by a user name).
  */
  dispatch({
    type: GET_COMPUTERBUILDS,
    payload: response.data
  });
};
