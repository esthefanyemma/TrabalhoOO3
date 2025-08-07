/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.faculdade.oo.util.InicializadorDados;
import com.faculdade.oo.view.swing.LoginView;

public class TrabalhoOficial {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar Look and Feel: " + e.getMessage());
        }
        
        try {
            System.out.println("=== SISTEMA DE GERENCIAMENTO DE FRANQUIAS ===");
            System.out.println("Trabalho Prático de Orientação a Objetos - DCC025");
            System.out.println("===============================================");
            System.out.println("Iniciando interface gráfica...");
            
            InicializadorDados inicializador = new InicializadorDados();
            inicializador.criarDadosExemplo();
            
            SwingUtilities.invokeLater(() -> {
                new LoginView().setVisible(true);
            });
            
        } catch (Exception e) {
            System.err.println("Erro fatal no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



