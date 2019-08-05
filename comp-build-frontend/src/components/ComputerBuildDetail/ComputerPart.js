import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteComputerPartById } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";

class ComputerPart extends Component {
  onDeleteClick = (buildIdentifier, computerPartIdentifier) => {
    this.props.deleteComputerPartById(buildIdentifier, computerPartIdentifier);
  };

  render() {
    const { computerPart, buildIdentifier, isOwner } = this.props;
    const { uniqueIdentifier } = computerPart;
    let editAndDeleteRows = <Fragment />;

    if (isOwner) {
      editAndDeleteRows = (
        <Fragment>
          <td>
            <Link
              to={`${
                Constants.UPDATE_COMPUTER_PART_URL
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
        <td>{computerPart.name}</td>
        <td>{computerPart.price}</td>
        <td>{computerPart.purchaseDate}</td>
        <td>{computerPart.placePurchasedAt}</td>
        <td>{computerPart.otherNote}</td>
        {editAndDeleteRows}
      </tr>
    );
  }
}

ComputerPart.propTypes = {
  deleteComputerPartById: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteComputerPartById }
)(ComputerPart);
