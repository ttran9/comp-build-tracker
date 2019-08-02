package tran.compbuildbackend.exceptions.computerbuild;

public class NoteExceptionResponse {

    private String message;

    public NoteExceptionResponse() { }

    public NoteExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
