import React, { Component, Fragment } from "react";
import ComputerPart from "./ComputerPart";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class ComputerPartList extends Component {
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
    const { computerParts, buildIdentifier, isOwner } = this.props;
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
                  Constants.CREATE_COMPUTER_PART_URL
                }`}
              >
                Create Computer Part!
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
          Computer Parts:{" "}
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
              <th scope="col">Part Name</th>
              <th scope="col">Price</th>
              <th scope="col">Purchase Date</th>
              <th scope="col">Place of Purchase</th>
              <th scope="col">Other notes</th>
              {editAndDeleteRows}
            </tr>
          </thead>
          <tbody>
            {computerParts.map(computerPart => {
              return (
                <ComputerPart
                  key={computerPart.id}
                  computerPart={computerPart}
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

ComputerPartList.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  null
)(ComputerPartList);
