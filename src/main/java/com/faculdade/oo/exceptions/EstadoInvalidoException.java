/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class EstadoInvalidoException extends NegocioException {
    
    public EstadoInvalidoException(String message) {
        super("INVALID_STATE", message);
    }
    
    public EstadoInvalidoException(String entidade, String estadoAtual, String operacao) {
        super("INVALID_STATE", String.format("Não é possível realizar '%s' em %s com estado '%s'", operacao, entidade, estadoAtual));
    }
}


