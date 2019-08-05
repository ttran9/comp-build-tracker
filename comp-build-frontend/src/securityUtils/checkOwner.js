const checkOwner = props => {
  const user = props.security.user;
  if (user !== undefined) {
    const { computerBuild } = props;
    if (user.username !== undefined && computerBuild.username !== undefined) {
      if (user.username === computerBuild.username) {
        return true;
      }
    }
  }
  return false;
};

export default checkOwner;
