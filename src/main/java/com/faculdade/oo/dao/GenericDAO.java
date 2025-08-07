/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.dao;

import java.util.List;

public interface GenericDAO<T> {
    
    void salvar(T entidade) throws Exception;
    
    T buscarPorId(int id) throws Exception;
    
    List<T> listarTodos() throws Exception;
    
    void atualizar(T entidade) throws Exception;

    void remover(int id) throws Exception;

    boolean existe(int id) throws Exception;
    
    int obterProximoId() throws Exception;
}



