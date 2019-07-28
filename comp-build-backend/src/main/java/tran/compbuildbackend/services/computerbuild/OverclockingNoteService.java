package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.OverclockingNote;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;

public interface OverclockingNoteService {
    /**
     * Adds a build note object to the database.
     * @param computerBuild The computer build object to be associated with the AbstractNote.
     * @param overclockingNote The overclockingnote to be persisted.
     * @return Returns the persisted overclockingnote object linked/associated with the ComputerBuild.
     */
    OverclockingNote create(ComputerBuild computerBuild, OverclockingNote overclockingNote);
}
