package com.itss.auth.exception;

import com.itss.auth.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            validationErrors.put(error.getField(), error.getDefaultMessage())
        );
        
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .error("Validation failed")
                .errorCode("VALIDATION_ERROR")
                .data(validationErrors)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
                
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CustomExceptions.UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(
            CustomExceptions.UserNotFoundException ex, WebRequest request) {
        ApiResponse<Void> response = ApiResponse.error(
                ex.getMessage(), 
                "USER_NOT_FOUND",
                request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
