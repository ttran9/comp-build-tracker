package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.ComputerPart;

public interface ComputerPartService {

    /**
     * Adds a computer part object to the database.
     * @param computerBuild The computer build object to be associated with the ComputerPart.
     * @param computerPart The computer part to be saved.
     * @return Returns the persisted computer part object linked/associated with the ComputerBuild.
     */
    ComputerPart createComputerPart(ComputerBuild computerBuild, ComputerPart computerPart);

}
