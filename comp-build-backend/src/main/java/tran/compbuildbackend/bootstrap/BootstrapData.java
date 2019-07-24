package tran.compbuildbackend.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.payload.email.LoginRequest;
import tran.compbuildbackend.security.JwtTokenProvider;
import tran.compbuildbackend.services.computerbuild.ComputerBuildService;
import tran.compbuildbackend.services.security.ApplicationUserAuthenticationService;
import tran.compbuildbackend.services.users.ApplicationUserService;

import static tran.compbuildbackend.constants.tests.TestUtility.SAMPLE_BUDGET_COMPUTER_BUILD_NAME;
import static tran.compbuildbackend.constants.tests.TestUtility.SAMPLE_GAMING_COMPUTER_BUILD_DESCRIPTION;
import static tran.compbuildbackend.constants.users.UserConstants.*;

@Component
@Profile({"test"})
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private ApplicationUserService applicationUserService;
    private ComputerBuildService computerBuildService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ApplicationUserAuthenticationService authenticationService;

    @Autowired
    public BootstrapData(ApplicationUserService applicationUserService, ComputerBuildService computerBuildService) {
        this.applicationUserService = applicationUserService;
        this.computerBuildService = computerBuildService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUsers();
        mockUserLogin();
        createSampleComputerBuild();
        mockUserLogout();
    }

    private void createUsers() {
        // create a user that is enabled
        ApplicationUser user = createUser(USER_NAME_ONE, USER_ONE_EMAIL, USER_PASSWORD, FULL_NAME_ONE);

        // create a user with an account that is not enabled.
        ApplicationUser secondUser = createUser(USER_NAME_TWO, USER_TWO_EMAIL, USER_PASSWORD, FULL_NAME_TWO);

        // create a user with an account that is not enabled.
        ApplicationUser thirdUser = createUser(USER_NAME_THREE, USER_THREE_EMAIL, USER_PASSWORD, FULL_NAME_THREE);

        // create another account that is used only to log in to get a JWT, it will not be used for other tests.
        ApplicationUser userForLogin = createUser(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, ANOTHER_EMAIL_TO_CREATE_NEW_USER,
                USER_PASSWORD, FULL_NAME_THREE);

        // a user account used to login as a second user when trying operations that require an original owner.
        ApplicationUser userForSecondLogin = createUser(USER_NAME_TO_TEST_OWNERSHIP_ENDPOINTS, EMAIL_TO_TEST_OWNERSHIP_ENDPOINTS,
                USER_PASSWORD, FULL_NAME);

        // save/create users
        applicationUserService.persistUser(user, null);
        applicationUserService.enableUser(user);

        applicationUserService.persistUser(secondUser, null);

        applicationUserService.persistUser(thirdUser, null);
        applicationUserService.persistUser(userForLogin, null);
        applicationUserService.enableUser(userForLogin);

        applicationUserService.persistUser(userForSecondLogin, null);
        applicationUserService.enableUser(userForSecondLogin);


    }

    private ApplicationUser createUser(String userName, String email, String password, String fullName) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        return user;
    }

    private void createSampleComputerBuild() {
        ComputerBuild computerBuild = new ComputerBuild();
        computerBuild.setName(SAMPLE_BUDGET_COMPUTER_BUILD_NAME);
        computerBuild.setBuildDescription(SAMPLE_GAMING_COMPUTER_BUILD_DESCRIPTION);

        computerBuildService.createNewComputerBuild(computerBuild);
    }

    private void mockUserLogin() {
        LoginRequest loginRequest = new LoginRequest(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD);
        authenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
    }

    private void mockUserLogout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
