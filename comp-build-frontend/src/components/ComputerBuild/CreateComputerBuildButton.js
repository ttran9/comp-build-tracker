import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import * as Constants from "../../Constants";

const CreateComputerBuildButton = () => {
  return (
    <Fragment>
      <Link
        to={`${Constants.CREATE_COMPUTER_BUILD_URL}`}
        className="btn btn-lg btn-info"
      >
        Create a Computer Build!
      </Link>
    </Fragment>
  );
};

export default CreateComputerBuildButton;
