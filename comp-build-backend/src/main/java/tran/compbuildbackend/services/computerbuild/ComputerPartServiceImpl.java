package tran.compbuildbackend.services.computerbuild;

import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.ComputerPart;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.computerbuild.ComputerPartRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_BUILD_DOES_NOT_EXIST;

@Service
public class ComputerPartServiceImpl implements ComputerPartService {

    private ComputerPartRepository computerPartRepository;

    private ComputerBuildRepository computerBuildRepository;

    public ComputerPartServiceImpl(ComputerPartRepository computerPartRepository, ComputerBuildRepository computerBuildRepository) {
        this.computerPartRepository = computerPartRepository;
        this.computerBuildRepository = computerBuildRepository;
    }

    @Override
    public ComputerPart createComputerPart(ComputerBuild computerBuild, ComputerPart computerPart) {

        verifyOwnerOfComputerBuild(computerBuild.getBuildIdentifier());

        computerPart.setComputerBuild(computerBuild);

        return computerPartRepository.save(computerPart);
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
