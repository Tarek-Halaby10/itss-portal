// InvitationAlreadyUsedException.java
package com.itss.auth.exception;

public class InvitationAlreadyUsedException extends InvitationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvitationAlreadyUsedException() {
        super("This invitation has already been used");
    }
}