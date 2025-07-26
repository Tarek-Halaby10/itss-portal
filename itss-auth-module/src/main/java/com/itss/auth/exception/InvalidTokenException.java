// InvalidTokenException.java
package com.itss.auth.exception;

public class InvalidTokenException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
        super("Invalid token provided");
    }
}