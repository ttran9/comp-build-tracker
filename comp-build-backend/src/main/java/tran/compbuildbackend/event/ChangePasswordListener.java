package tran.compbuildbackend.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tran.compbuildbackend.event.utility.EventUtil;
import tran.compbuildbackend.exceptions.security.ChangePasswordTokenException;
import tran.compbuildbackend.services.verificationtoken.ChangePasswordTokenServiceImpl;

import static tran.compbuildbackend.constants.email.EmailConstants.PASSWORD_CHANGE_REQUEST;
import static tran.compbuildbackend.constants.mapping.MappingConstants.CHANGE_PASSWORD_URL;

@Component
public class ChangePasswordListener implements ApplicationListener<OnPasswordResetRequestEvent> {
    @Autowired
    private ChangePasswordTokenServiceImpl changePasswordTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void onApplicationEvent(OnPasswordResetRequestEvent event) {
        this.changePasswordRequest(event);
    }

    private void changePasswordRequest(OnPasswordResetRequestEvent event) {
        try {
            String subject = "Change Password";
            EventUtil.sendEmail(event, changePasswordTokenService, messageSource, PASSWORD_CHANGE_REQUEST, mailSender,
                    subject, CHANGE_PASSWORD_URL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ChangePasswordTokenException("Password cannot be changed at this time!");
        }
    }
}
