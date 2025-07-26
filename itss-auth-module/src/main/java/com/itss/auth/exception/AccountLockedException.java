// AccountLockedException.java
package com.itss.auth.exception;

public class AccountLockedException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountLockedException() {
        super("Account is locked. Please contact administrator.");
    }
}