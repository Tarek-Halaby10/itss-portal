// BusinessLogicException.java
package com.itss.auth.exception;

public class BusinessLogicException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessLogicException(String message) {
        super("BUSINESS_001", message);
    }
}