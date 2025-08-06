package com.faculdade.oo.exceptions;

public class ArquivoException extends DaoException {
    
    public ArquivoException(String message) {
        super("FILE_ERROR", message);
    }
    
    public ArquivoException(String message, Throwable cause) {
        super("FILE_ERROR", message, cause);
    }
}