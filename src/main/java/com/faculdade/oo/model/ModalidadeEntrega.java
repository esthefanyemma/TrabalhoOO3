/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public enum ModalidadeEntrega {
    RETIRADA_LOJA("Retirada na Loja"),
    ENTREGA_DOMICILIO("Entrega a Domicílio"),
    DRIVE_THRU("Drive Thru");
    
    private final String descricao;
    
    ModalidadeEntrega(String descricao) {
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



