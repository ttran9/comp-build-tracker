package tran.compbuildbackend.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.security.EmailVerificationTokenException;
import tran.compbuildbackend.services.users.ApplicationUserService;

import static tran.compbuildbackend.constants.users.UserConstants.*;

@Component
@Profile({"test"})
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private ApplicationUserService applicationUserService;

    @Autowired
    public BootstrapData(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUsers();
    }

    private void createUsers() {
        // create a user that is enabled
        ApplicationUser user = new ApplicationUser();
        user.setUsername(USER_NAME_ONE);
        user.setPassword(USER_PASSWORD);
        user.setFullName(FULL_NAME_ONE);

        // create a user with an account that is not enabled.
        ApplicationUser userTwo = new ApplicationUser();
        userTwo.setUsername(USER_NAME_TWO);
        userTwo.setPassword(USER_PASSWORD);
        userTwo.setFullName(FULL_NAME_TWO);

        applicationUserService.persistUser(user, null);
        applicationUserService.enableUser(user);

        applicationUserService.persistUser(userTwo, null);
    }

}
