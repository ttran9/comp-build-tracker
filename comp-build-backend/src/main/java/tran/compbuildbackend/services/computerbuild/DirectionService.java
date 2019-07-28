package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.domain.computerbuild.Direction;

public interface DirectionService {
    /**
     * Adds a direction object to the database.
     * @param computerBuild The computer build object to be associated with the Direction.
     * @param direction The computer part to be saved.
     * @return Returns the persisted direction object linked/associated with the ComputerBuild.
     */
    Direction createDirection(ComputerBuild computerBuild, Direction direction);
}
