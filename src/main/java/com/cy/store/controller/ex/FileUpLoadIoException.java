package com.cy.store.controller.ex;

/**
 * 上传文件内容异常
 */
public class FileUpLoadIoException extends FileUpLoadException{
    public FileUpLoadIoException() {
        super();
    }

    public FileUpLoadIoException(String message) {
        super(message);
    }

    public FileUpLoadIoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUpLoadIoException(Throwable cause) {
        super(cause);
    }

    protected FileUpLoadIoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
