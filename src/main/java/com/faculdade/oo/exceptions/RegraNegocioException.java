package com.faculdade.oo.exceptions;

public class RegraNegocioException extends NegocioException {
    
    public RegraNegocioException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }
}