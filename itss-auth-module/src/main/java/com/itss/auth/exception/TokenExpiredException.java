// TokenExpiredException.java
package com.itss.auth.exception;

public class TokenExpiredException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpiredException() {
        super("Token has expired");
    }

    public TokenExpiredException(String tokenType) {
        super(tokenType + " token has expired");
    }
}


