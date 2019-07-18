package tran.compbuildbackend.services.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tran.compbuildbackend.domain.security.ChangePasswordToken;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.request.UsernameRequestException;
import tran.compbuildbackend.exceptions.security.ChangePasswordTokenException;
import tran.compbuildbackend.repositories.security.ChangePasswordTokenRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import java.util.Calendar;
import java.util.UUID;

import static tran.compbuildbackend.constants.security.SecurityConstants.CHANGE_PASSWORD_TOKEN_TYPE;

@Service
public class ChangePasswordTokenServiceImpl implements VerificationTokenService {

    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    public ChangePasswordTokenServiceImpl(ChangePasswordTokenRepository changePasswordTokenRepository) {
        this.changePasswordTokenRepository = changePasswordTokenRepository;
    }

    @Override
    public String createVerificationToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        ChangePasswordToken changePasswordToken = new ChangePasswordToken(token, user);
        return VerificationTokenUtil.createToken(user, token, changePasswordTokenRepository, changePasswordToken);
    }

    @Override
    public ChangePasswordToken getVerificationToken(String verificationToken) {
        return changePasswordTokenRepository.findByToken(verificationToken);
    }

    @Override
    public ApplicationUser validateVerificationToken(String token) {
        ChangePasswordToken changePasswordToken = getVerificationToken(token);
        if(changePasswordToken == null) {
            throw new ChangePasswordTokenException("token is not present.");
        }
        SecurityUtil.isTokenExpired(changePasswordToken, CHANGE_PASSWORD_TOKEN_TYPE);
        return changePasswordToken.getUser();
    }
}
