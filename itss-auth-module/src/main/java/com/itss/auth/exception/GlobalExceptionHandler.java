// GlobalExceptionHandler.java
package com.itss.auth.exception;

import com.itss.auth.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================== Custom Application Exceptions ====================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found: {} at {}", ex.getMessage(), request.getRequestURI());
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Resource already exists: {} at {}", ex.getMessage(), request.getRequestURI());
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            ValidationException ex, HttpServletRequest request) {
        log.warn("Validation error: {} at {}", ex.getMessage(), request.getRequestURI());
        
        if (ex.getFieldErrors() != null && !ex.getFieldErrors().isEmpty()) {
            ApiResponse<Object> response = ApiResponse.validationError(
                ex.getMessage(),
                ex.getFieldErrors(),
                request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            ApiResponse<Object> response = ApiResponse.error(
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessLogicException(
            BusinessLogicException ex, HttpServletRequest request) {
        log.warn("Business logic error: {} at {}", ex.getMessage(), request.getRequestURI());
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailServiceException(
            EmailServiceException ex, HttpServletRequest request) {
        log.error("Email service error: {} at {}", ex.getMessage(), request.getRequestURI(), ex);
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getErrorCode(),
            "Email service is currently unavailable. Please try again later.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    // ==================== Spring Security Exceptions ====================

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {
        log.warn("Bad credentials at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "AUTH_001",
            "Invalid username or password",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResponse<Object>> handleLockedException(
            LockedException ex, HttpServletRequest request) {
        log.warn("Account locked at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "AUTH_002",
            "Account is locked. Please contact administrator.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Object>> handleDisabledException(
            DisabledException ex, HttpServletRequest request) {
        log.warn("Account disabled at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "AUTH_003",
            "Account is disabled. Please contact administrator.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "AUTH_004",
            "Access denied. You don't have permission to perform this action.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({AuthenticationException.class, 
                       com.itss.auth.exception.AuthenticationException.class})
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
            Exception ex, HttpServletRequest request) {
        log.warn("Authentication error at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "AUTH_001",
            "Authentication failed",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // ==================== Validation Exceptions ====================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation failed at {}: {}", request.getRequestURI(), ex.getMessage());
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Object> response = ApiResponse.validationError(
            "Validation failed",
            fieldErrors,
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> handleBindException(
            BindException ex, HttpServletRequest request) {
        log.warn("Binding error at {}: {}", request.getRequestURI(), ex.getMessage());
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Object> response = ApiResponse.validationError(
            "Validation failed",
            fieldErrors,
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("Constraint violation at {}: {}", request.getRequestURI(), ex.getMessage());
        
        Map<String, String> fieldErrors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            fieldErrors.put(fieldName, errorMessage);
        }
        
        ApiResponse<Object> response = ApiResponse.validationError(
            "Validation failed",
            fieldErrors,
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ==================== HTTP Exceptions ====================

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("Method not supported at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "HTTP_001",
            "HTTP method not supported: " + ex.getMethod(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Message not readable at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "HTTP_002",
            "Invalid request body format",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing parameter at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "HTTP_003",
            "Missing required parameter: " + ex.getParameterName(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Type mismatch at {}: {}", request.getRequestURI(), ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            "HTTP_004",
            "Invalid parameter type for: " + ex.getName(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ==================== Generic Exception Handler ====================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        
        ApiResponse<Object> response = ApiResponse.error(
            "INTERNAL_ERROR",
            "An unexpected error occurred. Please try again later.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}