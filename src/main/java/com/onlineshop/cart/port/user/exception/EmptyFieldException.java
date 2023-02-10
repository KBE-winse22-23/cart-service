package com.onlineshop.cart.port.user.exception;

/**
 * @Author Roman Behroz
 * Exeception will be thrown if any required fields are left blank  e.g. name
 */
public class EmptyFieldException extends Exception{
    public EmptyFieldException() {
        super();
    }

    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFieldException(Throwable cause) {
        super(cause);
    }

    protected EmptyFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
