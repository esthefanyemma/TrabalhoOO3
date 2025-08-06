package com.faculdade.oo.exceptions;

public class ContaBloqueadaException extends AutenticacaoException {
    
    public ContaBloqueadaException() {
        super("ACCOUNT_BLOCKED", "Conta de usuário está bloqueada ou inativa");
    }
    
    public ContaBloqueadaException(String message) {
        super("ACCOUNT_BLOCKED", message);
    }
}