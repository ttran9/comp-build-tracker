import React, { Component } from "react";

class OverclockingNote extends Component {
  render() {
    const { overclockingNote } = this.props;
    return (
      <tr>
        <td>{overclockingNote.description}</td>
      </tr>
    );
  }
}

export default OverclockingNote;
