/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public enum ModoEntrega {
    RETIRADA("Retirada no Local"),
    ENTREGA_DOMICILIO("Entrega em Domicílio"),
    ENTREGA_EXPRESSA("Entrega Expressa");
    
    private final String descricao;
    
    ModoEntrega(String descricao) {
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




