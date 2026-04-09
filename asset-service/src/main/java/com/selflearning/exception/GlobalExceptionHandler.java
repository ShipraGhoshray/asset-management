package com.selflearning.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(UnknownAssetTypeException.class)
    public ResponseEntity<String> handleUnknownAssetType(UnknownAssetTypeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}