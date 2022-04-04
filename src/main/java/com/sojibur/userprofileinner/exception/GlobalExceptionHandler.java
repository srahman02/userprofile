package com.sojibur.userprofileinner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<ApiError> handleInternalServerException(InternalServerException ex){
        return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex){
        return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> buildResponseEntity(Exception ex, HttpStatus status){
        ApiError apiError = new ApiError();
        Error error = new Error(ex.getMessage());
        List<Error> errorList = new ArrayList<>();
        errorList.add(error);
        apiError.setStatus(status);
        apiError.setErrors(errorList);
        return new ResponseEntity<>(apiError, status);
    }
}
