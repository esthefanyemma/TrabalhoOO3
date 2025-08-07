/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.produto;

import com.faculdade.oo.controller.SistemaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroProdutoDialog extends JDialog {
    
    private SistemaController sistemaController;
    private int franquiaId;
    private boolean produtoCadastrado = false;
    
    private JTextField txtNome, txtDescricao, txtPreco, txtQuantidadeEstoque, txtEstoqueMinimo;
    private JButton btnSalvar, btnCancelar;
    
    public CadastroProdutoDialog(JFrame parent, SistemaController sistemaController, int franquiaId) {
        super(parent, "Cadastrar Produto", true);
        this.sistemaController = sistemaController;
        this.franquiaId = franquiaId;
        
        initializeComponents();
        setupLayout();
        setupListeners();
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        txtNome = new JTextField(20);
        txtDescricao = new JTextField(30);
        txtPreco = new JTextField(10);
        txtQuantidadeEstoque = new JTextField(10);
        txtEstoqueMinimo = new JTextField(10);
        
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("Cadastrar Produto");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        addFormField(mainPanel, gbc, 1, "Nome:", txtNome);
        addFormField(mainPanel, gbc, 2, "Descrição:", txtDescricao);
        addFormField(mainPanel, gbc, 3, "Preço (R$):", txtPreco);
        addFormField(mainPanel, gbc, 4, "Quantidade em Estoque:", txtQuantidadeEstoque);
        addFormField(mainPanel, gbc, 5, "Estoque Mínimo:", txtEstoqueMinimo);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void setupListeners() {
        btnSalvar.addActionListener(e -> salvarProduto());
        btnCancelar.addActionListener(e -> dispose());
        
        getRootPane().setDefaultButton(btnSalvar);
        
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void salvarProduto() {
        try {
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String precoStr = txtPreco.getText().trim();
            String quantidadeStr = txtQuantidadeEstoque.getText().trim();
            String estoqueMinimoStr = txtEstoqueMinimo.getText().trim();
            
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório");
            }
            if (descricao.isEmpty()) {
                throw new IllegalArgumentException("Descrição é obrigatória");
            }
            if (precoStr.isEmpty()) {
                throw new IllegalArgumentException("Preço é obrigatório");
            }
            if (quantidadeStr.isEmpty()) {
                throw new IllegalArgumentException("Quantidade em estoque é obrigatória");
            }
            if (estoqueMinimoStr.isEmpty()) {
                throw new IllegalArgumentException("Estoque mínimo é obrigatório");
            }
            
            // Converter valores numéricos
            double preco;
            int quantidadeEstoque;
            int estoqueMinimo;
            
            try {
                preco = Double.parseDouble(precoStr.replace(",", "."));
                if (preco <= 0) {
                    throw new IllegalArgumentException("Preço deve ser maior que zero");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Preço deve ser um valor numérico válido");
            }
            
            try {
                quantidadeEstoque = Integer.parseInt(quantidadeStr);
                if (quantidadeEstoque < 0) {
                    throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantidade em estoque deve ser um número inteiro");
            }
            
            try {
                estoqueMinimo = Integer.parseInt(estoqueMinimoStr);
                if (estoqueMinimo < 0) {
                    throw new IllegalArgumentException("Estoque mínimo não pode ser negativo");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Estoque mínimo deve ser um número inteiro");
            }
            
            sistemaController.cadastrarProduto(nome, descricao, preco, quantidadeEstoque, estoqueMinimo, franquiaId);
            
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            
            produtoCadastrado = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    public boolean isProdutoCadastrado() {
        return produtoCadastrado;
    }
}



