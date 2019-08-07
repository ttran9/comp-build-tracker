import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import {
  getObjectById,
  updateObject
} from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";
import { GET_COMPUTERPART } from "../../actions/types";

class EditComputerPart extends Component {
  constructor() {
    super();

    this.state = {
      id: "",
      uniqueIdentifier: "",
      computerBuild: "",
      name: "",
      purchaseDate: "",
      price: "",
      placePurchasedAt: "",
      otherNote: "",
      errors: {}
    };
  }

  componentWillReceiveProps(newProps) {
    if (newProps.errors) {
      this.setState({
        errors: newProps.errors
      });
    }
    const {
      id,
      uniqueIdentifier,
      computerBuild,
      name,
      purchaseDate,
      price,
      placePurchasedAt,
      otherNote
    } = newProps.computerPart;

    this.setState({
      id,
      uniqueIdentifier,
      computerBuild,
      name,
      purchaseDate,
      price,
      placePurchasedAt,
      otherNote
    });
  }

  componentDidMount() {
    const { buildIdentifier, uniqueIdentifier } = this.props.match.params;
    this.props.getObjectById(
      Constants.COMPUTER_PART_API,
      buildIdentifier,
      uniqueIdentifier,
      GET_COMPUTERPART
    );
  }

  onSubmit = event => {
    event.preventDefault();

    const { buildIdentifier } = this.props.match.params;

    const newComputerPart = {
      id: this.state.id,
      uniqueIdentifier: this.state.uniqueIdentifier,
      computerBuild: buildIdentifier,
      name: this.state.name,
      purchaseDate: this.state.purchaseDate,
      price: this.state.price,
      placePurchasedAt: this.state.placePurchasedAt,
      otherNote: this.state.otherNote
    };

    this.props.updateObject(
      newComputerPart,
      buildIdentifier,
      this.state.uniqueIdentifier,
      this.props.history,
      Constants.COMPUTER_PART_API
    );
  };

  onChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  render() {
    const { errors } = this.state;
    return (
      <div className="computer-part">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Edit Computer Part</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <label for="name">Part Name</label>
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.name
                    })}
                    placeholder="Part Name"
                    name="name"
                    id="name"
                    value={this.state.name}
                    onChange={this.onChange}
                  />
                  {errors.name && (
                    <div className="invalid-feedback">{errors.name}</div>
                  )}
                </div>
                <div className="form-group">
                  <label for="purchaseDate">Purchase Date</label>
                  <input
                    type="date"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.purchaseDate
                    })}
                    name="purchaseDate"
                    id="purchaseDate"
                    value={this.state.purchaseDate}
                    onChange={this.onChange}
                  />
                  {errors.purchaseDate && (
                    <div className="invalid-feedback">
                      {errors.purchaseDate}
                    </div>
                  )}
                </div>
                <div className="form-group">
                  <label for="price">Price</label>
                  <input
                    type="number"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.price
                    })}
                    placeholder="19.99"
                    name="price"
                    id="price"
                    value={this.state.price}
                    onChange={this.onChange}
                  />
                  {errors.price && (
                    <div className="invalid-feedback">{errors.price}</div>
                  )}
                </div>
                <div className="form-group">
                  <label for="placePurchasedAt">Place Purchased At</label>
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.placePurchasedAt
                    })}
                    placeholder="Place of Purchase"
                    name="placePurchasedAt"
                    id="placePurchasedAt"
                    value={this.state.placePurchasedAt}
                    onChange={this.onChange}
                  />
                  {errors.placePurchasedAt && (
                    <div className="invalid-feedback">
                      {errors.placePurchasedAt}
                    </div>
                  )}
                </div>
                <div className="form-group">
                  <label for="otherNote">Other Notes</label>
                  <textarea
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.otherNote
                    })}
                    placeholder="Other Notes"
                    name="otherNote"
                    id="otherNote"
                    value={this.state.otherNote}
                    onChange={this.onChange}
                  />
                  {errors.otherNote && (
                    <div className="invalid-feedback">{errors.otherNote}</div>
                  )}
                </div>
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

EditComputerPart.propTypes = {
  errors: PropTypes.object.isRequired,
  getObjectById: PropTypes.func.isRequired,
  updateObject: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  computerPart: state.computerBuildDetails.computerPart
});

export default connect(
  mapStateToProps,
  { getObjectById, updateObject }
)(EditComputerPart);
