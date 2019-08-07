import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteObjectById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";
import { DELETE_PURPOSE } from "../../actions/types";

class Purpose extends Component {
  onDeleteClick = (buildIdentifier, purposeIdentifier) => {
    this.props.deleteObjectById(
      Constants.PURPOSE_API,
      buildIdentifier,
      purposeIdentifier,
      DELETE_PURPOSE
    );
  };

  render() {
    const { purpose, buildIdentifier, isOwner } = this.props;
    const { uniqueIdentifier } = purpose;
    let editAndDeleteRows = <Fragment />;

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <td>
            <Link
              to={`${
                Constants.UPDATE_PURPOSE_URL
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
        <td>{purpose.description}</td>
        {editAndDeleteRows}
      </tr>
    );
  }
}

Purpose.propTypes = {
  deleteObjectById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteObjectById }
)(Purpose);
