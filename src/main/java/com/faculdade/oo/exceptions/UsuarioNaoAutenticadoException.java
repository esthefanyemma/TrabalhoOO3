/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class UsuarioNaoAutenticadoException extends AutenticacaoException {
    
    public UsuarioNaoAutenticadoException() {
        super("USER_NOT_AUTHENTICATED", "Usuário não está autenticado");
    }
    
    public UsuarioNaoAutenticadoException(String message) {
        super("USER_NOT_AUTHENTICATED", message);
    }
}



