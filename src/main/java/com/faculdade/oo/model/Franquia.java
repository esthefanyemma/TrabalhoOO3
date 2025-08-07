/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

import java.util.ArrayList;
import java.util.List;

public class Franquia {
    private int id;
    private String nome;
    private Endereco endereco;
    private int gerenteId;
    private List<Integer> vendedoresIds;
    private double receitaAcumulada;
    private int totalPedidos;
    
    public Franquia() {
        this.vendedoresIds = new ArrayList<>();
        this.receitaAcumulada = 0.0;
        this.totalPedidos = 0;
    }
    
    public Franquia(int id, String nome, Endereco endereco, int gerenteId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.gerenteId = gerenteId;
        this.vendedoresIds = new ArrayList<>();
        this.receitaAcumulada = 0.0;
        this.totalPedidos = 0;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    
    public int getGerenteId() { return gerenteId; }
    public void setGerenteId(int gerenteId) { this.gerenteId = gerenteId; }
    
    public List<Integer> getVendedoresIds() { return vendedoresIds; }
    public void setVendedoresIds(List<Integer> vendedoresIds) { this.vendedoresIds = vendedoresIds; }
    
    public double getReceitaAcumulada() { return receitaAcumulada; }
    public void setReceitaAcumulada(double receitaAcumulada) { this.receitaAcumulada = receitaAcumulada; }
    
    public int getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(int totalPedidos) { this.totalPedidos = totalPedidos; }
    
    public void adicionarVendedor(int vendedorId) {
        if (!vendedoresIds.contains(vendedorId)) {
            vendedoresIds.add(vendedorId);
        }
    }
    
    public void removerVendedor(int vendedorId) {
        vendedoresIds.remove(Integer.valueOf(vendedorId));
    }
    
    public void adicionarReceita(double valor) {
        this.receitaAcumulada += valor;
        this.totalPedidos++;
    }
    
    public double getTicketMedio() {
        return totalPedidos > 0 ? receitaAcumulada / totalPedidos : 0.0;
    }
    
    @Override
    public String toString() {
        return String.format("Franquia [ID: %d, Nome: %s, Endereço: %s, Receita: R$ %.2f, Pedidos: %d]", 
                           id, nome, endereco.toString(), receitaAcumulada, totalPedidos);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Franquia franquia = (Franquia) obj;
        return id == franquia.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}



