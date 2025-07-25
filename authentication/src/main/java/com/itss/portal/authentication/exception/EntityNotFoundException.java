package com.itss.portal.authentication.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity) {
        super(entity + " not found");
    }
}
