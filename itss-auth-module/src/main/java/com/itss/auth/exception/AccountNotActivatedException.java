// AccountNotActivatedException.java
package com.itss.auth.exception;

public class AccountNotActivatedException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotActivatedException() {
        super("Account is not activated. Please contact administrator.");
    }
}