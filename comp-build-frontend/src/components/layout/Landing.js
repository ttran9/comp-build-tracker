import React, { Component, Fragment } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import ComputerBuild from "../ComputerBuild/ComputerBuild";
import { getComputerBuilds } from "../../actions/computerBuildActions";
import CreateComputerBuildButton from "../ComputerBuild/CreateComputerBuildButton";

class Landing extends Component {
  componentDidMount() {
    this.props.getComputerBuilds();
  }

  render() {
    const { validToken } = this.props.security;

    const computerBuilds = this.props.computerBuild.computerBuilds;

    return (
      <div className="light-overlay landing-inner text-dark">
        <div className="container">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Computer Build Tracker</h1> <br />
              {validToken && <CreateComputerBuildButton />}
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
