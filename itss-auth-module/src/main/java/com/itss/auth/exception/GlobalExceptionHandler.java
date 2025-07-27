package com.itss.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "VALIDATION_ERROR");
        error.put("message", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CustomExceptions.UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(CustomExceptions.UserNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "USER_NOT_FOUND");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomExceptions.EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(CustomExceptions.EmailAlreadyExistsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "EMAIL_ALREADY_EXISTS");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CustomExceptions.UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameAlreadyExistsException(CustomExceptions.UsernameAlreadyExistsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "USERNAME_ALREADY_EXISTS");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CustomExceptions.InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(CustomExceptions.InvalidTokenException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "INVALID_TOKEN");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(CustomExceptions.TokenExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleTokenExpiredException(CustomExceptions.TokenExpiredException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "TOKEN_EXPIRED");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(CustomExceptions.PermissionDeniedException.class)
    public ResponseEntity<Map<String, Object>> handlePermissionDeniedException(CustomExceptions.PermissionDeniedException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "PERMISSION_DENIED");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(CustomExceptions.RoleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoleNotFoundException(CustomExceptions.RoleNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "ROLE_NOT_FOUND");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "INTERNAL_ERROR");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
} 
