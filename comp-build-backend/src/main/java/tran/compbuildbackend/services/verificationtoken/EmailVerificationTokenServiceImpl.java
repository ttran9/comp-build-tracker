package tran.compbuildbackend.services.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.security.EmailVerificationToken;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.UsernameRequestException;
import tran.compbuildbackend.exceptions.security.EmailVerificationTokenException;
import tran.compbuildbackend.repositories.security.EmailVerificationTokenRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import java.util.Calendar;
import java.util.UUID;

import static tran.compbuildbackend.constants.security.SecurityConstants.CHANGE_PASSWORD_TOKEN_TYPE;
import static tran.compbuildbackend.constants.security.SecurityConstants.EMAIL_VERIFICATION_TOKEN_TYPE;

@Service
public class EmailVerificationTokenServiceImpl implements VerificationTokenService {

    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    public EmailVerificationTokenServiceImpl(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Override
    public String createVerificationToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        EmailVerificationToken newUserToken = new EmailVerificationToken(token, user);
        return VerificationTokenUtil.createToken(user, token, emailVerificationTokenRepository, newUserToken);
    }

    @Override
    public EmailVerificationToken getVerificationToken(String verificationToken) {
        return emailVerificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public ApplicationUser validateVerificationToken(String token) {
        EmailVerificationToken emailVerificationToken = getVerificationToken(token);
        if(emailVerificationToken == null) {
            throw new EmailVerificationTokenException("token is not present.");
        }
        SecurityUtil.isTokenExpired(emailVerificationToken, EMAIL_VERIFICATION_TOKEN_TYPE);
        return emailVerificationToken.getUser();
    }
}
