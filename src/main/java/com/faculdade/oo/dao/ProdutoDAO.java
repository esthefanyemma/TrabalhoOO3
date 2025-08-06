package com.faculdade.oo.dao;

import com.faculdade.oo.model.Produto;
import com.faculdade.oo.exceptions.ValidacaoException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProdutoDAO implements GenericDAO<Produto> {
    
    private static final String ARQUIVO_PRODUTOS = "produtos.txt";
    private static final String SEPARADOR = ";";
    
    @Override
    public void salvar(Produto produto) throws Exception {
        if (produto == null) {
            throw new ValidacaoException("Produto não pode ser nulo");
        }
        
        if (existe(produto.getId())) {
            throw new ValidacaoException("Produto com ID " + produto.getId() + " já existe");
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(ARQUIVO_PRODUTOS), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND)) {
            
            writer.write(produtoParaLinha(produto));
            writer.newLine();
        }
    }
    
    @Override
    public Produto buscarPorId(int id) throws Exception {
        List<Produto> produtos = listarTodos();
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Produto> listarTodos() throws Exception {
        List<Produto> produtos = new ArrayList<>();
        
        if (!Files.exists(Paths.get(ARQUIVO_PRODUTOS))) {
            return produtos;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_PRODUTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    produtos.add(linhaPraProduto(linha));
                }
            }
        }
        
        return produtos;
    }
    
    @Override
    public void atualizar(Produto produto) throws Exception {
        if (produto == null) {
            throw new ValidacaoException("Produto não pode ser nulo");
        }
        
        if (!existe(produto.getId())) {
            throw new ValidacaoException("Produto com ID " + produto.getId() + " não encontrado");
        }
        
        List<Produto> produtos = listarTodos();
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_PRODUTOS))) {
            for (Produto p : produtos) {
                if (p.getId() == produto.getId()) {
                    writer.write(produtoParaLinha(produto));
                } else {
                    writer.write(produtoParaLinha(p));
                }
                writer.newLine();
            }
        }
    }
    
    @Override
    public void remover(int id) throws Exception {
        if (!existe(id)) {
            throw new ValidacaoException("Produto com ID " + id + " não encontrado");
        }
        
        List<Produto> produtos = listarTodos();
        produtos.removeIf(p -> p.getId() == id);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_PRODUTOS))) {
            for (Produto produto : produtos) {
                writer.write(produtoParaLinha(produto));
                writer.newLine();
            }
        }
    }
    
    @Override
    public boolean existe(int id) throws Exception {
        return buscarPorId(id) != null;
    }
    
    @Override
    public int obterProximoId() throws Exception {
        List<Produto> produtos = listarTodos();
        return produtos.stream()
                .mapToInt(Produto::getId)
                .max()
                .orElse(0) + 1;
    }
    
    public List<Produto> buscarPorFranquia(int franquiaId) throws Exception {
        return listarTodos().stream()
                .filter(p -> p.getFranquiaId() == franquiaId)
                .toList();
    }
    
    public List<Produto> buscarComEstoqueBaixo(int franquiaId) throws Exception {
        return buscarPorFranquia(franquiaId).stream()
                .filter(p -> p.getQuantidadeEstoque() <= p.getEstoqueMinimo())
                .toList();
    }
    
    private String produtoParaLinha(Produto produto) {
        return String.join(SEPARADOR,
                String.valueOf(produto.getId()),
                produto.getNome(),
                produto.getDescricao(),
                String.valueOf(produto.getPreco()),
                String.valueOf(produto.getQuantidadeEstoque()),
                String.valueOf(produto.getEstoqueMinimo()),
                String.valueOf(produto.getFranquiaId())
        );
    }
    
    private Produto linhaPraProduto(String linha) throws ValidacaoException {
        String[] campos = linha.split(SEPARADOR);
        
        if (campos.length != 7) {
            throw new ValidacaoException("Formato inválido na linha do produto: " + linha);
        }
        
        try {
            int id = Integer.parseInt(campos[0]);
            String nome = campos[1];
            String descricao = campos[2];
            double preco = Double.parseDouble(campos[3]);
            int quantidadeEstoque = Integer.parseInt(campos[4]);
            int estoqueMinimo = Integer.parseInt(campos[5]);
            int franquiaId = Integer.parseInt(campos[6]);
            
            Produto produto = new Produto(id, nome, descricao, preco, quantidadeEstoque, franquiaId);
            produto.setEstoqueMinimo(estoqueMinimo);
            return produto;
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Erro ao converter dados do produto: " + e.getMessage());
        }
    }
}