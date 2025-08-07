/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.dao;

import com.faculdade.oo.model.*;
import com.faculdade.oo.exceptions.ValidacaoException;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PedidoDAO implements GenericDAO<Pedido> {
    
    private static final String ARQUIVO_PEDIDOS = "data/pedidos.txt";
    private static final String ARQUIVO_ITENS = "data/itens_pedido.txt";
    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void salvar(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new ValidacaoException("Pedido não pode ser nulo");
        }
        
        if (existe(pedido.getId())) {
            throw new ValidacaoException("Pedido com ID " + pedido.getId() + " já existe");
        }
        
        // Salva o pedido
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(ARQUIVO_PEDIDOS), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND)) {
            
            writer.write(pedidoParaLinha(pedido));
            writer.newLine();
        }
        
        salvarItens(pedido);
    }
    
    @Override
    public Pedido buscarPorId(int id) throws Exception {
        List<Pedido> pedidos = listarTodos();
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Pedido> listarTodos() throws Exception {
        List<Pedido> pedidos = new ArrayList<>();
        
        if (!Files.exists(Paths.get(ARQUIVO_PEDIDOS))) {
            return pedidos;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_PEDIDOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    Pedido pedido = linhaPraPedido(linha);
                    pedido.setItens(carregarItensPedido(pedido.getId()));
                    pedidos.add(pedido);
                }
            }
        }
        
        return pedidos;
    }
    
    @Override
    public void atualizar(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new ValidacaoException("Pedido não pode ser nulo");
        }
        
        if (!existe(pedido.getId())) {
            throw new ValidacaoException("Pedido com ID " + pedido.getId() + " não encontrado");
        }
        
        List<Pedido> pedidos = listarTodos();
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_PEDIDOS))) {
            for (Pedido p : pedidos) {
                if (p.getId() == pedido.getId()) {
                    writer.write(pedidoParaLinha(pedido));
                } else {
                    writer.write(pedidoParaLinha(p));
                }
                writer.newLine();
            }
        }
        
        removerItens(pedido.getId());
        salvarItens(pedido);
    }
    
    @Override
    public void remover(int id) throws Exception {
        if (!existe(id)) {
            throw new ValidacaoException("Pedido com ID " + id + " não encontrado");
        }
        
        List<Pedido> pedidos = listarTodos();
        pedidos.removeIf(p -> p.getId() == id);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_PEDIDOS))) {
            for (Pedido pedido : pedidos) {
                writer.write(pedidoParaLinha(pedido));
                writer.newLine();
            }
        }
        
        removerItens(id);
    }
    
    @Override
    public boolean existe(int id) throws Exception {
        return buscarPorId(id) != null;
    }
    
    @Override
    public int obterProximoId() throws Exception {
        List<Pedido> pedidos = listarTodos();
        return pedidos.stream()
                .mapToInt(Pedido::getId)
                .max()
                .orElse(0) + 1;
    }
    
    public List<Pedido> buscarPorVendedor(int vendedorId) throws Exception {
        return listarTodos().stream()
                .filter(p -> p.getVendedorId() == vendedorId)
                .toList();
    }

    public List<Pedido> buscarPorFranquia(int franquiaId) throws Exception {
        return listarTodos().stream()
                .filter(p -> p.getFranquiaId() == franquiaId)
                .toList();
    }

    public List<Pedido> buscarPorStatus(StatusPedido status) throws Exception {
        return listarTodos().stream()
                .filter(p -> p.getStatus() == status)
                .toList();
    }
    
    private void salvarItens(Pedido pedido) throws Exception {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            return;
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(ARQUIVO_ITENS), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND)) {
            
            for (ItemPedido item : pedido.getItens()) {
                writer.write(itemParaLinha(item, pedido.getId()));
                writer.newLine();
            }
        }
    }
    
    private List<ItemPedido> carregarItensPedido(int pedidoId) throws Exception {
        List<ItemPedido> itens = new ArrayList<>();
        
        if (!Files.exists(Paths.get(ARQUIVO_ITENS))) {
            return itens;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_ITENS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] campos = linha.split(SEPARADOR);
                    if (campos.length >= 5 && Integer.parseInt(campos[0]) == pedidoId) {
                        itens.add(linhaParaItem(linha));
                    }
                }
            }
        }
        
        return itens;
    }
    
    private void removerItens(int pedidoId) throws Exception {
        if (!Files.exists(Paths.get(ARQUIVO_ITENS))) {
            return;
        }
        
        List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_ITENS));
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_ITENS))) {
            for (String linha : linhas) {
                if (!linha.trim().isEmpty()) {
                    String[] campos = linha.split(SEPARADOR);
                    if (campos.length >= 5 && Integer.parseInt(campos[0]) != pedidoId) {
                        writer.write(linha);
                        writer.newLine();
                    }
                }
            }
        }
    }
    
    private String pedidoParaLinha(Pedido pedido) {
        return String.join(SEPARADOR,
                String.valueOf(pedido.getId()),
                pedido.getDataPedido().format(FORMATTER),
                String.valueOf(pedido.getVendedorId()),
                String.valueOf(pedido.getFranquiaId()),
                pedido.getNomeCliente(),
                pedido.getTelefoneCliente(),
                pedido.getEmailCliente(),
                enderecoParaString(pedido.getEnderecoEntrega()),
                pedido.getFormaPagamento().name(),
                pedido.getModoEntrega().name(),
                pedido.getStatus().name(),
                String.valueOf(pedido.getValorTotal()),
                pedido.getObservacoes() != null ? pedido.getObservacoes() : ""
        );
    }
    
    private Pedido linhaPraPedido(String linha) throws ValidacaoException {
        String[] campos = linha.split(SEPARADOR);
        
        if (campos.length != 13) {
            throw new ValidacaoException("Formato inválido na linha do pedido: " + linha);
        }
        
        try {
            Pedido pedido = new Pedido();
            pedido.setId(Integer.parseInt(campos[0]));
            pedido.setDataPedido(LocalDateTime.parse(campos[1], FORMATTER));
            pedido.setVendedorId(Integer.parseInt(campos[2]));
            pedido.setFranquiaId(Integer.parseInt(campos[3]));
            pedido.setNomeCliente(campos[4]);
            pedido.setTelefoneCliente(campos[5]);
            pedido.setEmailCliente(campos[6]);
            pedido.setEnderecoEntrega(stringParaEndereco(campos[7]));
            pedido.setFormaPagamento(FormaPagamento.valueOf(campos[8]));
            pedido.setModoEntrega(ModoEntrega.valueOf(campos[9]));
            pedido.setStatus(StatusPedido.valueOf(campos[10]));
            pedido.setValorTotal(Double.parseDouble(campos[11]));
            pedido.setObservacoes(campos[12].isEmpty() ? null : campos[12]);
            
            return pedido;
        } catch (Exception e) {
            throw new ValidacaoException("Erro ao converter dados do pedido: " + e.getMessage());
        }
    }
    
    private String itemParaLinha(ItemPedido item, int pedidoId) {
        return String.join(SEPARADOR,
                String.valueOf(pedidoId),
                String.valueOf(item.getProdutoId()),
                item.getNomeProduto(),
                String.valueOf(item.getQuantidade()),
                String.valueOf(item.getPrecoUnitario())
        );
    }
    
    private ItemPedido linhaParaItem(String linha) throws ValidacaoException {
        String[] campos = linha.split(SEPARADOR);
        
        if (campos.length != 5) {
            throw new ValidacaoException("Formato inválido na linha do item: " + linha);
        }
        
        try {
            ItemPedido item = new ItemPedido();
            item.setProdutoId(Integer.parseInt(campos[1]));
            item.setNomeProduto(campos[2]);
            item.setQuantidade(Integer.parseInt(campos[3]));
            item.setPrecoUnitario(Double.parseDouble(campos[4]));
            
            return item;
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Erro ao converter dados do item: " + e.getMessage());
        }
    }
    
    private String enderecoParaString(Endereco endereco) {
        if (endereco == null) return "";
        return endereco.getRua() + "," + endereco.getNumero() + "," + 
               (endereco.getComplemento() != null ? endereco.getComplemento() : "") + "," +
               (endereco.getBairro() != null ? endereco.getBairro() : "") + "," +
               endereco.getCidade() + "," + endereco.getEstado() + "," + endereco.getCep();
    }
    
    private Endereco stringParaEndereco(String enderecoStr) {
        if (enderecoStr == null || enderecoStr.trim().isEmpty()) return null;
        
        String[] partes = enderecoStr.split(",");
        if (partes.length != 7) return null;
        
        Endereco endereco = new Endereco();
        endereco.setRua(partes[0].trim());
        endereco.setNumero(partes[1].trim());
        endereco.setComplemento(partes[2].trim().isEmpty() ? null : partes[2].trim());
        endereco.setBairro(partes[3].trim().isEmpty() ? null : partes[3].trim());
        endereco.setCidade(partes[4].trim());
        endereco.setEstado(partes[5].trim());
        endereco.setCep(partes[6].trim());
        endereco.setTipo(TipoEndereco.ENTREGA);
        
        return endereco;
    }
}



