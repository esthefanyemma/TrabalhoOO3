package com.faculdade.oo.exceptions;

public class PermissaoException extends NegocioException {
    
    public PermissaoException(String message) {
        super("PERMISSION_DENIED", message);
    }
    
    public PermissaoException(String operacao, String tipoUsuario) {
        super("PERMISSION_DENIED", String.format("Operação '%s' não permitida para usuário do tipo '%s'", operacao, tipoUsuario));
    }
}