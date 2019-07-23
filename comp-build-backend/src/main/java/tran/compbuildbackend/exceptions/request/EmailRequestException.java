package tran.compbuildbackend.exceptions.request;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailRequestException extends RuntimeException {
    public EmailRequestException(String message) {
        super(message);
    }
}
