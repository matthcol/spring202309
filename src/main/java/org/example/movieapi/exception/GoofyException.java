package org.example.movieapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.I_AM_A_TEAPOT, reason = "Goofy mistake")
public class GoofyException extends RuntimeException {
    public GoofyException() {
    }

    public GoofyException(String message) {
        super(message);
    }

    public GoofyException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoofyException(Throwable cause) {
        super(cause);
    }

    public GoofyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
