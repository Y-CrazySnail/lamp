package com.lamp.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LampException extends RuntimeException {
    public LampException() {
        super();
    }

    public LampException(String message) {
        super(message);
        log.error(message);
    }

    public LampException(String message, Throwable cause) {
        super(message, cause);
    }

    public LampException(Throwable cause) {
        super(cause);
    }

    protected LampException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
