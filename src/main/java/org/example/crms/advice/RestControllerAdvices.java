package org.example.crms.advice;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.example.crms.exception.BadRequestException;
import org.example.crms.exception.NotFoundException;
import org.example.crms.exception.UnAuthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdvices {


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUsernameNotFoundException(UsernameNotFoundException e) {
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        System.out.println("e = " + e);
        return Map.of("error", "Invalid JSON");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {


        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return errors;
    }

        @ExceptionHandler(BadRequestException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, String> handleBadRequestException(BadRequestException e) {
            return Map.of("error", e.getMessage());
        }


        @ExceptionHandler(UnAuthorizedException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public Map<String, String> handleUnAuthorizedException(UnAuthorizedException e) {
            return Map.of("error", e.getMessage());
        }

        @ExceptionHandler(NotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Map<String, String> handleNotFoundException(NotFoundException e) {
            return Map.of("error", e.getMessage());
        }

        @ExceptionHandler(JwtException.class)
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        public Map<String, String> handleExpiredJwtException(JwtException e) {

            return Map.of("error", "Invalid token", "message", e.getMessage());
        }
}
