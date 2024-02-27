package com.easybytes.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String resourceField, String resourceValue) {
        super(String.format("%s with the given %s: %s was not found!", resourceName, resourceField, resourceValue));
    }
}
