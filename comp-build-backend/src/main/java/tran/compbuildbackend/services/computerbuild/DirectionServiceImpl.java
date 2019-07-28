package tran.compbuildbackend.services.computerbuild;

import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.ComputerPart;
import tran.compbuildbackend.domain.computerbuild.Direction;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.computerbuild.ComputerPartRepository;
import tran.compbuildbackend.repositories.computerbuild.DirectionRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_BUILD_DOES_NOT_EXIST;

@Service
public class DirectionServiceImpl implements DirectionService {

    private DirectionRepository directionRepository;

    private ComputerBuildRepository computerBuildRepository;

    public DirectionServiceImpl(DirectionRepository directionRepository, ComputerBuildRepository computerBuildRepository) {
        this.directionRepository = directionRepository;
        this.computerBuildRepository = computerBuildRepository;
    }

    @Override
    public Direction createDirection(ComputerBuild computerBuild, Direction direction) {
        verifyOwnerOfComputerBuild(computerBuild.getBuildIdentifier());

        direction.setComputerBuild(computerBuild);

        return directionRepository.save(direction);
    }

    private ComputerBuild verifyOwnerOfComputerBuild(String buildIdentifier) {
        ApplicationUser user = SecurityUtil.getLoggedInUser();

        ComputerBuild oldBuild = getComputerBuildByBuildIdentifier(buildIdentifier);

        if(oldBuild.getUser().getUsername().equals(user.getUsername())) {
            return oldBuild;
        }
        return null;
    }

    public ComputerBuild getComputerBuildByBuildIdentifier(String buildIdentifier) {
        ComputerBuild computerBuild = computerBuildRepository.getComputerBuildByBuildIdentifier(buildIdentifier);
        if(computerBuild == null) {
            throw new GenericRequestException(COMPUTER_BUILD_DOES_NOT_EXIST);
        }
        if(computerBuild.getId() == null || computerBuild.getUser() == null) {
            throw new GenericRequestException(COMPUTER_BUILD_DOES_NOT_EXIST);
        }
        return computerBuild;
    }
}
