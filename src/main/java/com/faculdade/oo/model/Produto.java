package com.faculdade.oo.model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    private int franquiaId;
    private int estoqueMinimo;
    
    public Produto() {}
    
    public Produto(int id, String nome, String descricao, double preco, int quantidadeEstoque, int franquiaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.franquiaId = franquiaId;
        this.estoqueMinimo = 5; 
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    
    public int getFranquiaId() { return franquiaId; }
    public void setFranquiaId(int franquiaId) { this.franquiaId = franquiaId; }
    
    public int getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    
    // Métodos de negócio
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= estoqueMinimo;
    }
    
    public void diminuirEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        if (quantidadeEstoque < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.quantidadeEstoque -= quantidade;
    }
    
    public void aumentarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.quantidadeEstoque += quantidade;
    }
    
    public boolean temEstoqueDisponivel(int quantidade) {
        return quantidadeEstoque >= quantidade;
    }
    
    public void reduzirEstoque(int quantidade) throws IllegalArgumentException {
        if (quantidade > quantidadeEstoque) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }
        this.quantidadeEstoque -= quantidade;
    }
    
    @Override
    public String toString() {
        return String.format("Produto [ID: %d, Nome: %s, Preço: R$ %.2f, Estoque: %d%s]", 
                           id, nome, preco, quantidadeEstoque, 
                           isEstoqueBaixo() ? " (ESTOQUE BAIXO!)" : "");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id == produto.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

