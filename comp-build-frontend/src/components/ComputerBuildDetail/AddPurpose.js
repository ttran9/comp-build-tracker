import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import { createObject } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";

class AddPurpose extends Component {
  constructor() {
    super();

    this.state = {
      description: "",
      priority: 1,
      errors: {}
    };
  }

  onSubmit = event => {
    event.preventDefault();

    const newPurpose = {
      description: this.state.description,
      priority: this.state.priority
    };

    const { buildIdentifier } = this.props.match.params;

    this.props.createObject(
      newPurpose,
      this.props.history,
      Constants.PURPOSE_API,
      buildIdentifier
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
  };

  render() {
    const { errors } = this.state;
    return (
      <div className="build-note">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Add Purpose</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <label for="description">Purpose Description</label>
                  <textarea
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.description
                    })}
                    placeholder="Purpose Description"
                    name="description"
                    id="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  />
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    id="priority"
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value={1}>Low</option>
                    <option value={2}>Medium</option>
                    <option value={3}>High</option>
                  </select>
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

AddPurpose.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
  createObject: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  security: state.security
});

export default connect(
  mapStateToProps,
  { createObject }
)(AddPurpose);
