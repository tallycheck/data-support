package com.taoswork.tallycheck.authority.core;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class IllegalCodePathException extends RuntimeException {
    public IllegalCodePathException() {
    }

    public IllegalCodePathException(String message) {
        super(message);
    }

    public IllegalCodePathException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCodePathException(Throwable cause) {
        super(cause);
    }
}
