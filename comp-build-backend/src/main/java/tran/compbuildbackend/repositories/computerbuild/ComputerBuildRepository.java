package tran.compbuildbackend.repositories.computerbuild;

import org.springframework.data.repository.CrudRepository;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.user.ApplicationUser;

public interface ComputerBuildRepository extends CrudRepository<ComputerBuild, Long> {

    ComputerBuild getComputerBuildByBuildIdentifier(String buildIdentifier);
    Iterable<ComputerBuild> getAllByUser(ApplicationUser user);
}
