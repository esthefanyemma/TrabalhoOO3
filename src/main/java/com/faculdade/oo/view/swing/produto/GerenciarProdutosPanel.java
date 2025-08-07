/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.produto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Franquia;
import com.faculdade.oo.model.Produto;

public class GerenciarProdutosPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrar, btnEditar, btnRemover, btnAtualizar;
    private int franquiaId;
    
    public GerenciarProdutosPanel(SistemaController sistemaController) {
        this.sistemaController = sistemaController;
        
        try {
            Franquia franquia = sistemaController.buscarFranquiaGerente();
            this.franquiaId = franquia != null ? franquia.getId() : 0;
            
            if (this.franquiaId == 0) {
                JOptionPane.showMessageDialog(this, "Você não possui uma franquia associada.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar franquia: " + e.getMessage());
            return;
        }
        
        initializeComponents();
        setupLayout();
        carregarProdutos();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Gerenciar Produtos"));
        
        String[] colunas = {"ID", "Nome", "Descrição", "Preço", "Estoque", "Est. Mín.", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setGridColor(new Color(240, 240, 240));
        tabelaProdutos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaProdutos.getTableHeader().setBackground(new Color(248, 249, 250));
        tabelaProdutos.getTableHeader().setForeground(new Color(52, 58, 64));
        tabelaProdutos.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        btnCadastrar = new JButton("Cadastrar Produto");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnAtualizar = new JButton("Atualizar Lista");
        
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        
        setupListeners();
    }
    
    private void setupLayout() {
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnAtualizar);
        
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setPreferredSize(new Dimension(900, 450));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(panelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        btnCadastrar.addActionListener(e -> mostrarCadastroProduto());
        btnEditar.addActionListener(e -> editarProdutoSelecionado());
        btnRemover.addActionListener(e -> removerProdutoSelecionado());
        btnAtualizar.addActionListener(e -> carregarProdutos());
        
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            boolean temSelecao = tabelaProdutos.getSelectedRow() != -1;
            btnEditar.setEnabled(temSelecao);
            btnRemover.setEnabled(temSelecao);
        });
    }
    
    private void carregarProdutos() {
        try {
            List<Produto> produtos = sistemaController.listarProdutosPorFranquia(franquiaId);
            modeloTabela.setRowCount(0);
            
            for (Produto produto : produtos) {
                String status = produto.isEstoqueBaixo() ? "ESTOQUE BAIXO" : "OK";
                Object[] linha = {
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    String.format("R$ %.2f", produto.getPreco()),
                    produto.getQuantidadeEstoque(),
                    produto.getEstoqueMinimo(),
                    status
                };
                modeloTabela.addRow(linha);
            }
            
            destacarEstoqueBaixo();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produtos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void destacarEstoqueBaixo() {
        tabelaProdutos.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String status = (String) table.getValueAt(row, 6);
                if ("ESTOQUE BAIXO".equals(status)) {
                    c.setBackground(new Color(255, 200, 200));
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }
                
                return c;
            }
        });
    }
    
    private void mostrarCadastroProduto() {
        CadastroProdutoDialog dialog = new CadastroProdutoDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            sistemaController, 
            franquiaId
        );
        dialog.setVisible(true);
        
        if (dialog.isProdutoCadastrado()) {
            carregarProdutos();
        }
    }
    
    private void editarProdutoSelecionado() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        int produtoId = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        
        try {
            List<Produto> produtos = sistemaController.listarProdutosPorFranquia(franquiaId);
            Produto produto = produtos.stream()
                    .filter(p -> p.getId() == produtoId)
                    .findFirst()
                    .orElse(null);
            
            if (produto != null) {
                EdicaoProdutoDialog dialog = new EdicaoProdutoDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this), 
                    sistemaController, 
                    produto
                );
                dialog.setVisible(true);
                
                if (dialog.isProdutoAtualizado()) {
                    carregarProdutos();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao editar produto: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerProdutoSelecionado() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        int produtoId = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeProduto = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente remover o produto \"" + nomeProduto + "\"?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                sistemaController.removerProduto(produtoId);
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
                carregarProdutos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao remover produto: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


