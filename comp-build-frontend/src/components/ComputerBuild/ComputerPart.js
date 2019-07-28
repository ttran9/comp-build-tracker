import React, { Component } from "react";

class ComputerPart extends Component {
  render() {
    const { computerPart } = this.props;
    return (
      <tr>
        <td>{computerPart.name}</td>
        <td>{computerPart.price}</td>
        <td>{computerPart.purchaseDate}</td>
        <td>{computerPart.placePurchasedAt}</td>
        <td>{computerPart.otherNotes}</td>
      </tr>
    );
  }
}

export default ComputerPart;
