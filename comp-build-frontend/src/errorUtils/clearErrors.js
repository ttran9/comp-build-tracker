import { GET_ERRORS } from "../actions/types";

export const clearErrors = dispatch => {
  // helper function to clear errors from the application's state after successful form submission.
  dispatch({
    type: GET_ERRORS,
    payload: {}
  });
};
