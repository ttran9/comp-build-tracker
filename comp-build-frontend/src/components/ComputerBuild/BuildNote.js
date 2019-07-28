import React, { Component } from "react";

class BuildNote extends Component {
  render() {
    const { buildNote } = this.props;
    return (
      <tr>
        <td>{buildNote.description}</td>
      </tr>
    );
  }
}

export default BuildNote;
