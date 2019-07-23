package tran.compbuildbackend.payload.computerbuild;

public class ComputerBuildResponse {

    private String name;
    private String message;
    private String buildIdentifier;

    public ComputerBuildResponse() { }

    public ComputerBuildResponse(String name) {
        this.name = name;
    }

    public ComputerBuildResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBuildIdentifier() {
        return buildIdentifier;
    }

    public void setBuildIdentifier(String buildIdentifier) {
        this.buildIdentifier = buildIdentifier;
    }

    @Override
    public String toString() {
        return "ComputerBuildResponse{" +
                "name='" + name + '\'' +
                '}';
    }
}
