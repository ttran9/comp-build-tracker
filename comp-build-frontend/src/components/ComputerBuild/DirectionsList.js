import React, { Component } from "react";
import Direction from "./Direction";

class DirectionsList extends Component {
  render() {
    const { directions } = this.props;
    return (
      <div className="col-md-12 text-center">
        <h2>Directions:</h2>
        <table class="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Description</th>
            </tr>
          </thead>
          <tbody>
            {directions.map((direction, index) => {
              return (
                <Direction
                  key={direction.id}
                  index={index}
                  direction={direction}
                />
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

// DirectionsList.propTypes = {
//   getComputerBuildByBuildIdentifier: PropTypes.func.isRequired,
//   computerBuild: PropTypes.object.isRequired
// };

// const mapStateToProps = state => ({
//   computerBuild: state.computerBuild
// });

// export default connect(
//   mapStateToProps,
//   { getComputerBuildByBuildIdentifier }
// )(DirectionsList);

export default DirectionsList;
