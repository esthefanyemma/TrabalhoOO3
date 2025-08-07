/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public class Vendedor extends Usuario {
    private int franquiaId;
    private double totalVendas;
    private int quantidadeVendas;
    
    public Vendedor() {
        super();
        this.tipo = TipoUsuario.VENDEDOR;
        this.totalVendas = 0.0;
        this.quantidadeVendas = 0;
    }
    
    public Vendedor(int id, String nome, String cpf, String email, String senha, int franquiaId) {
        super(id, nome, cpf, email, senha, TipoUsuario.VENDEDOR);
        this.franquiaId = franquiaId;
        this.totalVendas = 0.0;
        this.quantidadeVendas = 0;
    }
    
    public int getFranquiaId() {
        return franquiaId;
    }
    
    public void setFranquiaId(int franquiaId) {
        this.franquiaId = franquiaId;
    }
    
    public double getTotalVendas() {
        return totalVendas;
    }
    
    public void setTotalVendas(double totalVendas) {
        this.totalVendas = totalVendas;
    }
    
    public int getQuantidadeVendas() {
        return quantidadeVendas;
    }
    
    public void setQuantidadeVendas(int quantidadeVendas) {
        this.quantidadeVendas = quantidadeVendas;
    }
    
    public void adicionarVenda(double valor) {
        this.totalVendas += valor;
        this.quantidadeVendas++;
    }
    
    public double getTicketMedio() {
        return quantidadeVendas > 0 ? totalVendas / quantidadeVendas : 0.0;
    }
    
    @Override
    public String toString() {
        return String.format("Vendedor [ID: %d, Nome: %s, Email: %s, Franquia ID: %d, Total Vendas: R$ %.2f]", 
                           id, nome, email, franquiaId, totalVendas);
    }
}



