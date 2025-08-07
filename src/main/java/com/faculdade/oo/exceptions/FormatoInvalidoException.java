/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class FormatoInvalidoException extends ValidacaoException {
    
    public FormatoInvalidoException(String campo, String formato) {
        super("INVALID_FORMAT", String.format("Campo '%s' está em formato inválido. Formato esperado: %s", campo, formato));
    }
    
    public FormatoInvalidoException(String message) {
        super("INVALID_FORMAT", message);
    }
}



