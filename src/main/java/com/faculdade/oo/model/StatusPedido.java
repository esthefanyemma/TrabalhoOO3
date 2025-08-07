package com.faculdade.oo.model;

public enum StatusPedido {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    EM_PREPARO("Em Preparo"),
    PRONTO("Pronto"),
    SAIU_PARA_ENTREGA("Saiu para Entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    
    StatusPedido(String descricao) {
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

