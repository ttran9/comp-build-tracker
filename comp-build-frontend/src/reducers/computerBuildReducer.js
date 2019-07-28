import {
  GET_COMPUTERBUILDS,
  GET_COMPUTERBUILD,
  DELETE_COMPUTERBUILD
} from "../actions/types";

const initialState = {
  computerBuilds: [],
  computerBuild: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_COMPUTERBUILDS:
      return {
        ...state,
        computerBuilds: action.payload
      };
    case GET_COMPUTERBUILD:
      return {
        ...state,
        computerBuild: action.payload
      };
    case DELETE_COMPUTERBUILD:
      return {
        ...state,
        computerBuilds: state.computerBuilds.filter(
          computerBuild => computerBuild.buildIdentifier !== action.payload
        )
      };
    default:
      return state;
  }
}
