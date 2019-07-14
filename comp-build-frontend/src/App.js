import React, { Component } from "react";
import "./App.css";
import Header from "./components/layout/Header";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import * as Constants from "./Constants";
import VerifyRegistrationToken from "./components/UserManagement/VerifyRegistrationToken";
import RequestSuccess from "./components/UserManagement/RequestSuccess";
import AccountActivated from "./components/UserManagement/AccountActivated";
import Footer from "./components/Layout/Footer";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoutes from "./securityUtils/SecuredRoutes";
import RequestPasswordChange from "./components/UserManagement/RequestPasswordChange";
import ProcessPasswordChange from "./components/UserManagement/ProcessPasswordChange";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  /*
   * for each page we go to we must update the status of our token because there are many pages on this web application that requires we are
   * authenticated (we must have a valid jwt token to view the web page or to make API calls.)
   */
  setJWTToken(jwtToken);
  const decoded_token = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_token
  });

  const currentTime = Date.now() / 1000;
  if (decoded_token.exp < currentTime) {
    // token is expired so we must redirect to the home page.
    store.dispatch(logout());
    window.location.href = `${Constants.HOME_URL}`;
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            <Switch>
              <Route exact path={`${Constants.HOME_URL}`} component={Landing} />
              <Route
                exact
                path={`${Constants.REGISTER_URL}`}
                component={Register}
              />
              <Route exact path={`${Constants.LOGIN_URL}`} component={Login} />
              <Route
                exact
                path={`${Constants.CONFIRM_REGISTRATION_URL}/:token`}
                component={VerifyRegistrationToken}
              />
              <Route
                exact
                path={`${Constants.REQUEST_SUCCESS_URL}`}
                component={RequestSuccess}
              />
              <Route
                exact
                path={`${Constants.ACCOUNT_ACTIVATED_URL}`}
                component={AccountActivated}
              />
              {
                // Private/Protected Routes below.
              }
              <SecuredRoutes
                exact
                path={`${Constants.CHANGE_PASSWORD_URL}`}
                component={RequestPasswordChange}
              />
              <SecuredRoutes
                exact
                path={`${Constants.CHANGE_PASSWORD_URL}/:token`}
                component={ProcessPasswordChange}
              />
            </Switch>
            <Footer />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
