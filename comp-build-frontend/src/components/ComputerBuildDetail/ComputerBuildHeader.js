import React, { Component, Fragment } from "react";
import checkOwner from "../../securityUtils/checkOwner";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteComputerBuildByBuildIdentifier } from "../../actions/computerBuildDetailActions";

class ComputerBuildHeader extends Component {
  onDelete = buildIdentifier => {
    this.props.deleteComputerBuildByBuildIdentifier(buildIdentifier);
  };

  render() {
    const { computerBuild } = this.props;
    const isOwner = checkOwner(this.props);

    let deleteBuildForm = <Fragment />;

    if (isOwner) {
      deleteBuildForm = (
        <div>
          <div className="row">
            <div className="col-sm-4" />
            <div className="col-sm-4">
              <span
                onClick={this.onDelete.bind(
                  this,
                  computerBuild.buildIdentifier
                )}
                className="btn btn-primary"
              >
                Delete This Build?
              </span>
            </div>
            <div className="col-sm-4" />
          </div>
          <br />
        </div>
      );
    }

    return (
      <div className="col-md-12 text-center">
        <h1>{computerBuild.name}</h1>
        <h3>{computerBuild.buildDescription}</h3>
        {deleteBuildForm}
        <p />
      </div>
    );
  }
}

ComputerBuildHeader.propTypes = {
  security: PropTypes.object.isRequired,
  deleteComputerBuildByBuildIdentifier: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { deleteComputerBuildByBuildIdentifier }
)(ComputerBuildHeader);
