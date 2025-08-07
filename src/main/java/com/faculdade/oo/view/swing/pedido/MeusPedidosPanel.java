package com.faculdade.oo.view.swing.pedido;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.*;
import com.faculdade.oo.view.swing.item.AdicionarItensDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MeusPedidosPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabela;
    private JButton btnNovoPedido, btnAdicionarItens, btnFinalizarPedido, btnAtualizar;
    
    public MeusPedidosPanel(SistemaController sistemaController) {
        this.sistemaController = sistemaController;
        
        initializeComponents();
        setupLayout();
        carregarPedidos();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                "Meus Pedidos",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 60, 60)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        String[] colunas = {"ID", "Cliente", "Data/Hora", "Status", "Valor Total", "Forma Pagamento", "Modo Entrega"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPedidos = new JTable(modeloTabela);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaPedidos.getTableHeader().setBackground(new Color(240, 248, 255));
        tabelaPedidos.getTableHeader().setForeground(new Color(60, 60, 60));
        tabelaPedidos.setRowHeight(25);
        tabelaPedidos.setGridColor(new Color(230, 230, 230));
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnNovoPedido = new JButton("Novo Pedido");
        btnAdicionarItens = new JButton("Adicionar Itens");
        btnFinalizarPedido = new JButton("Finalizar Pedido");
        btnAtualizar = new JButton("Atualizar");
        
        btnAdicionarItens.setEnabled(false);
        btnFinalizarPedido.setEnabled(false);
        
        setupListeners();
    }
    
    private void setupLayout() {
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotoes.setBackground(Color.WHITE);
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panelBotoes.add(btnNovoPedido);
        panelBotoes.add(btnAdicionarItens);
        panelBotoes.add(btnFinalizarPedido);
        panelBotoes.add(btnAtualizar);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        scrollPane.setPreferredSize(new Dimension(950, 450));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(panelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.setBackground(new Color(248, 249, 250));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JLabel lblInfo = new JLabel("Selecione um pedido PENDENTE para adicionar itens ou finalizar");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(108, 117, 125));
        panelInfo.add(lblInfo);
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnNovoPedido.addActionListener(e -> criarNovoPedido());
        btnAdicionarItens.addActionListener(e -> adicionarItensAoPedido());
        btnFinalizarPedido.addActionListener(e -> finalizarPedidoSelecionado());
        btnAtualizar.addActionListener(e -> carregarPedidos());
        
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int linhaSelecionada = tabelaPedidos.getSelectedRow();
            if (linhaSelecionada != -1) {
                String status = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
                boolean isPendente = "Pendente".equals(status);
                btnAdicionarItens.setEnabled(isPendente);
                btnFinalizarPedido.setEnabled(isPendente);
            } else {
                btnAdicionarItens.setEnabled(false);
                btnFinalizarPedido.setEnabled(false);
            }
        });
    }
    
    private void carregarPedidos() {
        try {
            List<Pedido> pedidos = sistemaController.listarPedidosVendedor();
            modeloTabela.setRowCount(0);
            
            for (Pedido pedido : pedidos) {
                Object[] linha = {
                    pedido.getId(),
                    pedido.getNomeCliente(),
                    pedido.getDataHoraFormatada(),
                    pedido.getStatus() != null ? pedido.getStatus().getDescricao() : "N/A",
                    String.format("R$ %.2f", pedido.getValorTotal()),
                    pedido.getFormaPagamento() != null ? pedido.getFormaPagamento().getDescricao() : "N/A",
                    pedido.getModoEntrega() != null ? pedido.getModoEntrega().getDescricao() : "N/A"
                };
                modeloTabela.addRow(linha);
            }
            
            destacarPedidosPorStatus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar pedidos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void destacarPedidosPorStatus() {
        tabelaPedidos.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String status = (String) table.getValueAt(row, 3);
                Color backgroundColor = Color.WHITE;
                
                switch (status) {
                    case "Pendente":
                        backgroundColor = new Color(255, 255, 200);
                        break;
                    case "Confirmado":
                        backgroundColor = new Color(200, 255, 200);
                        break;
                    case "Em Preparo":
                        backgroundColor = new Color(200, 200, 255);
                        break;
                    case "Entregue":
                        backgroundColor = new Color(220, 220, 220);
                        break;
                    case "Cancelado":
                        backgroundColor = new Color(255, 200, 200);
                        break;
                }
                
                c.setBackground(backgroundColor);
                
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }
                
                return c;
            }
        });
    }
    
    private void criarNovoPedido() {
        NovoPedidoDialog dialog = new NovoPedidoDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            sistemaController
        );
        dialog.setVisible(true);
        
        if (dialog.isPedidoCriado()) {
            carregarPedidos();
            
            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Pedido criado com sucesso! Deseja adicionar produtos agora?",
                "Adicionar Produtos",
                JOptionPane.YES_NO_OPTION
            );
            
            if (opcao == JOptionPane.YES_OPTION) {
                int pedidoId = dialog.getPedidoId();
                for (int i = 0; i < tabelaPedidos.getRowCount(); i++) {
                    if ((Integer) modeloTabela.getValueAt(i, 0) == pedidoId) {
                        tabelaPedidos.setRowSelectionInterval(i, i);
                        adicionarItensAoPedido();
                        break;
                    }
                }
            }
        }
    }
    
    private void adicionarItensAoPedido() {
        int linhaSelecionada = tabelaPedidos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido primeiro.");
            return;
        }
        
        int pedidoId = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeCliente = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        AdicionarItensDialog dialog = new AdicionarItensDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            sistemaController, 
            pedidoId, 
            nomeCliente
        );
        dialog.setVisible(true);
        
        if (dialog.isItensAdicionados()) {
            carregarPedidos();
        }
    }
    
    private void finalizarPedidoSelecionado() {
        int linhaSelecionada = tabelaPedidos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido primeiro.");
            return;
        }
        
        int pedidoId = (Integer) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeCliente = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente finalizar o pedido do cliente \"" + nomeCliente + "\"?\n" +
            "Esta ação não poderá ser desfeita e o estoque será atualizado.",
            "Confirmar Finalização",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                sistemaController.finalizarPedido(pedidoId);
                JOptionPane.showMessageDialog(this, 
                    "Pedido finalizado com sucesso!\nEstoque atualizado automaticamente.");
                carregarPedidos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao finalizar pedido: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
