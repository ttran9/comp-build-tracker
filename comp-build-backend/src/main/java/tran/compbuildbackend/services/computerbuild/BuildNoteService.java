package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.BuildNote;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;

public interface BuildNoteService {
    /**
     * Adds a build note object to the database.
     * @param computerBuild The computer build object to be associated with the AbstractNote.
     * @param buildNote The build note to be persisted.
     * @return Returns the persisted build note object linked/associated with the ComputerBuild.
     */
    BuildNote create(ComputerBuild computerBuild, BuildNote buildNote);
}
