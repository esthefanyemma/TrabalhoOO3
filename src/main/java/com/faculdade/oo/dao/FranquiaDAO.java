/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.dao;

import com.faculdade.oo.model.Franquia;
import com.faculdade.oo.model.Endereco;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FranquiaDAO implements GenericDAO<Franquia> {
    
    private static final String ARQUIVO = "data/franquias.txt";
    private static final String SEPARADOR = ";";
    private static final String SEPARADOR_LISTA = ",";
    
    @Override
    public void salvar(Franquia franquia) throws Exception {
        List<Franquia> franquias = listarTodos();

        if (franquias.stream().anyMatch(f -> f.getId() == franquia.getId())) {
            throw new Exception("Já existe uma franquia com o ID " + franquia.getId());
        }
        
        franquias.add(franquia);
        salvarTodos(franquias);
    }
    
    @Override
    public Franquia buscarPorId(int id) throws Exception {
        return listarTodos().stream()
                           .filter(f -> f.getId() == id)
                           .findFirst()
                           .orElse(null);
    }
    
    public Franquia buscarPorGerente(int gerenteId) throws Exception {
        return listarTodos().stream()
                           .filter(f -> f.getGerenteId() == gerenteId)
                           .findFirst()
                           .orElse(null);
    }
    
    @Override
    public List<Franquia> listarTodos() throws Exception {
        List<Franquia> franquias = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        
        if (!arquivo.exists()) {
            return franquias;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Franquia franquia = parsearLinha(linha);
                if (franquia != null) {
                    franquias.add(franquia);
                }
            }
        }
        
        return franquias;
    }
    
    @Override
    public void atualizar(Franquia franquia) throws Exception {
        List<Franquia> franquias = listarTodos();
        boolean encontrado = false;
        
        for (int i = 0; i < franquias.size(); i++) {
            if (franquias.get(i).getId() == franquia.getId()) {
                franquias.set(i, franquia);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new Exception("Franquia com ID " + franquia.getId() + " não encontrada");
        }
        
        salvarTodos(franquias);
    }
    
    @Override
    public void remover(int id) throws Exception {
        List<Franquia> franquias = listarTodos();
        boolean removido = franquias.removeIf(f -> f.getId() == id);
        
        if (!removido) {
            throw new Exception("Franquia com ID " + id + " não encontrada");
        }
        
        salvarTodos(franquias);
    }
    
    @Override
    public boolean existe(int id) throws Exception {
        return buscarPorId(id) != null;
    }
    
    @Override
    public int obterProximoId() throws Exception {
        List<Franquia> franquias = listarTodos();
        return franquias.stream()
                       .mapToInt(Franquia::getId)
                       .max()
                       .orElse(0) + 1;
    }
    
    private void salvarTodos(List<Franquia> franquias) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Franquia franquia : franquias) {
                writer.println(formatarLinha(franquia));
            }
        }
    }
    
    private String formatarLinha(Franquia franquia) {
        StringBuilder sb = new StringBuilder();
        Endereco endereco = franquia.getEndereco();
        
        sb.append(franquia.getId()).append(SEPARADOR)
          .append(franquia.getNome()).append(SEPARADOR)
          .append(endereco.getRua()).append(SEPARADOR)
          .append(endereco.getNumero()).append(SEPARADOR)
          .append(endereco.getComplemento() != null ? endereco.getComplemento() : "").append(SEPARADOR)
          .append(endereco.getBairro()).append(SEPARADOR)
          .append(endereco.getCidade()).append(SEPARADOR)
          .append(endereco.getEstado()).append(SEPARADOR)
          .append(endereco.getCep()).append(SEPARADOR)
          .append(franquia.getGerenteId()).append(SEPARADOR)
          .append(franquia.getReceitaAcumulada()).append(SEPARADOR)
          .append(franquia.getTotalPedidos()).append(SEPARADOR);
        
        String vendedoresIds = franquia.getVendedoresIds().stream()
                                     .map(String::valueOf)
                                     .collect(Collectors.joining(SEPARADOR_LISTA));
        sb.append(vendedoresIds);
        
        return sb.toString();
    }
    
    private Franquia parsearLinha(String linha) {
        String[] campos = linha.split(SEPARADOR);
        
        if (campos.length < 12) {
            return null;
        }
        
        int id = Integer.parseInt(campos[0]);
        String nome = campos[1];
        
        Endereco endereco = new Endereco(
            campos[2], // rua
            campos[3], // numero
            campos[4].isEmpty() ? null : campos[4], // complemento
            campos[5], // bairro
            campos[6], // cidade
            campos[7], // estado
            campos[8]  // cep
        );
        
        int gerenteId = Integer.parseInt(campos[9]);
        double receitaAcumulada = Double.parseDouble(campos[10]);
        int totalPedidos = Integer.parseInt(campos[11]);
        
        Franquia franquia = new Franquia(id, nome, endereco, gerenteId);
        franquia.setReceitaAcumulada(receitaAcumulada);
        franquia.setTotalPedidos(totalPedidos);

        int vendedoresIndex = -1;
        for (int i = 12; i < campos.length; i++) {
            if (!campos[i].matches("\\d+\\.\\d+") && !campos[i].isEmpty()) {
                vendedoresIndex = i;
                break;
            }
        }
        
        if (vendedoresIndex != -1 && !campos[vendedoresIndex].isEmpty()) {
            List<Integer> vendedoresIds = Arrays.stream(campos[vendedoresIndex].split(SEPARADOR_LISTA))
                                               .filter(s -> !s.isEmpty())
                                               .map(Integer::parseInt)
                                               .collect(Collectors.toList());
            franquia.setVendedoresIds(vendedoresIds);
        }
        
        return franquia;
    }
}



