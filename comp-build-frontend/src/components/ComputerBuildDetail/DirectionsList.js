import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import Direction from "./Direction";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import * as Constants from "../../Constants";

class DirectionsList extends Component {
  render() {
    const { directions, buildIdentifier, isOwner } = this.props;

    let createButton = <Fragment />;
    let editAndDeleteRows = <Fragment />;

    if (isOwner && buildIdentifier !== undefined) {
      createButton = (
        <div>
          <div className="row">
            <div className="col-sm-4" />
            <div className="col-sm-4">
              <Link
                className="btn btn-primary"
                to={`${Constants.COMPUTER_BUILD_URL}${buildIdentifier}${
                  Constants.CREATE_DIRECTION_URL
                }`}
              >
                Create Direction!
              </Link>
            </div>
            <div className="col-sm-4" />
          </div>
          <br />
        </div>
      );
    }

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <th scope="col">Edit</th>
          <th scope="col">Delete</th>
        </Fragment>
      );
    }

    return (
      <div className="col-md-12 text-center">
        <h2>Directions:</h2>
        {createButton}
        <table className="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Description</th>
              {editAndDeleteRows}
            </tr>
          </thead>
          <tbody>
            {directions.map((direction, index) => {
              return (
                <Direction
                  key={direction.id}
                  index={index}
                  direction={direction}
                  buildIdentifier={buildIdentifier}
                  isOwner={isOwner}
                />
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

// DirectionsList.propTypes = {
//   getComputerBuildByBuildIdentifier: PropTypes.func.isRequired,
//   computerBuild: PropTypes.object.isRequired
// };

// const mapStateToProps = state => ({
//   computerBuild: state.computerBuild
// });

// export default connect(
//   mapStateToProps,
//   { getComputerBuildByBuildIdentifier }
// )(DirectionsList);

DirectionsList.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  null
)(DirectionsList);
