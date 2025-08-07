/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class AutenticacaoException extends SistemaException {
    
    public AutenticacaoException(String message) {
        super("AUTH_ERROR", message);
    }
    
    public AutenticacaoException(String message, Throwable cause) {
        super("AUTH_ERROR", message, cause);
    }
    
    public AutenticacaoException(String codigoErro, String message) {
        super(codigoErro, message);
    }
}


