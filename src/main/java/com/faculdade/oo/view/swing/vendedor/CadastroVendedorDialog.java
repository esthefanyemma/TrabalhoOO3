/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.vendedor;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Vendedor;

import javax.swing.*;
import java.awt.*;

public class CadastroVendedorDialog extends JDialog {
    
    private SistemaController sistemaController;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private Vendedor vendedorEdicao;
    private boolean modoEdicao;
    
    public CadastroVendedorDialog(Frame parent, SistemaController sistemaController) {
        super(parent, "Cadastrar Vendedor", true);
        this.sistemaController = sistemaController;
        this.modoEdicao = false;
        this.vendedorEdicao = null;
        initializeComponents();
        setupLayout();
    }
    
    public CadastroVendedorDialog(Frame parent, SistemaController sistemaController, Vendedor vendedor) {
        super(parent, "Editar Vendedor", true);
        this.sistemaController = sistemaController;
        this.modoEdicao = true;
        this.vendedorEdicao = vendedor;
        initializeComponents();
        setupLayout();
        preencherCampos();
    }
    
    private void initializeComponents() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        
        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        emailField = new JTextField(20);
        senhaField = new JPasswordField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel(modoEdicao ? "Editar Vendedor" : "Cadastro de Novo Vendedor");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        addFormField(mainPanel, gbc, 1, "Nome:", nomeField);
        addFormField(mainPanel, gbc, 2, "CPF:", cpfField);
        addFormField(mainPanel, gbc, 3, "Email:", emailField);
        addFormField(mainPanel, gbc, 4, "Senha:", senhaField);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarVendedor());
        buttonPanel.add(salvarButton);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelarButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void preencherCampos() {
        if (vendedorEdicao != null) {
            nomeField.setText(vendedorEdicao.getNome());
            cpfField.setText(vendedorEdicao.getCpf());
            cpfField.setEditable(false); 
            emailField.setText(vendedorEdicao.getEmail());
        }
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void salvarVendedor() {
        try {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());
            
            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, CPF e email são obrigatórios.");
                return;
            }
            
            if (modoEdicao) {
                if (senha.isEmpty()) {
                    sistemaController.editarVendedor(vendedorEdicao.getId(), nome, cpf, email, null);
                } else {
                    sistemaController.editarVendedor(vendedorEdicao.getId(), nome, cpf, email, senha);
                }
                JOptionPane.showMessageDialog(this, "Vendedor editado com sucesso!");
            } else {
                if (senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Senha é obrigatória para cadastro.");
                    return;
                }
                sistemaController.cadastrarVendedor(nome, cpf, email, senha);
                JOptionPane.showMessageDialog(this, "Vendedor cadastrado com sucesso!");
            }
            
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}



