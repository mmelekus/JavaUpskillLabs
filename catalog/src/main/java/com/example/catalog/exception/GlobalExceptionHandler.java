package com.example.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

// @RestControllerAdvice = @ControllerAdvice + @ResponseBody.
// Every method in this class can return a plain object and Spring will
// serialize it to JSON, just like @RestController does for normal endpoints.
// This class applies to all controllers in the application by default.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles ResourceNotFoundException -- returns 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        // TODO 13: Build an ApiError with:
        //   status = "404 NOT_FOUND"
        //   errorCode = ex.getResourceType().toUpperCase() + "_NOT_FOUND"
        //   message = ex.getMessage()
        //   fieldErrors = null (no per-field errors for a not-found)
        // Return ResponseEntity with status HttpStatus.NOT_FOUND and the ApiError as the body.
        ApiError error = new ApiError(
                "404 NOT_FOUND",
                ex.getResourceType().toUpperCase() + "_NOT_FOUND",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Handles BusinessRuleException -- returns 422
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(BusinessRuleException ex) {
        // TODO 14: Build an ApiError with:
        //   status = "422 UNPROCESSABLE_ENTITY"
        //   errorCode = ex.getErrorCode()
        //   message = ex.getMessage()
        //   fieldErrors = null
        // Return ResponseEntity with status HttpStatus.UNPROCESSABLE_ENTITY.
        ApiError error = new ApiError(
                "422 UNPROCESSABLE_ENTITY",
                ex.getErrorCode(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    // Handles validation failures from @Valid -- returns 400
    // MethodArgumentNotValidException is thrown by Spring MVC when @Valid fails.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ApiError.FieldError(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiError error = new ApiError(
                "400 BAD_REQUEST",
                "VALIDATION_FAILURE",
                "One or more fields failed validation",
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Catch-all for any unhandled exception -- returns 500
    // Security note: the response body intentionally contains no stack trace,
    // no exception class name, and no internal message.
    // Those details are available in the server logs, not in the HTTP response.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        // TODO 15: Build an ApiError with:
        //   status = "500 INTERNAL_SERVER_ERROR"
        //   errorCode = "INTERNAL_ERROR"
        //   message = "An unexpected error occurred. Please contact support."
        //   fieldErrors = null
        // Log the real exception (use System.err.println for now -- a real app uses SLF4J).
        // Return ResponseEntity with status HttpStatus.INTERNAL_SERVER_ERROR.
        System.err.println("Unhandled exception: " + ex.getMessage());
        ApiError error = new ApiError(
                "500 INTERNAL_SERVER_ERROR",
                "INTERNAL_ERROR",
                "An unexpected error occurred. Please contact support.",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}