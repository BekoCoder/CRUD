package uz.pdp.website.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.website.exception.AlreadyExistsException;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> exceptionHandler(AlreadyExistsException e) {
       return new  ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
