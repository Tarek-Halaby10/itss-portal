// UsernameAlreadyExistsException.java
package com.itss.auth.exception;

public class UsernameAlreadyExistsException extends ResourceAlreadyExistsException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException(String username) {
        super("User", "username", username);
    }
}