/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class TamanhoInvalidoException extends ValidacaoException {
    
    public TamanhoInvalidoException(String campo, int tamanhoAtual, int tamanhoMin, int tamanhoMax) {
        super("INVALID_LENGTH", String.format("Campo '%s' tem %d caracteres, deve ter entre %d e %d caracteres", 
              campo, tamanhoAtual, tamanhoMin, tamanhoMax));
    }
    
    public TamanhoInvalidoException(String message) {
        super("INVALID_LENGTH", message);
    }
}



