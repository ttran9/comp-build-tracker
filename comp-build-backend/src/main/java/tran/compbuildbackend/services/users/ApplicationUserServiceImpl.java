package tran.compbuildbackend.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tran.compbuildbackend.domain.security.ChangePasswordToken;
import tran.compbuildbackend.domain.security.EmailVerificationToken;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.event.OnPasswordResetRequestEvent;
import tran.compbuildbackend.event.OnRegistrationSuccessEvent;
import tran.compbuildbackend.exceptions.security.*;
import tran.compbuildbackend.repositories.security.ChangePasswordTokenRepository;
import tran.compbuildbackend.repositories.security.EmailVerificationTokenRepository;
import tran.compbuildbackend.repositories.users.ApplicationUserRepository;

import javax.servlet.http.HttpServletRequest;

import static tran.compbuildbackend.constants.fields.FieldValueConstants.*;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    private ChangePasswordTokenRepository changePasswordTokenRepository;


    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                      EmailVerificationTokenRepository emailVerificationTokenRepository,
                                      ChangePasswordTokenRepository changePasswordTokenRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.changePasswordTokenRepository = changePasswordTokenRepository;
    }

    @Override
    public ApplicationUser persistUser(ApplicationUser newApplicationUser, HttpServletRequest request) {
        try {
            newApplicationUser.setPassword(bCryptPasswordEncoder.encode(newApplicationUser.getPassword()));
            // username has to be unique (custom exception if this is violated)
            newApplicationUser.setUsername(newApplicationUser.getUsername());

            // we don't persist or show the confirm password.
            newApplicationUser.setConfirmPassword("");

            newApplicationUser.setEnabled(false); // by default a user account is not yet activated.

            ApplicationUser createdUser = applicationUserRepository.save(newApplicationUser);

            // send an email
            if(request != null) sendSuccessRegistrationEmail(newApplicationUser, request);

            EmailVerificationToken token = getTokenFromUser(createdUser);

            if(token != null) createdUser.setEmailVerificationToken(token);

            return createdUser;
        } catch(UsernameDuplicateException | DataIntegrityViolationException ex) {
            throw new UsernameDuplicateException(USER_NAME_ERROR + newApplicationUser.getUsername() + ALREADY_EXISTS_ERROR);
        }
    }

    @Override
    public void sendSuccessRegistrationEmail(ApplicationUser registeredUser, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() +  ":" + request.getServerPort();
        try {
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), appUrl));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            applicationUserRepository.delete(registeredUser);
//            removeUser(registeredUser);
            throw new UsernameCreationErrorException(USER_NAME_ERROR + registeredUser.getUsername() + GENERIC_USER_NAME_CREATION_ERROR);
        }
    }

    private EmailVerificationToken getTokenFromUser(ApplicationUser user) {
        return emailVerificationTokenRepository.findByUser(user);
    }

    @Override
    public void removeUser(ApplicationUser newApplicationUser) {
        EmailVerificationToken token = emailVerificationTokenRepository.findByUser(newApplicationUser);
        newApplicationUser.setEmailVerificationToken(token);
        applicationUserRepository.delete(newApplicationUser);
    }

    @Transactional
    @Override
    public void enableRegisteredUser(ApplicationUser user) {
        enableAndUpdateUser(user);
        // user is enabled so now delete the registration token required to register the user.
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByUser(user);
        if(emailVerificationToken == null) {
            throw new EmailVerificationTokenException("token is not present.");
        }
        emailVerificationTokenRepository.deleteByToken(emailVerificationToken.getToken());
    }

    @Override
    public void enableUser(ApplicationUser user) {
        enableAndUpdateUser(user);
    }

    private void enableAndUpdateUser(ApplicationUser user) {
        user.setEnabled(true);
        applicationUserRepository.save(user);
    }

    @Transactional
    @Override
    public void changeUserPassword(String newPassword, ApplicationUser user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            applicationUserRepository.save(user);
            ChangePasswordToken token = changePasswordTokenRepository.findByUser(user);
            changePasswordTokenRepository.deleteByToken(token.getToken());
        } catch(Exception ex) {
            throw new ChangePasswordTokenException("unable to change password.");
        }


    }

    @Override
    public ApplicationUser sendPasswordChangeEmail(String userName, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() +  ":" + request.getServerPort();
        ApplicationUser user = applicationUserRepository.findByUsername(userName);
        if(user == null) {
            throw new RequestChangePasswordException(PASSWORD_CANNOT_BE_CHANGED_FOR_INVALID_USER);
        }
        try {
            checkForOldToken(user);
            eventPublisher.publishEvent(new OnPasswordResetRequestEvent(user, request.getLocale(), appUrl));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RequestChangePasswordException("Password for '" + user.getUsername() + "' cannot be changed at this time");
        }
        ChangePasswordToken token = getChangePasswordTokenFromUser(user);

        if(token != null) user.setChangePasswordToken(token);

        return user;
    }

    private ChangePasswordToken getChangePasswordTokenFromUser(ApplicationUser user) {
        return changePasswordTokenRepository.findByUser(user);
    }

    private void checkForOldToken(ApplicationUser user) {
        ChangePasswordToken token = changePasswordTokenRepository.findByUser(user);
        if(token != null) {
            /*
             * need to delete token because a new one will be generated and this will cause an issue when trying to locate
             * the token for the user.
             */
            changePasswordTokenRepository.deleteByToken(token.getToken());
        }
    }
}
