import React, { Component } from "react";
import Purpose from "./Purpose";

class PurposeList extends Component {
  render() {
    const { purposeList } = this.props;
    return (
      <div className="col-md-12 text-center">
        <h2>Purpose:</h2>
        <table class="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Description</th>
            </tr>
          </thead>
          <tbody>
            {purposeList.map(purpose => {
              return <Purpose key={purpose.id} purpose={purpose} />;
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default PurposeList;
