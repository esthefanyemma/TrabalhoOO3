/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class OperacaoNaoPermitidaException extends NegocioException {
    
    public OperacaoNaoPermitidaException(String message) {
        super("OPERATION_NOT_ALLOWED", message);
    }
}



