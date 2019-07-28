export const clearErrors = dispatch => {
  // helper function to clear errors after successful form submission.
  dispatch({
    type: GET_ERRORS,
    payload: {}
  });
};
