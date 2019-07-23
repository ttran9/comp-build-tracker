package tran.compbuildbackend.exceptions.request;


public class MultipleFieldsExceptionResponse {

    private String username;

    private String email;

    public MultipleFieldsExceptionResponse() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
