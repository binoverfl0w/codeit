package org.example.codeit.app.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hexarch.ResourceNotFoundException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return Response.handleError("Resource not found!", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String msg = "Authentication failed!";
        return Response.handleError(msg, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Response.handleError("Validation failed!", errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class, hexarch.AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        return Response.handleError("Access denied!", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            JsonProcessingException.class,
            ParseException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            JsonMappingException.class
    })
    public ResponseEntity<Object> handleParsingExceptions(Exception ex, WebRequest request) {
        return Response.handleError("Invalid request body!", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({InvalidRequestBodyException.class})
//    public ResponseEntity<Object> handleInvalidBodyRequestException(InvalidRequestBodyException ex, WebRequest request) {
//        return Response.handleError("Invalid request body!", ex.getErrors(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({UniqueConstraintException.class})
//    public ResponseEntity<Object> handleUniqueContraintException(UniqueConstraintException ex, WebRequest request) {
//        return Response.handleError("Unique constraint violated!", ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleFormatException(IllegalArgumentException ex) {
        String msg = "";
        if (ex instanceof NumberFormatException)
            msg = ", expected number.";
        return Response.handleError("Illegal argument!", ex.getMessage() + msg, HttpStatus.BAD_REQUEST);
    }
}

