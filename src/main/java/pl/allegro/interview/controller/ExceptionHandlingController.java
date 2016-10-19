package pl.allegro.interview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import pl.allegro.interview.model.ExceptionCode;
import pl.allegro.interview.model.ExceptionResource;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ExceptionResource> handleHttpClientErrorException(HttpClientErrorException e) throws Throwable {

        switch (e.getStatusCode()) {

            case NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResource(ExceptionCode.EXC_001));

            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResource(ExceptionCode.EXC_000));
        }
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ExceptionResource> handleResourceAccessException(ResourceAccessException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ExceptionResource(ExceptionCode.EXC_002));
    }
}
