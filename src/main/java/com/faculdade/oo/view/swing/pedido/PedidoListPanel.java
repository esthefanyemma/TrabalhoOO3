/*
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.pedido;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PedidoListPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar;
    
    public PedidoListPanel(SistemaController sistemaController) {
        this.sistemaController = sistemaController;
        
        initializeComponents();
        setupLayout();
        setupListeners();
        carregarPedidos();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Pedidos da Franquia"));
        
        String[] colunas = {"ID", "Cliente", "Telefone", "Data/Hora", "Status", "Valor Total", "Forma Pagamento", "Modo Entrega"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaPedidos = new JTable(modeloTabela);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelaPedidos.setRowHeight(25);
        tabelaPedidos.setGridColor(new Color(240, 240, 240));
        tabelaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaPedidos.getTableHeader().setBackground(new Color(248, 249, 250));
        tabelaPedidos.getTableHeader().setForeground(new Color(52, 58, 64));
        tabelaPedidos.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        tabelaPedidos.getColumnModel().getColumn(0).setMaxWidth(50);   // ID
        tabelaPedidos.getColumnModel().getColumn(1).setPreferredWidth(150); // Cliente
        tabelaPedidos.getColumnModel().getColumn(2).setPreferredWidth(120); // Telefone
        tabelaPedidos.getColumnModel().getColumn(3).setPreferredWidth(130); // Data/Hora
        tabelaPedidos.getColumnModel().getColumn(4).setPreferredWidth(100); // Status
        tabelaPedidos.getColumnModel().getColumn(5).setPreferredWidth(100); // Valor Total
        tabelaPedidos.getColumnModel().getColumn(6).setPreferredWidth(120); // Forma Pagamento
        tabelaPedidos.getColumnModel().getColumn(7).setPreferredWidth(120); // Modo Entrega
        
        btnAtualizar = new JButton("Atualizar");
    }
    
    private void setupLayout() {
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotoes.add(btnAtualizar);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
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
        btnAtualizar.addActionListener(e -> carregarPedidos());
    }
    
    private void carregarPedidos() {
        try {
            Franquia franquia = sistemaController.buscarFranquiaGerente();
            
            if (franquia == null) {
                JOptionPane.showMessageDialog(this,
                    "Franquia não encontrada.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<Pedido> pedidos = sistemaController.listarPedidosFranquia(franquia.getId());
            modeloTabela.setRowCount(0);
            
            for (Pedido pedido : pedidos) {
                Object[] linha = {
                    pedido.getId(),
                    pedido.getNomeCliente(),
                    pedido.getTelefoneCliente() != null ? pedido.getTelefoneCliente() : "N/A",
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
                
                String status = (String) table.getValueAt(row, 4);
                Color backgroundColor = Color.WHITE;
                
                switch (status) {
                    case "PENDENTE":
                    case "Pendente":
                        backgroundColor = new Color(255, 255, 200);
                        break;
                    case "CONFIRMADO":
                    case "Confirmado":
                        backgroundColor = new Color(200, 255, 200);
                        break;
                    case "EM_PREPARO":
                    case "Em Preparo":
                        backgroundColor = new Color(200, 200, 255);
                        break;
                    case "SAIU_PARA_ENTREGA":
                    case "Saiu para Entrega":
                        backgroundColor = new Color(255, 200, 255);
                        break;
                    case "ENTREGUE":
                    case "Entregue":
                        backgroundColor = new Color(220, 220, 220);
                        break;
                    case "CANCELADO":
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
    
    public void atualizarLista() {
        carregarPedidos();
    }
}



