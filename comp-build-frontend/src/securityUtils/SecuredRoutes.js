import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import * as Constants from "../Constants";

const SecuredRoutes = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props =>
      security.validToken == true ? (
        <Component {...props} />
      ) : (
        <Redirect to={`${Constants.LOGIN_URL}`} />
      )
    }
  />
);

SecuredRoutes.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(SecuredRoutes);
