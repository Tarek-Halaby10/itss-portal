// AuthenticationException.java
package com.itss.auth.exception;

public class AuthenticationException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
        super("AUTH_001", message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super("AUTH_001", message, cause);
    }
}