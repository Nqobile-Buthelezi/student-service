package za.co.pass.studentservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// Centralise exception handling logic to ensure exceptions caught do not display implementation details
@ControllerAdvice // Allows the program to use error handling in bespoke ways
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Will handle validation exceptions from JPA when it handles validation of request DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Now we need to get all errors from our exception
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        // When we make an invalid request we will return this data wrapped in the response entity,
        // which to us will appear to be JSON. This will hide key implementation details of the back end logic
        // from the front end, so we will ensure we display only what the users need to correct their requests.
        return ResponseEntity.badRequest().body(errors);
    }

    // After creating our unique exception we must make use of it here in our global exception handler
    // When an email is found to be a duplicate inside our request DTO this error handling logic will trigger
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        // Add a custom log in this to allow us to debug better
        log.warn("Email address already exists {}", ex.getMessage());
        // There are two steps in this error handler one is to inform the user and the
        // other is to inform/log the error to the system itself.
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email address already exists.");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudentNotFoundException(StudentNotFoundException ex) {
        log.warn("Student not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Student not been found in the database.");
        return ResponseEntity.status(404).body(errors);
    }

}
