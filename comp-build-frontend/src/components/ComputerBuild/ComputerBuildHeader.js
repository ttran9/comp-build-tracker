import React, { Component } from "react";

class ComputerBuildHeader extends Component {
  render() {
    const { computerBuild } = this.props;
    return (
      <div className="col-md-12 text-center">
        <p className="display-3 mb-4">{computerBuild.name}</p>
        <p className="display-3 mb-4">{computerBuild.buildDescription}</p>
      </div>
    );
  }
}

export default ComputerBuildHeader;
