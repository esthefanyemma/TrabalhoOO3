/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class ContaBloqueadaException extends AutenticacaoException {
    
    public ContaBloqueadaException() {
        super("ACCOUNT_BLOCKED", "Conta de usuário está bloqueada ou inativa");
    }
    
    public ContaBloqueadaException(String message) {
        super("ACCOUNT_BLOCKED", message);
    }
}



