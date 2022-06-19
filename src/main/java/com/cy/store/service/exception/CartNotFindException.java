package com.cy.store.service.exception;

/**
 * 物品数据未找到异常
 */
public class CartNotFindException extends ServiceException{
    public CartNotFindException() {
        super();
    }

    public CartNotFindException(String message) {
        super(message);
    }

    public CartNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotFindException(Throwable cause) {
        super(cause);
    }

    protected CartNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
