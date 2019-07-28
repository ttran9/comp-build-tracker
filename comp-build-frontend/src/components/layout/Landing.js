import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import ComputerBuild from "../ComputerBuild/ComputerBuild";
import { getComputerBuilds } from "../../actions/computerBuildActions";

class Landing extends Component {
  componentDidMount() {
    this.props.getComputerBuilds();
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

    const computerBuilds = this.props.computerBuild.computerBuilds;

    return (
      <div className="light-overlay landing-inner text-dark">
        <div className="container">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Computer Build Tracker</h1>
              <hr />
              {computerBuilds.map(computerBuild => (
                <ComputerBuild
                  key={computerBuild.buildIdentifier}
                  computerBuild={computerBuild}
                />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Landing.propTypes = {
  security: PropTypes.object.isRequired,
  computerBuild: PropTypes.object.isRequired,
  getComputerBuilds: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  security: state.security,
  computerBuild: state.computerBuild
});

export default connect(
  mapStateToProps,
  { getComputerBuilds }
)(Landing);
