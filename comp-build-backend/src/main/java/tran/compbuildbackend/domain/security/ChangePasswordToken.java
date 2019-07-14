package tran.compbuildbackend.domain.security;

import tran.compbuildbackend.domain.user.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "change_password_token")
public class ChangePasswordToken extends VerificationToken {

    public ChangePasswordToken() { super(); }

    public ChangePasswordToken(String token, ApplicationUser user) {
        super(token, user);
        this.expirationDate = super.calculateExpirationDate(this.EXPIRATION);
    }


}
