import React, { Component, Fragment } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import * as Constants from "../../Constants";
import { Link } from "react-router-dom";

class RequestSuccess extends Component {
  render() {
    const { message } = this.props.security;
    const contentWithMessage = <p className="lead">{message}</p>;

    const contentWithNoMessage = (
      <Fragment>
        <p className="lead">You did not make a request on your account.</p>
        <Link
          className="btn btn-lg btn-secondary mr-2"
          to={`${Constants.HOME_URL}`}
        >
          Home
        </Link>
      </Fragment>
    );

    let content =
      this.props.security.message === undefined || message === ""
        ? contentWithNoMessage
        : contentWithMessage;

    return (
      <div className="registration-success">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-12 text-center">
                {content}
                <hr />
                <Link
                  className="btn btn-lg btn-primary"
                  to={`${Constants.LOGIN_URL}`}
                >
                  Login
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

RequestSuccess.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(RequestSuccess);
