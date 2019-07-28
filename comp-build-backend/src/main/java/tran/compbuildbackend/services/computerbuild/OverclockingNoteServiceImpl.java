package tran.compbuildbackend.services.computerbuild;

import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.OverclockingNote;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.BuildNoteRepository;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.computerbuild.OverclockingNoteRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_BUILD_DOES_NOT_EXIST;
import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.DEFAULT_PRIORITY;

@Service
public class OverclockingNoteServiceImpl implements OverclockingNoteService {

    private OverclockingNoteRepository overclockingNoteRepository;

    private ComputerBuildRepository computerBuildRepository;

    public OverclockingNoteServiceImpl(OverclockingNoteRepository overclockingNoteRepository, ComputerBuildRepository computerBuildRepository) {
        this.overclockingNoteRepository = overclockingNoteRepository;
        this.computerBuildRepository = computerBuildRepository;
    }

    @Override
    public OverclockingNote create(ComputerBuild computerBuild, OverclockingNote overclockingNote) {
        verifyOwnerOfComputerBuild(computerBuild.getBuildIdentifier());

        if(overclockingNote.getPriority() < 1 || overclockingNote.getPriority() > 3) {
            overclockingNote.setPriority(DEFAULT_PRIORITY);
        }

        overclockingNote.setComputerBuild(computerBuild);

        return overclockingNoteRepository.save(overclockingNote);
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
