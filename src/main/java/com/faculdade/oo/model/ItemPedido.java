/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

public class ItemPedido {
    private int produtoId;
    private String nomeProduto;
    private double precoUnitario;
    private int quantidade;
    private double subtotal;
    
    public ItemPedido() {}
    
    public ItemPedido(int produtoId, String nomeProduto, double precoUnitario, int quantidade) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.subtotal = precoUnitario * quantidade;
    }
    
    // Getters e Setters
    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }
    
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    
    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { 
        this.precoUnitario = precoUnitario;
        calcularSubtotal();
    }
    
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade;
        calcularSubtotal();
    }
    
    public double getSubtotal() { return subtotal; }
    
    private void calcularSubtotal() {
        this.subtotal = precoUnitario * quantidade;
    }
    
    @Override
    public String toString() {
        return String.format("%s - Qtd: %d - Preço Unit: R$ %.2f - Subtotal: R$ %.2f", 
                           nomeProduto, quantidade, precoUnitario, subtotal);
    }
}



