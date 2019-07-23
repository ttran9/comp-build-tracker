package tran.compbuildbackend.exceptions.request;

public class EmailRequestExceptionResponse {

    private String message;

    public EmailRequestExceptionResponse() {
    }

    public EmailRequestExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

