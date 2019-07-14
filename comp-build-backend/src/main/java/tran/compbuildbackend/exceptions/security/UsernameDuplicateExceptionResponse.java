package tran.compbuildbackend.exceptions.security;

public class UsernameDuplicateExceptionResponse {

    private String username;

    public UsernameDuplicateExceptionResponse() { }

    public UsernameDuplicateExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
