import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteObjectById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";
import { DELETE_OVERCLOCKING_NOTE } from "../../actions/types";

class OverclockingNote extends Component {
  onDeleteClick = (buildIdentifier, overclockingNoteIdentifier) => {
    this.props.deleteObjectById(
      Constants.OVERCLOCKING_NOTE_API,
      buildIdentifier,
      overclockingNoteIdentifier,
      DELETE_OVERCLOCKING_NOTE
    );
  };

  render() {
    const { overclockingNote, buildIdentifier, isOwner } = this.props;
    const { uniqueIdentifier } = overclockingNote;
    let editAndDeleteRows = <Fragment />;

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <td>
            <Link
              to={`${
                Constants.UPDATE_OVERCLOCKING_NOTE_URL
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
        <td>{overclockingNote.description}</td>
        {editAndDeleteRows}
      </tr>
    );
  }
}

OverclockingNote.propTypes = {
  deleteObjectById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteObjectById }
)(OverclockingNote);
