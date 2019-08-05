import React, { Component } from "react";
import { connect } from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import {
  getBuildNoteById,
  updateBuildNote
} from "../../actions/computerBuildDetailActions";

class EditBuildNote extends Component {
  constructor() {
    super();

    this.state = {
      id: "",
      uniqueIdentifier: "",
      computerBuild: "",
      description: "",
      priority: "",
      errors: {}
    };
  }

  onSubmit = event => {
    event.preventDefault();

    const { buildIdentifier } = this.props.match.params;

    const newBuildNote = {
      id: this.state.id,
      uniqueIdentifier: this.state.uniqueIdentifier,
      computerBuild: buildIdentifier,
      priority: this.state.priority,
      description: this.state.description
    };

    this.props.updateBuildNote(
      newBuildNote,
      buildIdentifier,
      this.state.uniqueIdentifier,
      this.props.history
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
      description,
      priority
    } = newProps.buildNote;

    this.setState({
      id,
      uniqueIdentifier,
      computerBuild,
      description,
      priority
    });
  };

  componentDidMount() {
    const { buildIdentifier, uniqueIdentifier } = this.props.match.params;
    this.props.getBuildNoteById(buildIdentifier, uniqueIdentifier);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="build-note">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Edit Build Note</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <label for="description">Build Note Description</label>
                  <textarea
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.description
                    })}
                    placeholder="Build Note Description"
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

EditBuildNote.propTypes = {
  errors: PropTypes.object.isRequired,
  buildNote: PropTypes.object.isRequired,
  updateBuildNote: PropTypes.func.isRequired,
  getBuildNoteById: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  buildNote: state.computerBuildDetails.buildNote
});

export default connect(
  mapStateToProps,
  { updateBuildNote, getBuildNoteById }
)(EditBuildNote);
