/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class ArquivoException extends DaoException {
    
    public ArquivoException(String message) {
        super("FILE_ERROR", message);
    }
    
    public ArquivoException(String message, Throwable cause) {
        super("FILE_ERROR", message, cause);
    }
}


