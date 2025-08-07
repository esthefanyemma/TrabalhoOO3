/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public enum TipoEndereco {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    ENTREGA("Entrega"),
    COBRANCA("Cobrança");
    
    private final String descricao;
    
    TipoEndereco(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}




