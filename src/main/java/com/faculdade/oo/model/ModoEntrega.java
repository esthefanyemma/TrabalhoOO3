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

