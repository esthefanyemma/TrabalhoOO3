/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private String nomeCliente;
    private String telefoneCliente;
    private String emailCliente;
    private Endereco enderecoEntrega;
    private LocalDateTime dataHora;
    private LocalDateTime dataPedido;
    private List<ItemPedido> itens;
    private FormaPagamento formaPagamento;
    private ModalidadeEntrega modalidadeEntrega;
    private ModoEntrega modoEntrega;
    private StatusPedido status;
    private double taxaEntrega;
    private double taxaServico;
    private double valorTotal;
    private int vendedorId;
    private int franquiaId;
    private boolean aprovado;
    private String observacoes;
    
    public Pedido() {
        this.itens = new ArrayList<>();
        this.dataHora = LocalDateTime.now();
        this.taxaEntrega = 0.0;
        this.taxaServico = 0.0;
        this.valorTotal = 0.0;
        this.aprovado = false;
    }
    
    public Pedido(int id, String nomeCliente, int vendedorId, int franquiaId) {
        this();
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.vendedorId = vendedorId;
        this.franquiaId = franquiaId;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { 
        this.itens = itens;
        calcularValorTotal();
    }
    
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    
    public ModalidadeEntrega getModalidadeEntrega() { return modalidadeEntrega; }
    public void setModalidadeEntrega(ModalidadeEntrega modalidadeEntrega) { 
        this.modalidadeEntrega = modalidadeEntrega;
        
        if (modalidadeEntrega == ModalidadeEntrega.ENTREGA_DOMICILIO) {
            this.taxaEntrega = 5.0; 
        } else {
            this.taxaEntrega = 0.0;
        }
        calcularValorTotal();
    }
    
    public double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(double taxaEntrega) { 
        this.taxaEntrega = taxaEntrega;
        calcularValorTotal();
    }
    
    public double getTaxaServico() { return taxaServico; }
    public void setTaxaServico(double taxaServico) { 
        this.taxaServico = taxaServico;
        calcularValorTotal();
    }
    
    public double getValorTotal() { return valorTotal; }
    
    public int getVendedorId() { return vendedorId; }
    public void setVendedorId(int vendedorId) { this.vendedorId = vendedorId; }
    
    public int getFranquiaId() { return franquiaId; }
    public void setFranquiaId(int franquiaId) { this.franquiaId = franquiaId; }
    
    public boolean isAprovado() { return aprovado; }
    public void setAprovado(boolean aprovado) { this.aprovado = aprovado; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    // Novos getters e setters
    public String getTelefoneCliente() { return telefoneCliente; }
    public void setTelefoneCliente(String telefoneCliente) { this.telefoneCliente = telefoneCliente; }
    
    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }
    
    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(Endereco enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }
    
    public LocalDateTime getDataPedido() { return dataPedido != null ? dataPedido : dataHora; }
    public void setDataPedido(LocalDateTime dataPedido) { 
        this.dataPedido = dataPedido;
        this.dataHora = dataPedido; 
    }
    
    public ModoEntrega getModoEntrega() { return modoEntrega; }
    public void setModoEntrega(ModoEntrega modoEntrega) { 
        this.modoEntrega = modoEntrega;
        
        if (modoEntrega == ModoEntrega.ENTREGA_DOMICILIO) {
            this.modalidadeEntrega = ModalidadeEntrega.ENTREGA_DOMICILIO;
            this.taxaEntrega = 5.0;
        } else if (modoEntrega == ModoEntrega.ENTREGA_EXPRESSA) {
            this.modalidadeEntrega = ModalidadeEntrega.ENTREGA_DOMICILIO;
            this.taxaEntrega = 10.0;
        } else {
            this.modalidadeEntrega = ModalidadeEntrega.RETIRADA_LOJA;
            this.taxaEntrega = 0.0;
        }
        calcularValorTotal();
    }
    
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        calcularValorTotal();
    }
    
    public void removerItem(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
            calcularValorTotal();
        }
    }
    
    public void calcularValorTotal() {
        double subtotal = itens.stream()
                              .mapToDouble(ItemPedido::getSubtotal)
                              .sum();
        this.valorTotal = subtotal + taxaEntrega + taxaServico;
    }
    
    public void calcularTotal() {
        calcularValorTotal(); 
    }
    
    public double getSubtotal() {
        return itens.stream()
                   .mapToDouble(ItemPedido::getSubtotal)
                   .sum();
    }
    
    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return String.format("Pedido [ID: %d, Cliente: %s, Data/Hora: %s, Valor Total: R$ %.2f, Aprovado: %s]", 
                           id, nomeCliente, getDataHoraFormatada(), valorTotal, 
                           aprovado ? "Sim" : "Não");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pedido pedido = (Pedido) obj;
        return id == pedido.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}



