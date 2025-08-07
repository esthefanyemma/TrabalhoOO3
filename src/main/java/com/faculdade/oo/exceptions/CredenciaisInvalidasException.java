package com.faculdade.oo.exceptions;

public class CredenciaisInvalidasException extends AutenticacaoException {
    
    public CredenciaisInvalidasException() {
        super("INVALID_CREDENTIALS", "Email ou senha incorretos");
    }
    
    public CredenciaisInvalidasException(String message) {
        super("INVALID_CREDENTIALS", message);
    }
}