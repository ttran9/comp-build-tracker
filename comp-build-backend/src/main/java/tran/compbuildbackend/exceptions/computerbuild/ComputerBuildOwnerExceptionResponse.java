package tran.compbuildbackend.exceptions.computerbuild;

public class ComputerBuildOwnerExceptionResponse {
    private String message;

    public ComputerBuildOwnerExceptionResponse() {
    }

    public ComputerBuildOwnerExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
