
// PasswordMismatchException.java
package com.itss.auth.exception;

public class PasswordMismatchException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordMismatchException() {
        super("Current password is incorrect");
    }
}