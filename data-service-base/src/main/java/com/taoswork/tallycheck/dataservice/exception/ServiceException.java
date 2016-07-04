package com.taoswork.tallycheck.dataservice.exception;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class ServiceException extends Exception implements Serializable {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public static ServiceException treatAsServiceException(Throwable e) {
        if (e instanceof ServiceException) {
            return (ServiceException) e;
        } else {
            Throwable cause = e.getCause();
            if (cause instanceof ServiceException) {
                return (ServiceException) cause;
            } else {
                return new ServiceException(e);
            }
        }
    }
}
