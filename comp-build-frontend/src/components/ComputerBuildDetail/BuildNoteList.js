import React, { Component, Fragment } from "react";
import BuildNote from "./BuildNote";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class BuildNoteList extends Component {
  constructor() {
    super();

    this.state = {
      isVisible: true
    };
  }

  toggleVisibility = () => {
    this.setState({
      isVisible: !this.state.isVisible
    });
  };

  render() {
    const { buildNotes, buildIdentifier, isOwner } = this.props;
    let createButton = <Fragment />;
    let editAndDeleteRows = <Fragment />;
    const { isVisible } = this.state;

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
        <h2>
          Build Notes:{" "}
          <span onClick={this.toggleVisibility}>
            <button className="btn btn-primary">
              {isVisible ? <span>Hide</span> : <span>Show</span>}
            </button>
          </span>
        </h2>
        {createButton}
        <table
          className={
            isVisible
              ? "table table-dark table-bordered"
              : "table table-dark table-bordered hide-element"
          }
        >
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
