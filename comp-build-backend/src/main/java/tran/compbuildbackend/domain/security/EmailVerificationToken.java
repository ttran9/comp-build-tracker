package tran.compbuildbackend.domain.security;

import tran.compbuildbackend.domain.user.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email_verification_token")
public class EmailVerificationToken extends VerificationToken {

    public EmailVerificationToken() {
        super();
    }

    public EmailVerificationToken(String token, ApplicationUser user) {
        super(token, user);
        this.expirationDate = super.calculateExpirationDate(this.EXPIRATION);
    }
}
