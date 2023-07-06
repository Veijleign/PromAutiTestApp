package com.example.testapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionBody {
    private Long internalCode;
    private HttpStatus status;
    private String errorMessage;
    private LocalDateTime timestamp;
}
