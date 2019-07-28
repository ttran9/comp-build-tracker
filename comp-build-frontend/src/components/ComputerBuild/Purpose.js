import React, { Component } from "react";

class Purpose extends Component {
  render() {
    const { purpose } = this.props;
    return (
      <tr>
        <td>{purpose.description}</td>
      </tr>
    );
  }
}

export default Purpose;
