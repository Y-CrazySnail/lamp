package com.yeem.lamp.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YeemException extends RuntimeException {
    public YeemException() {
        super();
    }

    public YeemException(String message) {
        super(message);
        log.error(message);
    }

    public YeemException(String message, Throwable cause) {
        super(message, cause);
    }

    public YeemException(Throwable cause) {
        super(cause);
    }

    protected YeemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
