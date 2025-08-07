/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class DaoException extends SistemaException {
    
    public DaoException(String message) {
        super("DAO_ERROR", message);
    }
    
    public DaoException(String message, Throwable cause) {
        super("DAO_ERROR", message, cause);
    }
    
    public DaoException(String codigoErro, String message) {
        super(codigoErro, message);
    }
    
    public DaoException(String codigoErro, String message, Throwable cause) {
        super(codigoErro, message, cause);
    }
}



