// InvalidPasswordException.java
package com.itss.auth.exception;

public class InvalidPasswordException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
        super("Password does not meet security requirements");
    }
}