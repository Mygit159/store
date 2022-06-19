package com.cy.store.service.exception;

/**
 * 用户不存在异常
 */
public class UserNotFindException extends ServiceException{
    public UserNotFindException() {
        super();
    }

    public UserNotFindException(String message) {
        super(message);
    }

    public UserNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFindException(Throwable cause) {
        super(cause);
    }

    protected UserNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
