package com.faculdade.oo.view.swing;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Produto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EdicaoProdutoDialog extends JDialog {
    
    private SistemaController sistemaController;
    private Produto produto;
    private boolean produtoAtualizado = false;
    
    private JTextField txtNome, txtDescricao, txtPreco, txtQuantidadeEstoque, txtEstoqueMinimo;
    private JButton btnSalvar, btnCancelar;
    
    public EdicaoProdutoDialog(JFrame parent, SistemaController sistemaController, Produto produto) {
        super(parent, "Editar Produto", true);
        this.sistemaController = sistemaController;
        this.produto = produto;
        
        initializeComponents();
        setupLayout();
        setupListeners();
        preencherCampos();
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        // Campos com styling melhorado
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);
        
        txtNome = new JTextField(20);
        txtNome.setFont(fieldFont);
        txtNome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        txtDescricao = new JTextField(30);
        txtDescricao.setFont(fieldFont);
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        txtPreco = new JTextField(10);
        txtPreco.setFont(fieldFont);
        txtPreco.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        txtQuantidadeEstoque = new JTextField(10);
        txtQuantidadeEstoque.setFont(fieldFont);
        txtQuantidadeEstoque.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        txtEstoqueMinimo = new JTextField(10);
        txtEstoqueMinimo.setFont(fieldFont);
        txtEstoqueMinimo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Botões estilizados
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel principal com formulário estilizado
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Editar Produto",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(52, 58, 64)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        
        // ID (somente leitura)
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblIdLabel = new JLabel("ID:");
        lblIdLabel.setFont(labelFont);
        lblIdLabel.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblIdLabel, gbc);
        gbc.gridx = 1;
        JLabel lblId = new JLabel(String.valueOf(produto.getId()));
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblId.setForeground(new Color(0, 123, 255));
        panelFormulario.add(lblId, gbc);
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblNome = new JLabel("Nome:*");
        lblNome.setFont(labelFont);
        lblNome.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblNome, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNome, gbc);
        
        // Descrição
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDescricao = new JLabel("Descrição:*");
        lblDescricao.setFont(labelFont);
        lblDescricao.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblDescricao, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDescricao, gbc);
        
        // Preço
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPreco = new JLabel("Preço (R$):*");
        lblPreco.setFont(labelFont);
        lblPreco.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblPreco, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtPreco, gbc);
        
        // Quantidade em Estoque
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblQuantidade = new JLabel("Quantidade em Estoque:*");
        lblQuantidade.setFont(labelFont);
        lblQuantidade.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtQuantidadeEstoque, gbc);
        
        // Estoque Mínimo
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblEstoqueMin = new JLabel("Estoque Mínimo:*");
        lblEstoqueMin.setFont(labelFont);
        lblEstoqueMin.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblEstoqueMin, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEstoqueMinimo, gbc);
        
        // Panel de botões estilizado
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        panelBotoes.setBackground(new Color(248, 249, 250));
        panelBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnSalvar);
        
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());
        
        // Enter para salvar, Escape para cancelar
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
    
    private void preencherCampos() {
        txtNome.setText(produto.getNome());
        txtDescricao.setText(produto.getDescricao());
        txtPreco.setText(String.format("%.2f", produto.getPreco()));
        txtQuantidadeEstoque.setText(String.valueOf(produto.getQuantidadeEstoque()));
        txtEstoqueMinimo.setText(String.valueOf(produto.getEstoqueMinimo()));
    }
    
    private void salvarAlteracoes() {
        try {
            // Validar campos
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
            
            // Atualizar produto
            sistemaController.atualizarProduto(produto.getId(), nome, descricao, preco, quantidadeEstoque, estoqueMinimo);
            
            JOptionPane.showMessageDialog(this, 
                "Produto atualizado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            produtoAtualizado = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao atualizar produto: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isProdutoAtualizado() {
        return produtoAtualizado;
    }
}
