package comptoirs.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

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
}