/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.dao;

import com.faculdade.oo.model.Usuario;
import com.faculdade.oo.model.Dono;
import com.faculdade.oo.model.Gerente;
import com.faculdade.oo.model.Vendedor;
import com.faculdade.oo.model.TipoUsuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements GenericDAO<Usuario> {
    
    private static final String ARQUIVO_PADRAO = "data/usuarios.txt";
    private static final String SEPARADOR = ";";
    private String nomeArquivo;
    
    public UsuarioDAO() {
        this.nomeArquivo = ARQUIVO_PADRAO;
    }
    
    public UsuarioDAO(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    @Override
    public void salvar(Usuario usuario) throws Exception {
        List<Usuario> usuarios = listarTodos();
        
        if (usuarios.stream().anyMatch(u -> u.getId() == usuario.getId())) {
            throw new Exception("Já existe um usuário com o ID " + usuario.getId());
        }
        
        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()))) {
            throw new Exception("Já existe um usuário com o email " + usuario.getEmail());
        }
        
        if (usuarios.stream().anyMatch(u -> u.getCpf().equals(usuario.getCpf()))) {
            throw new Exception("Já existe um usuário com o CPF " + usuario.getCpf());
        }
        
        usuarios.add(usuario);
        salvarTodos(usuarios);
    }
    
    @Override
    public Usuario buscarPorId(int id) throws Exception {
        return listarTodos().stream()
                           .filter(u -> u.getId() == id)
                           .findFirst()
                           .orElse(null);
    }
    
    public Usuario buscarPorEmail(String email) throws Exception {
        return listarTodos().stream()
                           .filter(u -> u.getEmail().equals(email))
                           .findFirst()
                           .orElse(null);
    }
    
    public List<Usuario> buscarPorTipo(TipoUsuario tipo) throws Exception {
        return listarTodos().stream()
                           .filter(u -> u.getTipo() == tipo)
                           .toList();
    }
    
    @Override
    public List<Usuario> listarTodos() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        File arquivo = new File(nomeArquivo);
        
        if (!arquivo.exists()) {
            return usuarios;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Usuario usuario = parsearLinha(linha);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        }
        
        return usuarios;
    }
    
    @Override
    public void atualizar(Usuario usuario) throws Exception {
        List<Usuario> usuarios = listarTodos();
        boolean encontrado = false;
        
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new Exception("Usuário com ID " + usuario.getId() + " não encontrado");
        }
        
        salvarTodos(usuarios);
    }
    
    @Override
    public void remover(int id) throws Exception {
        List<Usuario> usuarios = listarTodos();
        boolean removido = usuarios.removeIf(u -> u.getId() == id);
        
        if (!removido) {
            throw new Exception("Usuário com ID " + id + " não encontrado");
        }
        
        salvarTodos(usuarios);
    }
    
    @Override
    public boolean existe(int id) throws Exception {
        return buscarPorId(id) != null;
    }
    
    @Override
    public int obterProximoId() throws Exception {
        List<Usuario> usuarios = listarTodos();
        return usuarios.stream()
                      .mapToInt(Usuario::getId)
                      .max()
                      .orElse(0) + 1;
    }
    
    private void salvarTodos(List<Usuario> usuarios) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Usuario usuario : usuarios) {
                writer.println(formatarLinha(usuario));
            }
        }
    }
    
    private String formatarLinha(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(usuario.getId()).append(SEPARADOR)
          .append(usuario.getNome()).append(SEPARADOR)
          .append(usuario.getCpf()).append(SEPARADOR)
          .append(usuario.getEmail()).append(SEPARADOR)
          .append(usuario.getSenha()).append(SEPARADOR)
          .append(usuario.getTipo().name());
        
        if (usuario instanceof Gerente) {
            Gerente gerente = (Gerente) usuario;
            sb.append(SEPARADOR).append(gerente.getFranquiaId());
        } else if (usuario instanceof Vendedor) {
            Vendedor vendedor = (Vendedor) usuario;
            sb.append(SEPARADOR).append(vendedor.getFranquiaId())
              .append(SEPARADOR).append(vendedor.getTotalVendas())
              .append(SEPARADOR).append(vendedor.getQuantidadeVendas());
        }
        
        return sb.toString();
    }
    
    private Usuario parsearLinha(String linha) {
        String[] campos = linha.split(SEPARADOR);
        
        if (campos.length < 6) {
            return null;
        }
        
        int id = Integer.parseInt(campos[0]);
        String nome = campos[1];
        String cpf = campos[2];
        String email = campos[3];
        String senha = campos[4];
        TipoUsuario tipo = TipoUsuario.valueOf(campos[5]);
        
        switch (tipo) {
            case DONO:
                return new Dono(id, nome, cpf, email, senha);
                
            case GERENTE:
                Gerente gerente = new Gerente(id, nome, cpf, email, senha);
                if (campos.length > 6) {
                    gerente.setFranquiaId(Integer.parseInt(campos[6]));
                }
                return gerente;
                
            case VENDEDOR:
                Vendedor vendedor = new Vendedor();
                vendedor.setId(id);
                vendedor.setNome(nome);
                vendedor.setCpf(cpf);
                vendedor.setEmail(email);
                vendedor.setSenha(senha);
                vendedor.setTipo(tipo);
                if (campos.length > 6) {
                    vendedor.setFranquiaId(Integer.parseInt(campos[6]));
                }
                if (campos.length > 7) {
                    vendedor.setTotalVendas(Double.parseDouble(campos[7]));
                }
                if (campos.length > 8) {
                    vendedor.setQuantidadeVendas(Integer.parseInt(campos[8]));
                }
                return vendedor;
                
            default:
                return null;
        }
    }
}



