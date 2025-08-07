package com.faculdade.oo.view.swing.produto;

import com.faculdade.oo.controller.SistemaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color labelColor = new Color(70, 70, 70);
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome:*");
        lblNome.setFont(labelFont);
        lblNome.setForeground(labelColor);
        panelFormulario.add(lblNome, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblDescricao = new JLabel("Descrição:*");
        lblDescricao.setFont(labelFont);
        lblDescricao.setForeground(labelColor);
        panelFormulario.add(lblDescricao, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtDescricao, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblPreco = new JLabel("Preço (R$):*");
        lblPreco.setFont(labelFont);
        lblPreco.setForeground(labelColor);
        panelFormulario.add(lblPreco, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtPreco, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblQuantidade = new JLabel("Quantidade em Estoque:*");
        lblQuantidade.setFont(labelFont);
        lblQuantidade.setForeground(labelColor);
        panelFormulario.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtQuantidadeEstoque, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel lblEstoqueMin = new JLabel("Estoque Mínimo:*");
        lblEstoqueMin.setFont(labelFont);
        lblEstoqueMin.setForeground(labelColor);
        panelFormulario.add(lblEstoqueMin, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtEstoqueMinimo, gbc);
        
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotoes.setBackground(Color.WHITE);
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);
        
        mainPanel.add(panelFormulario, BorderLayout.CENTER);
        mainPanel.add(panelBotoes, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBackground(new Color(248, 249, 250));
        panelHeader.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel lblLegenda = new JLabel("* Campos obrigatórios");
        lblLegenda.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblLegenda.setForeground(new Color(108, 117, 125));
        panelHeader.add(lblLegenda);
        add(panelHeader, BorderLayout.NORTH);
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
            
            JOptionPane.showMessageDialog(this, 
                "Produto cadastrado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            produtoCadastrado = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar produto: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isProdutoCadastrado() {
        return produtoCadastrado;
    }
}
