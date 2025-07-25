// TokenRefreshException.java
package com.itss.portal.authentication.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String token, String msg) {
        super("Failed for token [" + token + "]: " + msg);
    }
}
