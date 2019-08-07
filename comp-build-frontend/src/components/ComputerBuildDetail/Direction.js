import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteObjectById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";
import { DELETE_DIRECTION } from "../../actions/types";

class Direction extends Component {
  onDeleteClick = (buildIdentifier, directionIdentifier) => {
    this.props.deleteObjectById(
      Constants.DIRECTION_API,
      buildIdentifier,
      directionIdentifier,
      DELETE_DIRECTION
    );
  };

  render() {
    const { direction, index, isOwner, buildIdentifier } = this.props;
    const { uniqueIdentifier } = direction;
    let editAndDeleteRows = <Fragment />;

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <td>
            <Link
              to={`${
                Constants.UPDATE_DIRECTION_URL
              }${buildIdentifier}/${uniqueIdentifier}`}
            >
              <i className="far fa-edit pr-1" />
            </Link>
          </td>
          <td>
            <i
              onClick={this.onDeleteClick.bind(
                this,
                buildIdentifier,
                uniqueIdentifier
              )}
              className="fas fa-trash pr-1"
            />
          </td>
        </Fragment>
      );
    }

    return (
      <tr>
        <td>{index + 1}.</td>
        <td>{direction.description}</td>
        {editAndDeleteRows}
      </tr>
    );
  }
}

Direction.propTypes = {
  deleteObjectById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteObjectById }
)(Direction);
