// ResourceAlreadyExistsException.java
package com.itss.auth.exception;

public class ResourceAlreadyExistsException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String resource, String field, String value) {
        super("RESOURCE_002", String.format("%s with %s '%s' already exists", resource, field, value));
    }
}
