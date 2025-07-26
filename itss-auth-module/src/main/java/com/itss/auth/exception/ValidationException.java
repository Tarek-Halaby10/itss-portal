// ValidationException.java
package com.itss.auth.exception;

import java.util.Map;

public class ValidationException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<String, String> fieldErrors;

    public ValidationException(String message) {
        super("VALIDATION_001", message);
        this.fieldErrors = null;
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super("VALIDATION_001", message);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}