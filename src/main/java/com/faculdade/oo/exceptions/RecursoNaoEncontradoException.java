/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class RecursoNaoEncontradoException extends DaoException {
    
    public RecursoNaoEncontradoException(String recurso, Object id) {
        super("RESOURCE_NOT_FOUND", String.format("%s com ID '%s' não encontrado", recurso, id));
    }
    
    public RecursoNaoEncontradoException(String message) {
        super("RESOURCE_NOT_FOUND", message);
    }
}


