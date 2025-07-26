// RoleNotFoundException.java
package com.itss.auth.exception;

public class RoleNotFoundException extends ResourceNotFoundException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String roleIdentifier) {
        super("Role", roleIdentifier);
    }
}
