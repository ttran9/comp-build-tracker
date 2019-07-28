import React, { Component } from "react";
import { getComputerBuildByBuildIdentifier } from "../../actions/computerBuildActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import ComputerBuildHeader from "./ComputerBuildHeader";
import ComputerPartList from "./ComputerPartList";
import DirectionsList from "./DirectionsList";
import PurposeList from "./PurposeList";
import BuildNoteList from "./BuildNoteList";
import OverclockingNoteList from "./OverclockingNoteList";

class ComputerBuildDetail extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { buildIdentifier } = this.props.match.params;
    this.props.getComputerBuildByBuildIdentifier(
      buildIdentifier,
      this.props.history
    );
  }

  render() {
    const { computerBuild } = this.props.computerBuild;
    const {
      computerParts,
      directions,
      purposeList,
      buildNotes,
      overclockingNotes
    } = computerBuild;
    return (
      <div className="container">
        <ComputerBuildHeader computerBuild={computerBuild} />
        {computerParts !== undefined && (
          <ComputerPartList computerParts={computerParts} />
        )}
        {purposeList !== undefined && <PurposeList purposeList={purposeList} />}
        {buildNotes !== undefined && <BuildNoteList buildNotes={buildNotes} />}
        {overclockingNotes !== undefined && (
          <OverclockingNoteList overclockingNotes={overclockingNotes} />
        )}
        {directions !== undefined && <DirectionsList directions={directions} />}
      </div>
    );
  }
}

ComputerBuildDetail.propTypes = {
  getComputerBuildByBuildIdentifier: PropTypes.func.isRequired,
  computerBuild: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  computerBuild: state.computerBuild
});
export default connect(
  mapStateToProps,
  { getComputerBuildByBuildIdentifier }
)(ComputerBuildDetail);
