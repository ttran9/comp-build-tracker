import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteBuildNoteById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";

class BuildNote extends Component {
  onDeleteClick = (buildIdentifier, buildNoteIdentifier) => {
    this.props.deleteBuildNoteById(buildIdentifier, buildNoteIdentifier);
  };

  render() {
    const { buildNote, buildIdentifier, isOwner } = this.props;
    const { uniqueIdentifier } = buildNote;
    let editAndDeleteRows = <Fragment />;

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <td>
            <Link
              to={`${
                Constants.UPDATE_BUILD_NOTE_URL
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
        <td>{buildNote.description}</td>
        {editAndDeleteRows}
      </tr>
    );
  }
}

BuildNote.propTypes = {
  deleteBuildNoteById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteBuildNoteById }
)(BuildNote);
