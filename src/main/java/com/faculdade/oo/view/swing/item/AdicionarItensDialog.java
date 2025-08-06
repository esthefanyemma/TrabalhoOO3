package com.faculdade.oo.view.swing;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdicionarItensDialog extends JDialog {
    
    private SistemaController sistemaController;
    private int pedidoId;
    private String nomeCliente;
    private boolean itensAdicionados = false;
    private int franquiaId;
    
    private JTable tabelaProdutos;
    private DefaultTableModel modeloProdutos;
    private JSpinner spinnerQuantidade;
    private JButton btnAdicionar, btnFechar;
    private JLabel lblPedidoInfo;
    
    public AdicionarItensDialogSimples(JFrame parent, SistemaController sistemaController, 
                               int pedidoId, String nomeCliente) {
        super(parent, "Adicionar Itens ao Pedido", true);
        this.sistemaController = sistemaController;
        this.pedidoId = pedidoId;
        this.nomeCliente = nomeCliente;
        
        // Obter ID da franquia do vendedor
        Vendedor vendedor = (Vendedor) sistemaController.getAuthController().getUsuarioLogado();
        this.franquiaId = vendedor.getFranquiaId();
        
        initializeComponents();
        setupLayout();
        setupListeners();
        carregarProdutos();
        
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        // Info do pedido
        lblPedidoInfo = new JLabel("Pedido #" + pedidoId + " - Cliente: " + nomeCliente);
        lblPedidoInfo.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Tabela de produtos
        String[] colunas = {"ID", "Nome", "Descrição", "Preço", "Estoque"};
        modeloProdutos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaProdutos = new JTable(modeloProdutos);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar larguras das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setMaxWidth(50);  // ID
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(200); // Descrição
        tabelaProdutos.getColumnModel().getColumn(3).setMaxWidth(80);  // Preço
        tabelaProdutos.getColumnModel().getColumn(4).setMaxWidth(70);  // Estoque
        
        // Spinner para quantidade
        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        
        // Botões
        btnAdicionar = new JButton("Adicionar Item");
        btnFechar = new JButton("Fechar");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior com info do pedido
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBorder(BorderFactory.createEtchedBorder());
        panelInfo.add(lblPedidoInfo);
        add(panelInfo, BorderLayout.NORTH);
        
        // Panel central com tabela
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior com controles
        JPanel panelControles = new JPanel(new FlowLayout());
        panelControles.add(new JLabel("Quantidade:"));
        panelControles.add(spinnerQuantidade);
        panelControles.add(btnAdicionar);
        panelControles.add(btnFechar);
        add(panelControles, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnAdicionar.addActionListener(e -> adicionarItem());
        btnFechar.addActionListener(e -> dispose());
        
        // Duplo clique para adicionar
        tabelaProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    adicionarItem();
                }
            }
        });
    }
    
    private void carregarProdutos() {
        try {
            List<Produto> produtos = sistemaController.listarProdutosPorFranquia(franquiaId);
            modeloProdutos.setRowCount(0);
            
            for (Produto produto : produtos) {
                Object[] linha = {
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    String.format("R$ %.2f", produto.getPreco()),
                    produto.getQuantidadeEstoque()
                };
                modeloProdutos.addRow(linha);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produtos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarItem() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um produto para adicionar ao pedido.");
            return;
        }
        
        try {
            int produtoId = (Integer) modeloProdutos.getValueAt(linhaSelecionada, 0);
            int quantidade = (Integer) spinnerQuantidade.getValue();
            int estoque = (Integer) modeloProdutos.getValueAt(linhaSelecionada, 4);
            
            if (quantidade > estoque) {
                JOptionPane.showMessageDialog(this, 
                    "Quantidade solicitada maior que o estoque disponível (" + estoque + ")");
                return;
            }
            
            sistemaController.adicionarItemPedido(pedidoId, produtoId, quantidade);
            
            JOptionPane.showMessageDialog(this, 
                "Item adicionado ao pedido com sucesso!");
            
            itensAdicionados = true;
            carregarProdutos(); // Recarregar para mostrar estoque atualizado
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao adicionar item: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isItensAdicionados() {
        return itensAdicionados;
    }
}
