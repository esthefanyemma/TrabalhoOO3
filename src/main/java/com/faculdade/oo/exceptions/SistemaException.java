/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public abstract class SistemaException extends Exception {
    
    protected String codigoErro;
    
    public SistemaException(String message) {
        super(message);
    }
    
    public SistemaException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SistemaException(String codigoErro, String message) {
        super(message);
        this.codigoErro = codigoErro;
    }
    
    public SistemaException(String codigoErro, String message, Throwable cause) {
        super(message, cause);
        this.codigoErro = codigoErro;
    }
    
    public String getCodigoErro() {
        return codigoErro;
    }
}



