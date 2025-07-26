// InvalidCredentialsException.java
package com.itss.auth.exception;

public class InvalidCredentialsException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}