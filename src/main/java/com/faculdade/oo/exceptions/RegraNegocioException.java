/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class RegraNegocioException extends NegocioException {
    
    public RegraNegocioException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }
}


