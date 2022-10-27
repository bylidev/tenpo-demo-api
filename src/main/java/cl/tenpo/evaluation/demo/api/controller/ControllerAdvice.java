package cl.tenpo.evaluation.demo.api.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    public static final String ERROR_RESPONSE_CAUSE = "cause";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_RESPONSE_CAUSE,ex.getBindingResult().getAllErrors().stream()
                .map(fe->String.format("%s: %s",((FieldError) fe).getField(),fe.getDefaultMessage())).collect(Collectors.joining(",")));
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public Map<String, String> feignException(
            FeignException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_RESPONSE_CAUSE,ex.getMessage());
        return errors;
    }

}
