package com.example.testapp.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class MainExceptionsHandler {

    @ExceptionHandler(MainException.class)
    public ResponseEntity<ExceptionBody> handleMainException(MainException exception) {
        return ResponseEntity.status(exception.getError().getStatus())
                .body(new ExceptionBody(
                        exception.getError().getInternalCode(),
                        exception.getError().getStatus(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleAnyException(Exception exception) {

        MainException mainException = new MainException(exception);
        return ResponseEntity.status(mainException
                .getError()
                .getStatus()
        ).body(
                new ExceptionBody(
                        mainException.getError().getInternalCode(),
                        mainException.getError().getStatus(),
                        mainException.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
