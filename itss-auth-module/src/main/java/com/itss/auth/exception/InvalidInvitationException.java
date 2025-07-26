// InvalidInvitationException.java
package com.itss.auth.exception;

public class InvalidInvitationException extends InvitationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInvitationException() {
        super("Invalid or expired invitation token");
    }
}