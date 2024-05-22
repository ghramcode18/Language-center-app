package Geeks.languagecenterapp.CustomExceptions;

import Geeks.languagecenterapp.DTO.Response.Register_Login_Response;
import jakarta.validation.ValidationException;
import lombok.Getter;

@Getter
public class CustomException extends ValidationException {

    private final int statusCode;

    private final Register_Login_Response responseBody = new Register_Login_Response();

    public CustomException(String message, int statusCode) {
        this.statusCode = statusCode;
        responseBody.setMessage(message);
    }

}