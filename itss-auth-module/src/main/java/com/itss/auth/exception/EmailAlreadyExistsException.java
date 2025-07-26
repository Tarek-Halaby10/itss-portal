// EmailAlreadyExistsException.java
package com.itss.auth.exception;

public class EmailAlreadyExistsException extends ResourceAlreadyExistsException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String email) {
        super("User", "email", email);
    }
}