package com.faculdade.oo.model;


public class Dono extends Usuario {
    
    public Dono() {
        super();
        this.tipo = TipoUsuario.DONO;
    }
    
    public Dono(int id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, TipoUsuario.DONO);
    }
    
    @Override
    public String toString() {
        return String.format("Dono [ID: %d, Nome: %s, Email: %s]", id, nome, email);
    }
}
