/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class ConflitoDadosException extends DaoException {
    
    public ConflitoDadosException(String message) {
        super("DATA_CONFLICT", message);
    }
    
    public ConflitoDadosException(String field, String value) {
        super("DATA_CONFLICT", String.format("Já existe um registro com %s '%s'", field, value));
    }
}



