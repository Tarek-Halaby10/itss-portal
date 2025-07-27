package com.itss.auth.exception;

public class CustomExceptions {
    
    public static class UserNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class EmailAlreadyExistsException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }
    
    public static class UsernameAlreadyExistsException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }
    
    public static class InvalidTokenException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public InvalidTokenException(String message) {
            super(message);
        }
    }
    
    public static class TokenExpiredException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public TokenExpiredException(String message) {
            super(message);
        }
    }
    
    public static class PermissionDeniedException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public PermissionDeniedException(String message) {
            super(message);
        }
    }
    
    public static class RoleNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public RoleNotFoundException(String message) {
            super(message);
        }
    }
} 
