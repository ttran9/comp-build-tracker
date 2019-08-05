import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteOverclockingNoteById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";

class OverclockingNote extends Component {
  onDeleteClick = (buildIdentifier, overclockingNoteIdentifier) => {
    this.props.deleteOverclockingNoteById(
      buildIdentifier,
      overclockingNoteIdentifier
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
  deleteOverclockingNoteById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteOverclockingNoteById }
)(OverclockingNote);
