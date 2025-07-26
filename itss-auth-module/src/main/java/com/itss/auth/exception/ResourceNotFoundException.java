// ResourceNotFoundException.java
package com.itss.auth.exception;

public class ResourceNotFoundException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resource, String identifier) {
        super("RESOURCE_001", String.format("%s with identifier '%s' not found", resource, identifier));
    }
}