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