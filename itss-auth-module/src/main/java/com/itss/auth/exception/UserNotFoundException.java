// UserNotFoundException.java
package com.itss.auth.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String userIdentifier) {
        super("User", userIdentifier);
    }
}
