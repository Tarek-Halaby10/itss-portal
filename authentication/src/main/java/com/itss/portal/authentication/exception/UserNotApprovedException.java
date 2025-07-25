// UserNotApprovedException.java
package com.itss.portal.authentication.exception;

public class UserNotApprovedException extends RuntimeException {
    public UserNotApprovedException(String msg) {
        super(msg);
    }
}
