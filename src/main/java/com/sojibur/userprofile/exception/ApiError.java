package com.sojibur.userprofile.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApiError {
    private HttpStatus status;
    private List<Error> errors;
}
