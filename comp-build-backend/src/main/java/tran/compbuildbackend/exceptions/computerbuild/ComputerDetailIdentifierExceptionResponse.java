package tran.compbuildbackend.exceptions.computerbuild;

public class ComputerDetailIdentifierExceptionResponse {

    private String message;

    public ComputerDetailIdentifierExceptionResponse() {
    }

    public ComputerDetailIdentifierExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
