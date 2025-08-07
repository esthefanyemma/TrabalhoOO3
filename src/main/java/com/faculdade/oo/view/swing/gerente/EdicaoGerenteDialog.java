package com.faculdade.oo.view.swing.gerente;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Gerente;

import javax.swing.*;
import java.awt.*;

public class EdicaoGerenteDialog extends JDialog {
    
    private SistemaController sistemaController;
    private Gerente gerente;
    private boolean gerenteEditado = false;
    
    private JTextField nomeField;
    private JTextField emailField;
    private JTextField cpfField;
    private JPasswordField senhaField;
    
    public EdicaoGerenteDialog(Frame parent, SistemaController sistemaController, Gerente gerente) {
        super(parent, "Editar Gerente", true);
        this.sistemaController = sistemaController;
        this.gerente = gerente;
        initializeComponents();
        setupLayout();
        setupEventListeners();
        preencherCampos();
    }
    
    private void initializeComponents() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        nomeField = new JTextField(20);
        emailField = new JTextField(20);
        cpfField = new JTextField(15);
        cpfField.setEditable(false); 
        senhaField = new JPasswordField(15);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Gerente"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        JPanel cpfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cpfPanel.add(cpfField);
        cpfPanel.add(new JLabel(" (não editável)"));
        formPanel.add(cpfPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Nova Senha:"), gbc);
        gbc.gridx = 1;
        JPanel senhaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        senhaPanel.add(senhaField);
        senhaPanel.add(new JLabel(" (deixe vazio para manter)"));
        formPanel.add(senhaPanel, gbc);
        
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
        
        salvarButton.addActionListener(e -> salvarGerente());
        cancelarButton.addActionListener(e -> dispose());
    }
    
    private void preencherCampos() {
        nomeField.setText(gerente.getNome());
        emailField.setText(gerente.getEmail());
        cpfField.setText(gerente.getCpf());
        // Senha fica vazia por segurança
    }
    
    private void salvarGerente() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String novaSenha = new String(senhaField.getPassword()).trim();
            
            if (novaSenha.isEmpty()) {
                novaSenha = null;
            }
            
            sistemaController.editarGerente(gerente.getId(), nome, email, novaSenha);
            
            JOptionPane.showMessageDialog(this,
                "Gerente editado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            gerenteEditado = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao editar gerente: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            nomeField.requestFocus();
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email é obrigatório.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            emailField.requestFocus();
            return false;
        }
        
        String email = emailField.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Email inválido.", "Campo Inválido", JOptionPane.WARNING_MESSAGE);
            emailField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isGerenteEditado() {
        return gerenteEditado;
    }
}
