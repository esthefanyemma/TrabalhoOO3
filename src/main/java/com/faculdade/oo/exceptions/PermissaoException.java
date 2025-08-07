/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class PermissaoException extends NegocioException {
    
    public PermissaoException(String message) {
        super("PERMISSION_DENIED", message);
    }
    
    public PermissaoException(String operacao, String tipoUsuario) {
        super("PERMISSION_DENIED", String.format("Operação '%s' não permitida para usuário do tipo '%s'", operacao, tipoUsuario));
    }
}



