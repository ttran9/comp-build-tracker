import {
  GET_COMPUTER_BUILD_DETAILS,
  DELETE_COMPUTERPART,
  GET_COMPUTERPART,
  DELETE_PURPOSE,
  GET_PURPOSE,
  DELETE_BUILD_NOTE,
  GET_BUILD_NOTE,
  DELETE_DIRECTION,
  GET_DIRECTION,
  DELETE_OVERCLOCKING_NOTE,
  GET_OVERCLOCKING_NOTE
} from "../actions/types";

const initialState = {
  computerParts: [],
  computerPart: {},
  directions: [],
  direction: {},
  buildNotes: [],
  buildNote: {},
  purposeList: [],
  purpose: {},
  overclockingNotes: [],
  overclockingNote: {},
  computerBuild: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_COMPUTER_BUILD_DETAILS:
      const computerBuild = action.payload;
      return {
        ...state,
        computerBuild: computerBuild,
        computerParts: computerBuild.computerParts,
        directions: computerBuild.directions,
        buildNotes: computerBuild.buildNotes,
        purposeList: computerBuild.purposeList,
        overclockingNotes: computerBuild.overclockingNotes
      };
    case DELETE_COMPUTERPART:
      return {
        ...state,
        computerParts: state.computerParts.filter(
          computerPart => computerPart.uniqueIdentifier !== action.payload
        )
      };
    case GET_COMPUTERPART:
      return {
        ...state,
        computerPart: action.payload
      };
    case DELETE_PURPOSE:
      return {
        ...state,
        purposeList: state.purposeList.filter(
          purpose => purpose.uniqueIdentifier !== action.payload
        )
      };
    case GET_PURPOSE:
      return {
        ...state,
        purpose: action.payload
      };
    case DELETE_BUILD_NOTE:
      return {
        ...state,
        buildNotes: state.buildNotes.filter(
          buildNote => buildNote.uniqueIdentifier !== action.payload
        )
      };
    case GET_BUILD_NOTE:
      return {
        ...state,
        buildNote: action.payload
      };
    case DELETE_DIRECTION:
      return {
        ...state,
        directions: state.directions.filter(
          direction => direction.uniqueIdentifier !== action.payload
        )
      };
    case GET_DIRECTION:
      return {
        ...state,
        direction: action.payload
      };
    case DELETE_OVERCLOCKING_NOTE:
      return {
        ...state,
        overclockingNotes: state.overclockingNotes.filter(
          overclockingNote =>
            overclockingNote.uniqueIdentifier !== action.payload
        )
      };
    case GET_OVERCLOCKING_NOTE:
      return {
        ...state,
        overclockingNote: action.payload
      };
    default:
      return state;
  }
}
