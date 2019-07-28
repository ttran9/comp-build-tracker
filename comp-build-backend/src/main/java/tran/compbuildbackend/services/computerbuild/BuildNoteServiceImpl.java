package tran.compbuildbackend.services.computerbuild;

import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.BuildNote;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.BuildNoteRepository;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_BUILD_DOES_NOT_EXIST;
import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.DEFAULT_PRIORITY;

@Service
public class BuildNoteServiceImpl implements BuildNoteService {

    private BuildNoteRepository buildNoteRepository;

    private ComputerBuildRepository computerBuildRepository;

    public BuildNoteServiceImpl(BuildNoteRepository buildNoteRepository, ComputerBuildRepository computerBuildRepository) {
        this.buildNoteRepository = buildNoteRepository;
        this.computerBuildRepository = computerBuildRepository;
    }

    @Override
    public BuildNote create(ComputerBuild computerBuild, BuildNote buildNote) {
        verifyOwnerOfComputerBuild(computerBuild.getBuildIdentifier());

        if(buildNote.getPriority() < 1 || buildNote.getPriority() > 3) {
            buildNote.setPriority(DEFAULT_PRIORITY);
        }

        buildNote.setComputerBuild(computerBuild);

        return buildNoteRepository.save(buildNote);
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
