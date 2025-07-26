// PermissionNotFoundException.java
package com.itss.auth.exception;

public class PermissionNotFoundException extends ResourceNotFoundException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermissionNotFoundException(String permissionIdentifier) {
        super("Permission", permissionIdentifier);
    }
}
