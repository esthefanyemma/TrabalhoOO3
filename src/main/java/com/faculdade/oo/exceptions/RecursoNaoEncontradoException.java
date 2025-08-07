package com.faculdade.oo.exceptions;

public class RecursoNaoEncontradoException extends DaoException {
    
    public RecursoNaoEncontradoException(String recurso, Object id) {
        super("RESOURCE_NOT_FOUND", String.format("%s com ID '%s' não encontrado", recurso, id));
    }
    
    public RecursoNaoEncontradoException(String message) {
        super("RESOURCE_NOT_FOUND", message);
    }
}