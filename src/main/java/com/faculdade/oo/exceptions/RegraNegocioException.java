/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class RegraNegocioException extends NegocioException {
    
    public RegraNegocioException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }
}



