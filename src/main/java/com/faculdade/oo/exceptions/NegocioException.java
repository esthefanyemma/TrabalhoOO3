/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class NegocioException extends SistemaException {
    
    public NegocioException(String message) {
        super("BUSINESS_ERROR", message);
    }
    
    public NegocioException(String message, Throwable cause) {
        super("BUSINESS_ERROR", message, cause);
    }
    
    public NegocioException(String codigoErro, String message) {
        super(codigoErro, message);
    }
}


