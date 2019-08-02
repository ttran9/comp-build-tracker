package tran.compbuildbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tran.compbuildbackend.exceptions.computerbuild.*;
import tran.compbuildbackend.exceptions.request.*;
import tran.compbuildbackend.exceptions.security.*;

import java.util.Map;

import static tran.compbuildbackend.constants.fields.FieldConstants.EMAIL_FIELD;
import static tran.compbuildbackend.constants.fields.FieldConstants.USER_NAME_FIELD;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handleDuplicateUsername(UsernameDuplicateException ex) {
        UsernameDuplicateExceptionResponse duplicateUserNameException = new UsernameDuplicateExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(duplicateUserNameException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidLogin(BadCredentialsException ex) {
        InvalidLoginExceptionResponse invalidLoginExceptionResponse = new InvalidLoginExceptionResponse();
        return new ResponseEntity<>(invalidLoginExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDisabledAccountLogin(DisabledException ex) {
        InvalidLoginExceptionResponse invalidLoginExceptionResponse = new InvalidLoginExceptionResponse();
        return new ResponseEntity<>(invalidLoginExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameCreationException(UsernameCreationErrorException ex) {
        UsernameCreationErrorExceptionResponse usernameCreationErrorExceptionResponse = new UsernameCreationErrorExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(usernameCreationErrorExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailVerificationTokenException(EmailVerificationTokenException ex) {
        EmailVerificationTokenExceptionResponse response = new EmailVerificationTokenExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRequestPasswordChangeException(RequestChangePasswordException ex) {
        RequestChangePasswordExceptionResponse response = new RequestChangePasswordExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleChangePasswordTokenException(ChangePasswordTokenException ex) {
        ChangePasswordTokenExceptionResponse response = new ChangePasswordTokenExceptionResponse();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleGenericRequestException(GenericRequestException ex) {
        GenericRequestExceptionResponse response = new GenericRequestExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailRequestException(EmailRequestException ex) {
        EmailRequestExceptionResponse response = new EmailRequestExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleMultipleFieldsException(MultipleFieldsException ex) {
        Map<String, String> errors = ex.getMapError();

        MultipleFieldsExceptionResponse response = new MultipleFieldsExceptionResponse();

        if(errors.get(USER_NAME_FIELD) != null) {
            response.setUsername(errors.get(USER_NAME_FIELD));
        }

        if(errors.get(EMAIL_FIELD) != null) {
            response.setEmail(errors.get(EMAIL_FIELD));
        }

        /*
         * TODO: add in other fields that could be incorrect on form submission from a variety of pages.
         * some pages: registration (fullname, username, email, password, confirm password), login (username, password),
         * create computer build (computer build name, computer build description), etc.
         */

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNoteException(NoteException ex) {
        NoteExceptionResponse response = new NoteExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleComputerDetailIdentifierException(ComputerDetailIdentifierException ex) {
        ComputerDetailIdentifierExceptionResponse response = new ComputerDetailIdentifierExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleComputerBuildOwnerException(ComputerBuildOwnerException ex) {
        ComputerBuildOwnerExceptionResponse response = new ComputerBuildOwnerExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
