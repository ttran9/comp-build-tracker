package tran.compbuildbackend.mapper;

import tran.compbuildbackend.payload.computerbuild.ComputerBuildResponse;

public class ComputerBuildToComputerBuildResponse {

    public static ComputerBuildResponse computerBuildToComputerBuildResponseName(String computerName) {
        ComputerBuildResponse computerBuildResponse = new ComputerBuildResponse();
        computerBuildResponse.setName(computerName);

        return computerBuildResponse;
    }

    public static ComputerBuildResponse computerBuildToComputerBuildResponseNameAndBuildIdentifier(String computerName,
                                                                                                   String buildIdentifier) {
        ComputerBuildResponse computerBuildResponse = new ComputerBuildResponse();
        computerBuildResponse.setName(computerName);
        computerBuildResponse.setBuildIdentifier(buildIdentifier);

        return computerBuildResponse;
    }

    public static ComputerBuildResponse computerBuildToComputerBuildResponseMessage(String message) {
        ComputerBuildResponse computerBuildResponse = new ComputerBuildResponse();
        computerBuildResponse.setMessage(message);

        return computerBuildResponse;
    }
}
