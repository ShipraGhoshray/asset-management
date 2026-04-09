package com.selflearning.exception;

public class UnknownAssetTypeException extends RuntimeException {
    public UnknownAssetTypeException(String typeCode) {
        super("Unknown asset type: " + typeCode);
    }
}
