package com.faculdade.oo.exceptions;

public class UsuarioNaoAutenticadoException extends AutenticacaoException {
    
    public UsuarioNaoAutenticadoException() {
        super("USER_NOT_AUTHENTICATED", "Usuário não está autenticado");
    }
    
    public UsuarioNaoAutenticadoException(String message) {
        super("USER_NOT_AUTHENTICATED", message);
    }
}