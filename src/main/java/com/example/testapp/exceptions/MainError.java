package com.example.testapp.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MainError {

    UNKNOWN_ERROR(1L, HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_ERROR(2L, HttpStatus.NOT_FOUND),
    ACCESS_ERROR(3L, HttpStatus.FORBIDDEN);

    private final Long internalCode;
    private final HttpStatus status;


}
