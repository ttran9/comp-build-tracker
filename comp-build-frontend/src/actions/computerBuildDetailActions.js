import axios from "axios";
import * as Constants from "../Constants";
import {
  GET_ERRORS,
  GET_COMPUTER_BUILD_DETAILS,
  DELETE_COMPUTERPART,
  GET_COMPUTERPART,
  DELETE_DIRECTION,
  GET_DIRECTION,
  DELETE_BUILD_NOTE,
  GET_BUILD_NOTE,
  DELETE_OVERCLOCKING_NOTE,
  GET_OVERCLOCKING_NOTE,
  DELETE_PURPOSE,
  GET_PURPOSE
} from "./types";

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

// computer part actions start
export const createComputerPart = (
  computerPart,
  history,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(
      `${Constants.COMPUTER_PART_API}${computerBuildIdentifier}`,
      computerPart
    );
    // go back to the computer build detail page where the created computer part will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    // clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteComputerPartById = (
  buildIdentifier,
  computerPartIdentifier
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting this computer part, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `${
        Constants.COMPUTER_PART_API
      }${buildIdentifier}/${computerPartIdentifier}`
    );
    dispatch({
      type: DELETE_COMPUTERPART,
      payload: computerPartIdentifier
    });
  }
};

export const getComputerPartById = (
  buildIdentifier,
  computerPartIdentifier
) => async dispatch => {
  try {
    const response = await axios.get(
      `${
        Constants.COMPUTER_PART_API
      }${buildIdentifier}/${computerPartIdentifier}`
    );
    dispatch({
      type: GET_COMPUTERPART,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateComputerPart = (
  computerPart,
  buildIdentifier,
  computerPartIdentifier,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `${
        Constants.COMPUTER_PART_API
      }${buildIdentifier}/${computerPartIdentifier}`,
      computerPart
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
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
// computer part actions end

// purpose actions start
export const createPurpose = (
  purpose,
  history,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(
      `${Constants.PURPOSE_API}${computerBuildIdentifier}`,
      purpose
    );
    // go back to the computer build detail page where the new purpose will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    // clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deletePurposeById = (
  buildIdentifier,
  purposeIdentifier
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting this purpose, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `${Constants.PURPOSE_API}${buildIdentifier}/${purposeIdentifier}`
    );
    dispatch({
      type: DELETE_PURPOSE,
      payload: purposeIdentifier
    });
  }
};

export const getPurposeById = (
  buildIdentifier,
  purposeIdentifier
) => async dispatch => {
  try {
    const response = await axios.get(
      `${Constants.PURPOSE_API}${buildIdentifier}/${purposeIdentifier}`
    );
    dispatch({
      type: GET_PURPOSE,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updatePurpose = (
  purpose,
  buildIdentifier,
  purposeIdentifier,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `${Constants.PURPOSE_API}${buildIdentifier}/${purposeIdentifier}`,
      purpose
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
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
// purpose actions end

// build note actions start
export const createBuildNote = (
  buildNote,
  history,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(
      `${Constants.BUILD_NOTE_API}${computerBuildIdentifier}`,
      buildNote
    );
    // go back to the computer build detail page where the new build note will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    // clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteBuildNoteById = (
  buildIdentifier,
  buildNoteIdentifier
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting this build note, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `${Constants.BUILD_NOTE_API}${buildIdentifier}/${buildNoteIdentifier}`
    );
    dispatch({
      type: DELETE_BUILD_NOTE,
      payload: buildNoteIdentifier
    });
  }
};

export const getBuildNoteById = (
  buildIdentifier,
  buildNoteIdentifier
) => async dispatch => {
  try {
    const response = await axios.get(
      `${Constants.BUILD_NOTE_API}${buildIdentifier}/${buildNoteIdentifier}`
    );
    dispatch({
      type: GET_BUILD_NOTE,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateBuildNote = (
  buildNote,
  buildIdentifier,
  buildNoteIdentifier,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `${Constants.BUILD_NOTE_API}${buildIdentifier}/${buildNoteIdentifier}`,
      buildNote
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
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
// build note actions end

// direction actions start
export const createDirection = (
  direction,
  history,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(
      `${Constants.DIRECTION_API}${computerBuildIdentifier}`,
      direction
    );
    // go back to the computer build detail page where the new direction will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    // clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteDirectionById = (
  buildIdentifier,
  directionIdentifier
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting this direction, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `${Constants.DIRECTION_API}${buildIdentifier}/${directionIdentifier}`
    );
    dispatch({
      type: DELETE_DIRECTION,
      payload: directionIdentifier
    });
  }
};

export const getDirectionById = (
  buildIdentifier,
  directionIdentifier
) => async dispatch => {
  try {
    const response = await axios.get(
      `${Constants.DIRECTION_API}${buildIdentifier}/${directionIdentifier}`
    );
    dispatch({
      type: GET_DIRECTION,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateDirection = (
  direction,
  buildIdentifier,
  directionIdentifier,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `${Constants.DIRECTION_API}${buildIdentifier}/${directionIdentifier}`,
      direction
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
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
// direction actions end

// overclocking note actions start
export const createOverclockingNote = (
  overclockingNote,
  history,
  computerBuildIdentifier
) => async dispatch => {
  try {
    await axios.post(
      `${Constants.OVERCLOCKING_NOTE_API}${computerBuildIdentifier}`,
      overclockingNote
    );
    // go back to the computer build detail page where the new overclocking note will be displayed.
    history.push(`${Constants.COMPUTER_BUILD_URL}${computerBuildIdentifier}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
    // clearErrors(dispatch);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteOverclockingNoteById = (
  buildIdentifier,
  overclockingNoteIdentifier
) => async dispatch => {
  if (
    window.confirm(
      `You are deleting this overclocking note, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `${
        Constants.OVERCLOCKING_NOTE_API
      }${buildIdentifier}/${overclockingNoteIdentifier}`
    );
    dispatch({
      type: DELETE_OVERCLOCKING_NOTE,
      payload: overclockingNoteIdentifier
    });
  }
};

export const getOverclockingNoteById = (
  buildIdentifier,
  overclockingNoteIdentifier
) => async dispatch => {
  try {
    const response = await axios.get(
      `${
        Constants.OVERCLOCKING_NOTE_API
      }${buildIdentifier}/${overclockingNoteIdentifier}`
    );
    dispatch({
      type: GET_OVERCLOCKING_NOTE,
      payload: response.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateOverclockingNote = (
  overclockingNote,
  buildIdentifier,
  overclockingNoteIdentifier,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `${
        Constants.OVERCLOCKING_NOTE_API
      }${buildIdentifier}/${overclockingNoteIdentifier}`,
      overclockingNote
    );
    history.push(`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}`);
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
// overclocking note actions end
