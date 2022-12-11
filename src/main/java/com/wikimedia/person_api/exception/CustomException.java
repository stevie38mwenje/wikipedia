package com.wikimedia.person_api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    protected String message;
    protected HttpStatus status;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
