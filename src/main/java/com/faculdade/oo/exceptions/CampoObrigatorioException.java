/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.exceptions;

public class CampoObrigatorioException extends ValidacaoException {
    
    public CampoObrigatorioException(String campo) {
        super("REQUIRED_FIELD", String.format("O campo '%s' é obrigatório", campo));
    }
}



