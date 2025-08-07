/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.franquia;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Endereco;
import com.faculdade.oo.model.Franquia;
import com.faculdade.oo.model.Gerente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EdicaoFranquiaDialog extends JDialog {
    
    private SistemaController sistemaController;
    private Franquia franquia;
    private boolean franquiaEditada = false;
    
    private JTextField nomeField;
    private JTextField ruaField;
    private JTextField numeroField;
    private JTextField complementoField;
    private JTextField bairroField;
    private JTextField cidadeField;
    private JTextField estadoField;
    private JTextField cepField;
    private JComboBox<GerenteItem> gerenteCombo;
    
    public EdicaoFranquiaDialog(Frame parent, SistemaController sistemaController, Franquia franquia) {
        super(parent, "Editar Franquia", true);
        this.sistemaController = sistemaController;
        this.franquia = franquia;
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadGerentes();
        preencherCampos();
    }
    
    private void initializeComponents() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        nomeField = new JTextField(20);
        ruaField = new JTextField(20);
        numeroField = new JTextField(10);
        complementoField = new JTextField(20);
        bairroField = new JTextField(20);
        cidadeField = new JTextField(20);
        estadoField = new JTextField(10);
        cepField = new JTextField(15);
        gerenteCombo = new JComboBox<>();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados da Franquia"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Rua:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ruaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        formPanel.add(numeroField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Complemento:"), gbc);
        gbc.gridx = 1;
        formPanel.add(complementoField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Bairro:"), gbc);
        gbc.gridx = 1;
        formPanel.add(bairroField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cidadeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        formPanel.add(estadoField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cepField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("Gerente:"), gbc);
        gbc.gridx = 1;
        formPanel.add(gerenteCombo, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");
        
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        JPanel buttonPanel = (JPanel) getContentPane().getComponent(1);
        JButton salvarButton = (JButton) buttonPanel.getComponent(0);
        JButton cancelarButton = (JButton) buttonPanel.getComponent(1);
        
        salvarButton.addActionListener(e -> salvarFranquia());
        cancelarButton.addActionListener(e -> dispose());
    }
    
    private void loadGerentes() {
        try {
            List<Gerente> gerentesSemFranquia = sistemaController.listarGerentesSemFranquia();
            List<Gerente> todosGerentes = sistemaController.listarTodosGerentes();
            
            gerenteCombo.removeAllItems();
            gerenteCombo.addItem(new GerenteItem(null, "Selecione um gerente..."));
            
            for (Gerente gerente : gerentesSemFranquia) {
                gerenteCombo.addItem(new GerenteItem(gerente, 
                    gerente.getNome() + " (ID: " + gerente.getId() + ")"));
            }
            
            Gerente gerenteAtual = todosGerentes.stream()
                .filter(g -> g.getId() == franquia.getGerenteId())
                .findFirst()
                .orElse(null);
            
            if (gerenteAtual != null) {
                gerenteCombo.addItem(new GerenteItem(gerenteAtual, 
                    gerenteAtual.getNome() + " (ID: " + gerenteAtual.getId() + ") - ATUAL"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar gerentes: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos() {
        nomeField.setText(franquia.getNome());
        ruaField.setText(franquia.getEndereco().getRua());
        numeroField.setText(franquia.getEndereco().getNumero());
        complementoField.setText(franquia.getEndereco().getComplemento() != null ? 
            franquia.getEndereco().getComplemento() : "");
        bairroField.setText(franquia.getEndereco().getBairro());
        cidadeField.setText(franquia.getEndereco().getCidade());
        estadoField.setText(franquia.getEndereco().getEstado());
        cepField.setText(franquia.getEndereco().getCep());
        
        for (int i = 0; i < gerenteCombo.getItemCount(); i++) {
            GerenteItem item = gerenteCombo.getItemAt(i);
            if (item.getGerente() != null && item.getGerente().getId() == franquia.getGerenteId()) {
                gerenteCombo.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void salvarFranquia() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            GerenteItem gerenteItem = (GerenteItem) gerenteCombo.getSelectedItem();
            if (gerenteItem == null || gerenteItem.getGerente() == null) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um gerente.",
                    "Campo Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Endereco endereco = new Endereco(
                ruaField.getText(),
                numeroField.getText(),
                complementoField.getText().isEmpty() ? null : complementoField.getText(),
                bairroField.getText(),
                cidadeField.getText(),
                estadoField.getText(),
                cepField.getText()
            );
            
            sistemaController.editarFranquia(
                franquia.getId(),
                nomeField.getText(),
                endereco,
                gerenteItem.getGerente().getId()
            );
            
            JOptionPane.showMessageDialog(this,
                "Franquia editada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            franquiaEditada = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao editar franquia: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ruaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rua é obrigatória.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (numeroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Número é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (bairroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bairro é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cidadeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cidade é obrigatória.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (estadoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Estado é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cepField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CEP é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    public boolean isFranquiaEditada() {
        return franquiaEditada;
    }
    
    private static class GerenteItem {
        private Gerente gerente;
        private String displayText;
        
        public GerenteItem(Gerente gerente, String displayText) {
            this.gerente = gerente;
            this.displayText = displayText;
        }
        
        public Gerente getGerente() {
            return gerente;
        }
        
        @Override
        public String toString() {
            return displayText;
        }
    }
}


