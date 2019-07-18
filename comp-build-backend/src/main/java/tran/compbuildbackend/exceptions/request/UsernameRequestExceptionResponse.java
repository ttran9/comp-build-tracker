package tran.compbuildbackend.exceptions.request;

public class UsernameRequestExceptionResponse {
    private String message;

    public UsernameRequestExceptionResponse() { }

    public UsernameRequestExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
