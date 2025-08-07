package com.faculdade.oo.exceptions;

public class CampoObrigatorioException extends ValidacaoException {
    
    public CampoObrigatorioException(String campo) {
        super("REQUIRED_FIELD", String.format("O campo '%s' é obrigatório", campo));
    }
}