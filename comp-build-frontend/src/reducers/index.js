import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import securityReducer from "./securityReducer";
import computerBuildReducer from "./computerBuildReducer";

export default combineReducers({
  // returns all the reducers

  errors: errorReducer,
  security: securityReducer,
  computerBuild: computerBuildReducer
});
