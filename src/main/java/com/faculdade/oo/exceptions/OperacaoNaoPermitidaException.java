package com.faculdade.oo.exceptions;

public class OperacaoNaoPermitidaException extends NegocioException {
    
    public OperacaoNaoPermitidaException(String message) {
        super("OPERATION_NOT_ALLOWED", message);
    }
}