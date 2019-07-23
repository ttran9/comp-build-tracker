package tran.compbuildbackend.domain.utility;

import com.google.gson.Gson;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;

public class ComputerBuildUtility {
    public static String getComputerBuildAsJson(String computerBuildName) {
        ComputerBuild computerBuild = new ComputerBuild();

        computerBuild.setName(computerBuildName);

        return new Gson().toJson(computerBuild);
    }
}
