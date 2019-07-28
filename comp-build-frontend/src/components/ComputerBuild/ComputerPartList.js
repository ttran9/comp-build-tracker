import React, { Component } from "react";
import ComputerPart from "./ComputerPart";

class ComputerPartList extends Component {
  render() {
    const { computerParts } = this.props;
    return (
      <div className="col-md-12 text-center">
        <h2>Computer Parts:</h2>
        <table class="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Part Name</th>
              <th scope="col">Price</th>
              <th scope="col">Purchase Date</th>
              <th scope="col">Place of Purchase</th>
              <th scope="col">Other notes</th>
            </tr>
          </thead>
          <tbody>
            {computerParts.map(computerPart => {
              return (
                <ComputerPart
                  key={computerPart.id}
                  computerPart={computerPart}
                />
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

// ComputerPartList.propTypes = {
//   getComputerBuildByBuildIdentifier: PropTypes.func.isRequired,
//   computerBuild: PropTypes.object.isRequired
// };

// const mapStateToProps = state => ({
//   computerBuild: state.computerBuild
// });

// export default connect(
//   mapStateToProps,
//   { getComputerBuildByBuildIdentifier }
// )(ComputerPartList);

export default ComputerPartList;
