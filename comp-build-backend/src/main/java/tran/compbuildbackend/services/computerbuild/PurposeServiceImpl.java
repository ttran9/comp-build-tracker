package tran.compbuildbackend.services.computerbuild;

import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.Purpose;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.computerbuild.PurposeRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_BUILD_DOES_NOT_EXIST;
import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.DEFAULT_PRIORITY;

@Service
public class PurposeServiceImpl implements PurposeService {

    private PurposeRepository purposeRepository;

    private ComputerBuildRepository computerBuildRepository;

    public PurposeServiceImpl(PurposeRepository purposeRepository, ComputerBuildRepository computerBuildRepository) {
        this.purposeRepository = purposeRepository;
        this.computerBuildRepository = computerBuildRepository;
    }

    @Override
    public Purpose create(ComputerBuild computerBuild, Purpose purpose) {
        verifyOwnerOfComputerBuild(computerBuild.getBuildIdentifier());

        if(purpose.getPriority() < 1 || purpose.getPriority() > 3) {
            purpose.setPriority(DEFAULT_PRIORITY);
        }

        purpose.setComputerBuild(computerBuild);

        return purposeRepository.save(purpose);
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
