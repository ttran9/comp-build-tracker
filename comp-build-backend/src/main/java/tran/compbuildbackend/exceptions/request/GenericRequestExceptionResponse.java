package tran.compbuildbackend.exceptions.request;

public class GenericRequestExceptionResponse {
    private String message;

    public GenericRequestExceptionResponse() { }

    public GenericRequestExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
