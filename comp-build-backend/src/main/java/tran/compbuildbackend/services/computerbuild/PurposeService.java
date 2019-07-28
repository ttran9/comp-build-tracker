package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.Purpose;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;

public interface PurposeService {
    /**
     * Adds a purpose object to the database.
     * @param computerBuild The computer build object to be associated with the AbstractNote.
     * @param purpose The purpose to be persisted.
     * @return Returns the persisted purpose object linked/associated with the ComputerBuild.
     */
    Purpose create(ComputerBuild computerBuild, Purpose purpose);
}
