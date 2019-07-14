import { SET_CURRENT_USER, REQUEST_SUCCESS } from "../actions/types";

const initialState = {
  user: {}, // a user object that can contain fields such as username, fullName, the date at which they signed up, etc.
  validToken: false, // a logged in user is expected to have this set to true.
  message: ""
};

const booleanActionPayload = payload => {
  /*
   * helper function to validate if there is a validToken present.
   * this can be determined if the payload has contents inside of it, such as having contents inside of the
   * "user" object from the security reducer.
   */
  return payload ? true : false;
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload),
        user: action.payload
      };
    case REQUEST_SUCCESS:
      return {
        ...state,
        message: action.payload
      };
    default:
      return state;
  }
}
