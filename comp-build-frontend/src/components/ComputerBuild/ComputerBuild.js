import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteComputerBuildByIdentifier } from "../../actions/computerBuildActions";
import * as Constants from "../../Constants";

class ComputerBuild extends Component {
  onDelete = buildIdentifier => {
    this.props.deleteComputerBuildByIdentifier(buildIdentifier);
  };

  constructor() {
    super();
    this.state = {
      isOwner: false
    };
  }

  componentDidMount() {
    const user = this.props.security.user;
    if (user !== undefined) {
      const { computerBuild } = this.props;
      if (user.username !== undefined) {
        if (user.username === computerBuild.username) {
          this.setState({
            isOwner: true
          });
        }
      }
    }
  }

  render() {
    const { computerBuild } = this.props;

    let deleteButton = <Fragment />;

    if (this.state.isOwner) {
      deleteButton = (
        <li
          className="list-group-item delete"
          onClick={this.onDelete.bind(this, computerBuild.buildIdentifier)}
        >
          <i className="fa fa-minus-circle pr-1"> Delete</i>
        </li>
      );
    }

    return (
      <div className="container">
        <div className="card card-body bg-light mb-3">
          <div className="row">
            <div className="col-sm-8">
              <h3>{computerBuild.name}</h3>
              <p>{computerBuild.buildDescription}</p>
            </div>
            <div className="col-sm-4">
              <ul className="list-group">
                <Link
                  to={`${Constants.COMPUTER_BUILD_URL}${
                    computerBuild.buildIdentifier
                  }`}
                >
                  <li className="list-group-item computer-build">
                    <i className="fa fa-flag-checkered pr-1">
                      {" "}
                      Details For This Build
                    </i>
                  </li>
                </Link>
                {deleteButton}
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ComputerBuild.propTypes = {
  deleteComputerBuildByIdentifier: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { deleteComputerBuildByIdentifier }
)(ComputerBuild);