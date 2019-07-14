import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import { connect } from "react-redux";
import PropTypes from "prop-types";

class Landing extends Component {
  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push(`${Constants.HOME_URL}`);
    }
  }

  render() {
    const { validToken, user } = this.props.security;

    const userIsNotAuthenticated = (
      <Fragment>
        <hr />
        <Link
          className="btn btn-lg btn-primary mr-2"
          to={`${Constants.REGISTER_URL}`}
        >
          Register
        </Link>
        <Link
          className="btn btn-lg btn-secondary mr-2"
          to={`${Constants.LOGIN_URL}`}
        >
          Login
        </Link>
      </Fragment>
    );

    const userIsAuthenticated = <div />;

    let content = true;

    if (validToken && user) {
      content = userIsAuthenticated;
    } else {
      content = userIsNotAuthenticated;
    }

    return (
      <div className="light-overlay landing-inner text-dark">
        <div className="container">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Computer Build Tracker</h1>
              <p className="lead">
                Create your account to create a computer build and add notes,
                directions, tips, or overclocking notes, or anything else.
              </p>
              {content}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Landing.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(Landing);
