package com.example.catalog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

// @JsonInclude(NON_NULL) tells Jackson not to serialize fields that are null.
// The "fieldErrors" field is only relevant for validation failures;
// it should be absent from the JSON for other error types.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final String status;       // e.g. "404 NOT_FOUND"
    private final String errorCode;    // machine-readable code, e.g. "PRODUCT_NOT_FOUND"
    private final String message;      // human-readable description
    private final Instant timestamp;
    private final List<FieldError> fieldErrors;  // non-null only for 400 validation failures

    // TODO 12: Write the constructor that accepts all five fields.
    // Mark the constructor public.
    // Assign all parameters to the final fields.
    // Use Instant.now() for timestamp when you want the current time.
    // (The full constructor is provided below -- write it yourself first)
    public ApiError(String status, String errorCode, String message,
                    List<FieldError> fieldErrors) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now();
        this.fieldErrors = fieldErrors;
    }

    // Getters -- required for Jackson serialization
    public String getStatus() { return status; }
    public String getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
    public List<FieldError> getFieldErrors() { return fieldErrors; }

    // Nested record for per-field validation errors
    public record FieldError(String field, String message) {}
}