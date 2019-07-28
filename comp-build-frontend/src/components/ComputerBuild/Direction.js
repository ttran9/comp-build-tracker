import React, { Component } from "react";

class Direction extends Component {
  render() {
    const { direction, index } = this.props;
    return (
      <tr>
        <td>{index + 1}.</td>
        <td>{direction.description}</td>
      </tr>
    );
  }
}

export default Direction;
