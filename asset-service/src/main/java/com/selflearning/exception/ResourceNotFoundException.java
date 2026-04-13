package com.selflearning.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String typeCode) {
        super("Unknown asset type: " + typeCode);
    }
}
