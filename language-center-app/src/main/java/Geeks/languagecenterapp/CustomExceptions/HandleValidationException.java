package Geeks.languagecenterapp.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleValidationException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument (MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>() ;
        ex.getBindingResult().getFieldErrors().forEach((error)->{
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return errors ;
    }

}