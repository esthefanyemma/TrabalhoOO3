/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class FaixaInvalidaException extends ValidacaoException {
    
    public FaixaInvalidaException(String campo, Object valor, Object min, Object max) {
        super("INVALID_RANGE", String.format("Campo '%s' com valor '%s' deve estar entre %s e %s", campo, valor, min, max));
    }
    
    public FaixaInvalidaException(String message) {
        super("INVALID_RANGE", message);
    }
}



