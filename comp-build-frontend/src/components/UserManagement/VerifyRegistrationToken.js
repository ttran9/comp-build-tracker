import React, { Component } from "react";
import { confirmRegistration } from "../../actions/securityActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import * as Constants from "../../Constants";

class VerifyRegistrationToken extends Component {
  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push(`${Constants.HOME_URL}`);
    }
  }

  onSubmit = event => {
    event.preventDefault();
    const { token } = this.props.match.params;
    this.props.confirmRegistration(token, this.props.history);
  };

  render() {
    const { token } = this.props.match.params;
    if (!token) {
      this.props.history.push(Constants.ACCOUNT_ACTIVATED_URL);
    }
    return (
      <div className="verify-registration">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">
                Activate your account below.
              </h1>
              <form onSubmit={this.onSubmit}>
                <input
                  type="submit"
                  className="btn btn-info btn-block mt-4"
                  value="activate account"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  security: state.security
});

VerifyRegistrationToken.propTypes = {
  confirmRegistration: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

export default connect(
  mapStateToProps,
  { confirmRegistration }
)(VerifyRegistrationToken);
