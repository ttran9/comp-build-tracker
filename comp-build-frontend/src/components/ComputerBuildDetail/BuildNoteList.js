import React, { Component, Fragment } from "react";
import BuildNote from "./BuildNote";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class BuildNoteList extends Component {
  render() {
    const { buildNotes, buildIdentifier, isOwner } = this.props;
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
                  Constants.CREATE_BUILD_NOTE_URL
                }`}
              >
                Create Build Note!
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
        <h2>Build Notes:</h2>
        {createButton}
        <table className="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Description</th>
              {editAndDeleteRows}
            </tr>
          </thead>
          <tbody>
            {buildNotes.map(buildNote => {
              return (
                <BuildNote
                  key={buildNote.id}
                  buildNote={buildNote}
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

BuildNoteList.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  null
)(BuildNoteList);
