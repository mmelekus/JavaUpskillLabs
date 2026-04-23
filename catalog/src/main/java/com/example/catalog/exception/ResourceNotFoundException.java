package com.example.catalog.exception;

// Thrown when a requested resource does not exist.
// @ResponseStatus is one way to declare the HTTP mapping.
// You will use @ControllerAdvice in this exercise instead,
// which gives centralized control over all mappings.
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(resourceType + " not found: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public String getResourceType() { return resourceType; }
    public String getResourceId() { return resourceId; }
}