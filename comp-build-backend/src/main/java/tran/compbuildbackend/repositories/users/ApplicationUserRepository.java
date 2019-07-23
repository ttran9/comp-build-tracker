package tran.compbuildbackend.repositories.users;

import org.springframework.data.repository.CrudRepository;
import tran.compbuildbackend.domain.user.ApplicationUser;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    ApplicationUser findByEmail(String email);
    ApplicationUser getById(Long id);
}
