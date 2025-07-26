package com.itss.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String error;
    private String errorCode;
    private String path;
    private Map<String, String> validationErrors;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Success responses
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // Error responses
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        response.setError(message);
        return response;
    }

    public static <T> ApiResponse<T> error(String errorCode, String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        response.setError(message);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> ApiResponse<T> error(String errorCode, String message, String path) {
        ApiResponse<T> response = error(errorCode, message);
        response.setPath(path);
        return response;
    }

    public static <T> ApiResponse<T> errorWithPath(String message, String path) {
        ApiResponse<T> response = error(message);
        response.setPath(path);
        return response;
    }

    // Error response with validation errors
    public static <T> ApiResponse<T> validationError(String message, Map<String, String> validationErrors) {
        ApiResponse<T> response = error("VALIDATION_ERROR", message);
        response.setValidationErrors(validationErrors);
        return response;
    }

    public static <T> ApiResponse<T> validationError(String message, Map<String, String> validationErrors, String path) {
        ApiResponse<T> response = validationError(message, validationErrors);
        response.setPath(path);
        return response;
    }
}