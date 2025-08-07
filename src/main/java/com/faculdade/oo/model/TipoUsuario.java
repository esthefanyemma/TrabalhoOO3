package com.faculdade.oo.model;

public enum TipoUsuario {
    DONO("Dono"),
    GERENTE("Gerente"),
    VENDEDOR("Vendedor");
    
    private final String descricao;
    
    TipoUsuario(String descricao) {
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

