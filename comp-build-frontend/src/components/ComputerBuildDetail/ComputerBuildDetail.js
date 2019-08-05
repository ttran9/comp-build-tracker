import React, { Component } from "react";
import { getComputerBuildFromId } from "../../actions/computerBuildDetailActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import ComputerBuildHeader from "./ComputerBuildHeader";
import ComputerPartList from "./ComputerPartList";
import DirectionsList from "./DirectionsList";
import PurposeList from "./PurposeList";
import BuildNoteList from "./BuildNoteList";
import OverclockingNoteList from "./OverclockingNoteList";

class ComputerBuildDetail extends Component {
  componentDidMount() {
    const { buildIdentifier } = this.props.match.params;
    this.props.getComputerBuildFromId(buildIdentifier);
  }

  checkOwner = props => {
    const user = props.security.user;
    if (user !== undefined) {
      const { computerBuild } = props.computerBuildDetails;
      if (user.username !== undefined && computerBuild.username !== undefined) {
        if (user.username === computerBuild.username) {
          return true;
        }
      }
    }
    return false;
  };

  render() {
    const { buildIdentifier } = this.props.match.params;
    const { computerBuildDetails } = this.props;

    const isOwner = this.checkOwner(this.props);

    const {
      computerParts,
      directions,
      purposeList,
      buildNotes,
      overclockingNotes,
      computerBuild
    } = computerBuildDetails;
    return (
      <div className="container">
        <ComputerBuildHeader computerBuild={computerBuild} />
        <ComputerPartList
          computerParts={computerParts}
          buildIdentifier={buildIdentifier}
          isOwner={isOwner}
        />
        <PurposeList
          purposeList={purposeList}
          buildIdentifier={buildIdentifier}
          isOwner={isOwner}
        />
        <OverclockingNoteList
          overclockingNotes={overclockingNotes}
          buildIdentifier={buildIdentifier}
          isOwner={isOwner}
        />
        <BuildNoteList
          buildNotes={buildNotes}
          buildIdentifier={buildIdentifier}
          isOwner={isOwner}
        />
        <DirectionsList
          directions={directions}
          buildIdentifier={buildIdentifier}
          isOwner={isOwner}
        />
      </div>
    );
  }
}

ComputerBuildDetail.propTypes = {
  getComputerBuildFromId: PropTypes.func.isRequired,
  computerBuildDetails: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  computerBuildDetails: state.computerBuildDetails,
  security: state.security
});

export default connect(
  mapStateToProps,
  { getComputerBuildFromId }
)(ComputerBuildDetail);
