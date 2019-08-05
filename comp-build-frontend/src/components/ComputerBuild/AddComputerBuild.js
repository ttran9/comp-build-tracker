import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createComputerBuild } from "../../actions/computerBuildActions";
import classnames from "classnames";

class AddComputerBuild extends Component {
  constructor() {
    super();

    // set initial state for input fields.
    this.state = {
      name: "",
      buildDescription: "",
      errors: {}
    };
  }

  componentWillReceiveProps = newProps => {
    if (newProps.errors) {
      this.setState({
        errors: newProps.errors
      });
    }
  };

  onChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  onSubmit = event => {
    event.preventDefault();
    const newBuild = {
      name: this.state.name,
      buildDescription: this.state.buildDescription
    };

    this.props.createComputerBuild(newBuild, this.props.history);
  };

  render() {
    const { errors } = this.state;
    return (
      <div>
        <div className="computer-build">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Add Computer Build</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group">
                    <label for="buildDescription">Build Name</label>
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.name
                      })}
                      placeholder="Build Name"
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
                    <label for="buildDescription">Build Description</label>
                    <textarea
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.buildDescription
                      })}
                      placeholder="Build Description"
                      name="buildDescription"
                      id="buildDescription"
                      value={this.state.buildDescription}
                      onChange={this.onChange}
                    />
                    {errors.buildDescription && (
                      <div className="invalid-feedback">
                        {errors.buildDescription}
                      </div>
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
      </div>
    );
  }
}

AddComputerBuild.propTypes = {
  createComputerBuild: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { createComputerBuild }
)(AddComputerBuild);
