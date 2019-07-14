import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import * as Constants from "../../Constants";

class AccountActivated extends Component {
  render() {
    const { message } = this.props.security;
    const contentWithMessage = <p className="lead">{message}</p>;

    const contentWithNoMessage = (
      <Fragment>
        <p className="lead">You did not request any content.</p>
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
      <div className="account-activated">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-12 text-center">
                {content}
                <hr />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

AccountActivated.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(AccountActivated);
