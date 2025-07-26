// BaseException.java
package com.itss.auth.exception;

public abstract class BaseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorCode;

    protected BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected BaseException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}