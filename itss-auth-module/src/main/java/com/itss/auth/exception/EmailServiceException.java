// EmailServiceException.java
package com.itss.auth.exception;

public class EmailServiceException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailServiceException(String message) {
        super("EMAIL_001", message);
    }

    public EmailServiceException(String message, Throwable cause) {
        super("EMAIL_001", message, cause);
    }
}
