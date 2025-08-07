/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public class Gerente extends Usuario {
    private int franquiaId;
    
    public Gerente() {
        super();
        this.tipo = TipoUsuario.GERENTE;
    }
    
    public Gerente(int id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, TipoUsuario.GERENTE);
    }
    
    public Gerente(int id, String nome, String cpf, String email, String senha, int franquiaId) {
        super(id, nome, cpf, email, senha, TipoUsuario.GERENTE);
        this.franquiaId = franquiaId;
    }
    
    public int getFranquiaId() {
        return franquiaId;
    }
    
    public void setFranquiaId(int franquiaId) {
        this.franquiaId = franquiaId;
    }
    
    @Override
    public String toString() {
        return String.format("Gerente [ID: %d, Nome: %s, Email: %s, Franquia ID: %d]", 
                           id, nome, email, franquiaId);
    }
}



