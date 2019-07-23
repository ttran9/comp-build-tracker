package tran.compbuildbackend.services.computerbuild;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import tran.compbuildbackend.controllers.utility.WebUtility;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.payload.email.LoginRequest;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.repositories.users.ApplicationUserRepository;
import tran.compbuildbackend.security.JwtTokenProvider;
import tran.compbuildbackend.services.security.ApplicationUserAuthenticationService;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static tran.compbuildbackend.constants.tests.TestUtility.SAMPLE_COMPUTER_BUILD_NAME;
import static tran.compbuildbackend.constants.tests.TestUtility.SAMPLE_GAMING_COMPUTER_BUILD_NAME;
import static tran.compbuildbackend.constants.users.UserConstants.*;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComputerBuildServiceImplTest {

    @Autowired
    private ComputerBuildRepository computerBuildRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private ComputerBuildService computerBuildService;

    @Autowired
    private ApplicationUserAuthenticationService applicationUserAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Before
    public void setUp() {
        computerBuildService = new ComputerBuildServiceImpl(computerBuildRepository,
                applicationUserRepository);
    }

    /*
     * This test checks if the user is logged in and attempts to create a computer build.
     */
    @Test
    public void createNewComputerBuildSuccess() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(0, newComputerBuild.getNumberOfDirections());
    }

    /*
     * This test will check if the user tries to create a new computer build but has not logged in doesn't have a valid
     * JWT token.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void createNewComputerBuildFailure() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_COMPUTER_BUILD_NAME);
        computerBuildService.createNewComputerBuild(computerBuild);
    }

    /*
     * This test will check if a newly created computer build will have its number of directions field updated and the
     * user and build identifier fields should be unchanged.
     */
    @Transactional
    @Test
    public void createUpdateNumberOfDirections() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        newComputerBuild.setNumberOfDirections(1);

        ComputerBuild updatedBuild = computerBuildService.updateNumberOfDirections(newComputerBuild, newComputerBuild.getBuildIdentifier());

        assertNotEquals(expectedNumberOfDirections, updatedBuild.getNumberOfDirections());
        assertEquals(newComputerBuild.getBuildIdentifier(), updatedBuild.getBuildIdentifier());
        assertEquals(newComputerBuild.getUser(), updatedBuild.getUser());
    }

    /*
     * This test checks the case where the user is logged in but is passing in a computerBuild object that is "invalid,"
     * in this case there is no user associated with the computerBuild.
     */
    @Test(expected = GenericRequestException.class)
    public void createUpdateNumberOfDirectionsFailure() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        computerBuild.setNumberOfDirections(1);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        computerBuildService.updateNumberOfDirections(computerBuild, computerBuild.getBuildIdentifier());
    }

    /*
     * This test will check if a newly created computer build will have its number of directions field updated when the
     * user updating is not the original user that created the computer build.
     */
    @Transactional
    @Test(expected = GenericRequestException.class)
    public void createUpdateNumberOfDirectionsAsNonOwner() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        newComputerBuild.setNumberOfDirections(1);

        // log in as a different user, this is expected to cause the update to fail.
        LoginRequest newLoginRequest = new LoginRequest(USER_NAME_ONE, USER_PASSWORD);
        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, newLoginRequest);

        computerBuildService.updateNumberOfDirections(newComputerBuild, newComputerBuild.getBuildIdentifier());
    }

    /*
     * This test will check if a computer build that was properly created can be deleted when passing in the correct
     * build identifier and the owner is logged in.
     */
    @Test
    @Transactional
    public void deleteComputerBuild() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        /*
         * as long as there is no exception thrown the test can be considered to pass. in the controller test, a certain
         * http status code can be checked for.
         */
        computerBuildService.deleteComputerBuild(newComputerBuild.getBuildIdentifier());
    }

    /*
     * This test will check if a computer build that was properly created can be deleted when passing in the correct
     * build identifier but not as the owner.
     */
    @Transactional
    @Test(expected = GenericRequestException.class)
    public void deleteComputerBuildAsNonOwner() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        // log in as a different user, this is expected to cause the delete to fail.
        LoginRequest newLoginRequest = new LoginRequest(USER_NAME_ONE, USER_PASSWORD);
        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, newLoginRequest);

        computerBuildService.deleteComputerBuild(newComputerBuild.getBuildIdentifier());
    }

    /*
     * This test will check if a computer build that was properly created can be deleted when passing in an incorrect
     * build identifier but as the owner.
     */
    @Transactional
    @Test(expected = GenericRequestException.class)
    public void deleteComputerBuildWithIncorrectIdentifier() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_GAMING_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        computerBuildService.deleteComputerBuild(newComputerBuild.getBuildIdentifier() + "1");
    }

    /*
     * This test checks if a computer build that was created can be found by its build identifier and that the fields match.
     */
    @Test
    public void getComputerBuildByIdentifier() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        ComputerBuild foundBuild = computerBuildService.getComputerBuildByBuildIdentifier(newComputerBuild.getBuildIdentifier());

        assertNotNull(foundBuild);
        assertEquals(newComputerBuild.getBuildIdentifier(), foundBuild.getBuildIdentifier());
        assertEquals(newComputerBuild.getUser().getUsername(), foundBuild.getUser().getUsername());
        assertEquals(newComputerBuild.getNumberOfDirections(), foundBuild.getNumberOfDirections());

    }

    /*
     * This test checks if a computer build that was created can be found by an invalid build identifier.
     */
    @Test(expected = GenericRequestException.class)
    public void getComputerBuildByInvalidIdentifier() {
        ComputerBuild computerBuild = createComputerBuild(SAMPLE_COMPUTER_BUILD_NAME);
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        int expectedNumberOfDirections = 0;

        WebUtility.logUserIn(applicationUserAuthenticationService, authenticationManager, jwtTokenProvider, loginRequest);

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        assertNotNull(newComputerBuild);
        assertNotNull(newComputerBuild.getBuildIdentifier());
        assertNotNull(newComputerBuild.getUser());
        assertEquals(expectedNumberOfDirections, newComputerBuild.getNumberOfDirections());

        // attempt to find the computer build created above with an invalid identifier.
        computerBuildService.getComputerBuildByBuildIdentifier(newComputerBuild.getBuildIdentifier() + "1");
    }

    /*
     * This test checks if we can get all the computer builds and check that its size isn't zero since the database
     * has at least one computer build (from the bootstrap class).
     */
    @Test
    public void getAllComputerBuilds() {
        Iterable<ComputerBuild> computerBuilds = computerBuildService.getAllComputerBuilds();
        assertNotNull(computerBuilds);
        assertNotEquals(0, computerBuilds.spliterator().getExactSizeIfKnown());
    }

    /*
     * This test checks if we can get all the computer builds from a user that has created at least one computer build
     * and check that its size isn't zero.
     */
    @Test
    public void getAllComputerBuildsFromUser() {
        Iterable<ComputerBuild> computerBuilds = computerBuildService.getAllComputerBuildsFromUser(ANOTHER_USER_NAME_TO_CREATE_NEW_USER);
        assertNotNull(computerBuilds);
        assertNotEquals(0, computerBuilds.spliterator().getExactSizeIfKnown());
    }

    /*
     * This test checks if we can get all the computer builds from a user that has not created any computer builds
     * and check that its size is zero.
     */
    @Test
    public void getAllComputerBuildsFromUserWithNoBuilds() {
        Iterable<ComputerBuild> computerBuilds = computerBuildService.getAllComputerBuildsFromUser(SUCCESSFUL_USER_NAME);
        assertNotNull(computerBuilds);
        assertEquals(0, computerBuilds.spliterator().getExactSizeIfKnown());
    }

    private ComputerBuild createComputerBuild(String computerBuildName) {
        ComputerBuild computerBuild = new ComputerBuild();
        computerBuild.setName(computerBuildName);
        return computerBuild;
    }


}
