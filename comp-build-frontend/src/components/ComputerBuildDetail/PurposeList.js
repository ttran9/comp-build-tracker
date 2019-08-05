import React, { Component, Fragment } from "react";
import Purpose from "./Purpose";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";

class PurposeList extends Component {
  render() {
    const { purposeList, buildIdentifier, isOwner } = this.props;
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
                  Constants.CREATE_PURPOSE_URL
                }`}
              >
                Create Purpose!
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
        <h2>Purpose:</h2>
        {createButton}
        <table className="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Description</th>
              {editAndDeleteRows}
            </tr>
          </thead>
          <tbody>
            {purposeList.map(purpose => {
              return (
                <Purpose
                  key={purpose.id}
                  purpose={purpose}
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

PurposeList.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  null
)(PurposeList);
