import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import { createDirection } from "../../actions/computerBuildDetailActions";
import * as Constants from "../../Constants";

class AddDirection extends Component {
  constructor() {
    super();

    this.state = {
      description: "",
      errors: {}
    };
  }

  onSubmit = event => {
    event.preventDefault();

    const newDirection = {
      description: this.state.description
    };

    const { buildIdentifier } = this.props.match.params;

    this.props.createDirection(
      newDirection,
      this.props.history,
      Constants.DIRECTION_API,
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
      <div className="direction">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Add Direction</h5>
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

AddDirection.propTypes = {
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
  createDirection: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  security: state.security
});

export default connect(
  mapStateToProps,
  { createDirection }
)(AddDirection);
