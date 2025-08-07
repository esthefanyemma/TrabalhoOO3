/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.controller;

import com.faculdade.oo.dao.UsuarioDAO;
import com.faculdade.oo.exceptions.AutenticacaoException;
import com.faculdade.oo.model.Usuario;

public class AutenticacaoController {
    
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogado;
    
    public AutenticacaoController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario autenticar(String email, String senha) throws AutenticacaoException {
        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);
            
            if (usuario == null) {
                throw new AutenticacaoException("Email não encontrado");
            }
            
            if (!usuario.getSenha().equals(senha)) {
                throw new AutenticacaoException("Senha incorreta");
            }
            
            this.usuarioLogado = usuario;
            return usuario;
            
        } catch (Exception e) {
            throw new AutenticacaoException("Erro ao autenticar usuário: " + e.getMessage());
        }
    }
    
    public void logout() {
        this.usuarioLogado = null;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public boolean isLogado() {
        return usuarioLogado != null;
    }
    
    public boolean isUsuarioTipo(Class<?> tipoUsuario) {
        return isLogado() && tipoUsuario.isInstance(usuarioLogado);
    }
}



