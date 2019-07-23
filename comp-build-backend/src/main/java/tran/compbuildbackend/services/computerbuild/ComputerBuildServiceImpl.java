package tran.compbuildbackend.services.computerbuild;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.users.ApplicationUserRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.*;

@Service
public class ComputerBuildServiceImpl implements ComputerBuildService {

    private ComputerBuildRepository computerBuildRepository;

    private ApplicationUserRepository applicationUserRepository;

    public ComputerBuildServiceImpl(ComputerBuildRepository computerBuildRepository, ApplicationUserRepository applicationUserRepository) {
        this.computerBuildRepository = computerBuildRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    /**
     * Adds a new computer build object in the database.
     * @param computerBuild The computer build object to be added.
     * @return Returns a computer build object with the associated user and a unique build identifier.
     */
    @Override
    public ComputerBuild createNewComputerBuild(ComputerBuild computerBuild) {

        // get the name of the user that is logged in.
        ApplicationUser user = SecurityUtil.getLoggedInUser();

        // set the user to be able to link the user and computer build that will be persisted.
        computerBuild.setUser(user);
        computerBuild.setBuildIdentifier(generateUniqueBuildIdentifier());

        return computerBuildRepository.save(computerBuild);
    }

    @Override
    public ComputerBuild updateNumberOfDirections(ComputerBuild updatedComputerBuild, String buildIdentifier) {
        ComputerBuild computerBuild = verifyOwnerOfComputerBuild(buildIdentifier);
        if(computerBuild != null) {
            return computerBuildRepository.save(updatedComputerBuild);
        }

        throw new GenericRequestException(COMPUTER_BUILD_CANNOT_BE_UPDATED);
    }

    @Override
    public void deleteComputerBuild(String buildIdentifier) {
        ComputerBuild computerBuild = verifyOwnerOfComputerBuild(buildIdentifier);
        if(computerBuild == null) {
            throw new GenericRequestException(COMPUTER_BUILD_CANNOT_BE_DELETED);
        }
        computerBuildRepository.delete(computerBuild);
    }

    @Override
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

    @Override
    public Iterable<ComputerBuild> getAllComputerBuilds() {
        return computerBuildRepository.findAll();
    }

    @Override
    public Iterable<ComputerBuild> getAllComputerBuildsFromUser(String userName) {
        ApplicationUser user = applicationUserRepository.findByUsername(userName);
        return computerBuildRepository.getAllByUser(user);
    }

    /**
     * This method will attempt to find a computer build by an automatically generated identifier and when
     * the computerbuild is null then that means it does not exist inside the database and the generated identifier
     * is unique and can be returned.
     * @return A build identifier that has not yet been assigned.
     */
    private String generateUniqueBuildIdentifier() {
        ComputerBuild computerBuild = new ComputerBuild();
        String buildIdentifier = "";
        while(computerBuild != null) {
            buildIdentifier = RandomStringUtils.randomAlphanumeric(IDENTIFIER_LENGTH).toLowerCase();
            computerBuild = computerBuildRepository.getComputerBuildByBuildIdentifier(buildIdentifier);
        }
        return buildIdentifier;
    }

    private ComputerBuild verifyOwnerOfComputerBuild(String buildIdentifier) {
        ApplicationUser user = SecurityUtil.getLoggedInUser();

        ComputerBuild oldBuild = getComputerBuildByBuildIdentifier(buildIdentifier);

        if(oldBuild.getUser().getUsername().equals(user.getUsername())) {
            return oldBuild;
        }
        return null;
    }

}
