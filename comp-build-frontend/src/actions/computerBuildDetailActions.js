import axios from "axios";
import * as Constants from "../Constants";
import { GET_ERRORS, GET_COMPUTER_BUILD_DETAILS } from "./types";
import { clearErrors } from "../errorUtils/clearErrors";

// computer build detail actions start
/*
 * although this method is defined in the computer build use it here as well
 * because in the computer build reducer we don't have direct access to a computer
 * part list, overclocking note list, direction list, purpose list, or other notes list.
 */
export const getComputerBuildFromId = buildIdentifier => async dispatch => {
  const response = await axios.get(
    `${Constants.COMPUTER_BUILD_API}${buildIdentifier}`
  );
  dispatch({
    type: GET_COMPUTER_BUILD_DETAILS,
    payload: response.data
  });
};

/*
 * this will differ slightly from the implementation inside of computerBuildActions as it will redirect the user back to the home page.
 */
export const deleteComputerBuildByBuildIdentifier = buildIdentifier => async dispatch => {
  if (window.confirm(Constants.CONFIRM_DELETE_MESSAGE)) {
    await axios.delete(`${Constants.COMPUTER_BUILD_API}${buildIdentifier}`);
    window.location.href = `${Constants.HOME_URL}`;
  }
};
// computer build detail actions end

// CRUD operations for computer build details (computer parts, purpose, build note, direction, and overclocking note) start.
export const createObject = (
  object,
  history,
  apiURL,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(`${apiURL}${computerBuildIdentifier}`, object);
    // go back to the computer build detail page where the created object will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteObjectById = (
  apiURL,
  buildIdentifier,
  uniqueIdentifier,
  dispatchType
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting the selected object, this action cannot be undone.`
    )
  ) {
    const response = await axios.delete(
      `${apiURL}${buildIdentifier}/${uniqueIdentifier}`
    );
    dispatch({
      type: dispatchType,
      payload: response.data
    });
  }
};

export const getObjectById = (
  apiURL,
  buildIdentifier,
  uniqueIdentifier,
  dispatchType
) => async dispatch => {
  try {
    const response = await axios.get(
      `${apiURL}${buildIdentifier}/${uniqueIdentifier}`
    );
    dispatch({
      type: dispatchType,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateObject = (
  object,
  buildIdentifier,
  uniqueIdentifier,
  history,
  apiURL
) => async dispatch => {
  try {
    await axios.patch(
      `${apiURL}${buildIdentifier}/${uniqueIdentifier}`,
      object
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
    clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
// CRUD operations for computer build details (computer parts, purpose, build note, direction, and overclocking note) end.
