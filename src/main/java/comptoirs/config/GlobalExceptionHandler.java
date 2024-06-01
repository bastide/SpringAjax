package comptoirs.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * The GlobalExceptionHandler class handles exceptions thrown by the application and provides custom response bodies
 * with detailed error messages.
 *
 * This class is annotated with @ControllerAdvice, which allows it to handle exceptions globally for all controllers.
 * It contains two exception handler methods:
 * - handleConstraintViolationException: Handles ConstraintViolationException and constructs a custom response body
 *   with a validation error message.
 * - handleDataIntegrityViolationException: Handles DataIntegrityViolationException and constructs a custom response body
 *   with a data integrity violation message.
 *
 * Both methods return a ResponseEntity object containing the custom response body and the appropriate HTTP status code.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        // Collect the error messages of all violations
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        // Construct a custom response body or use a predefined one
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation error: " + errorMessage, errorMessage);

        // Return a ResponseEntity containing the custom response body and HTTP status
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles a DataIntegrityViolationException and constructs a custom response
     * body with a detailed message.
     * 
     * @param ex The DataIntegrityViolationException that was thrown
     * @return A ResponseEntity containing the custom response body and HTTP status
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Attempt to get more specific cause of the exception
        Throwable rootCause = ex.getMostSpecificCause();
        String specificMessage = rootCause != null ? rootCause.getMessage() : ex.getMessage();

        // Construct a custom response body with the detailed message
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT,
                "Data integrity violation " + specificMessage,
                "The operation could not be completed due to a data integrity violation: " + specificMessage);

        // Return a ResponseEntity containing the custom response body and HTTP status
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

}