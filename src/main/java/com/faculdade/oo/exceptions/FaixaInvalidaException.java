package com.faculdade.oo.exceptions;

public class FaixaInvalidaException extends ValidacaoException {
    
    public FaixaInvalidaException(String campo, Object valor, Object min, Object max) {
        super("INVALID_RANGE", String.format("Campo '%s' com valor '%s' deve estar entre %s e %s", campo, valor, min, max));
    }
    
    public FaixaInvalidaException(String message) {
        super("INVALID_RANGE", message);
    }
}