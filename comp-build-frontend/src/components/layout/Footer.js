import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class Footer extends Component {
  render() {
    const { validToken } = this.props.security;

    const userIsNotAuthenticated = <span>Copyright, Todd Tran.</span>;

    const userIsAuthenticated = (
      <Fragment>
        <Link
          className="btn btn-primary"
          to={`${Constants.CHANGE_PASSWORD_URL}`}
        >
          Change Password!
        </Link>
        <br />
        <span>Created by Todd Tran.</span>
      </Fragment>
    );

    let footerContent;

    if (validToken) {
      footerContent = userIsAuthenticated;
    } else {
      footerContent = userIsNotAuthenticated;
    }

    return (
      <div className="container">
        <hr />
        <footer className="footer">
          <p className="text-center">{footerContent}</p>
        </footer>
      </div>
    );
  }
}

Footer.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(Footer);
