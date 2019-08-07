import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import {
  getObjectById,
  updateObject
} from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";
import { GET_DIRECTION } from "../../actions/types";

class EditDirection extends Component {
  constructor() {
    super();

    this.state = {
      id: "",
      uniqueIdentifier: "",
      computerBuild: "",
      description: "",
      errors: {}
    };
  }

  onSubmit = event => {
    event.preventDefault();

    const { buildIdentifier } = this.props.match.params;

    const newDirection = {
      id: this.state.id,
      uniqueIdentifier: this.state.uniqueIdentifier,
      computerBuild: buildIdentifier,
      description: this.state.description
    };

    this.props.updateObject(
      newDirection,
      buildIdentifier,
      this.state.uniqueIdentifier,
      this.props.history,
      Constants.DIRECTION_API
    );
  };

  onChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  componentWillReceiveProps = newProps => {
    if (newProps.errors) {
      this.setState({
        errors: newProps.errors
      });
    }

    const {
      id,
      uniqueIdentifier,
      computerBuild,
      description
    } = newProps.direction;

    this.setState({
      id,
      uniqueIdentifier,
      computerBuild,
      description
    });
  };

  componentDidMount() {
    const { buildIdentifier, uniqueIdentifier } = this.props.match.params;
    this.props.getObjectById(
      Constants.DIRECTION_API,
      buildIdentifier,
      uniqueIdentifier,
      GET_DIRECTION
    );
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="direction">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Edit Direction</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <label for="description">Direction Description</label>
                  <textarea
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.description
                    })}
                    placeholder="Direction Notes"
                    name="description"
                    id="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  />
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
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

EditDirection.propTypes = {
  errors: PropTypes.object.isRequired,
  direction: PropTypes.object.isRequired,
  updateObject: PropTypes.func.isRequired,
  getObjectById: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  direction: state.computerBuildDetails.direction
});

export default connect(
  mapStateToProps,
  { updateObject, getObjectById }
)(EditDirection);
