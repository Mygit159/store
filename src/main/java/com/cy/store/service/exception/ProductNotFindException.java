package com.cy.store.service.exception;

/**
 * 商品未找到异常
 */
public class ProductNotFindException extends ServiceException{
    public ProductNotFindException() {
        super();
    }

    public ProductNotFindException(String message) {
        super(message);
    }

    public ProductNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFindException(Throwable cause) {
        super(cause);
    }

    protected ProductNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
