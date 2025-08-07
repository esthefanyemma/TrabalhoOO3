/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class CredenciaisInvalidasException extends AutenticacaoException {
    
    public CredenciaisInvalidasException() {
        super("INVALID_CREDENTIALS", "Email ou senha incorretos");
    }
    
    public CredenciaisInvalidasException(String message) {
        super("INVALID_CREDENTIALS", message);
    }
}



