package com.faculdade.oo.view.swing.vendedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Vendedor;

public class RankingVendedoresPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public RankingVendedoresPanel(SistemaController sistemaController) {
        this.sistemaController = sistemaController;
        initializeComponents();
        setupLayout();
        loadData();
    }
    
    private void initializeComponents() {
        String[] columnNames = {"Posição", "Nome", "Total Vendas", "Qtd Vendas", "Ticket Médio"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(240, 240, 240));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(52, 58, 64));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        // Configurar larguras das colunas
        table.getColumnModel().getColumn(0).setMaxWidth(80);   // Posição
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Total Vendas
        table.getColumnModel().getColumn(3).setMaxWidth(100);  // Qtd Vendas
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Ticket Médio
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Ranking de Vendedores"));
        
        // Painel superior com botão de atualização
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAtualizar = new JButton("Atualizar Ranking");
        btnAtualizar.addActionListener(e -> loadData());
        topPanel.add(btnAtualizar);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Tabela com scroll padrão
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 450));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadData() {
        try {
            List<Vendedor> vendedores = sistemaController.listarVendedoresPorVendas();
            
            tableModel.setRowCount(0);
            
            if (vendedores.isEmpty()) {
                // Adicionar linha informativa
                Object[] row = {"N/A", "Nenhum vendedor encontrado", "R$ 0,00", "0", "R$ 0,00"};
                tableModel.addRow(row);
                return;
            }
            
            int posicao = 1;
            for (Vendedor vendedor : vendedores) {
                Object[] row = {
                    posicao + "º",
                    vendedor.getNome(),
                    String.format("R$ %.2f", vendedor.getTotalVendas()),
                    vendedor.getQuantidadeVendas(),
                    String.format("R$ %.2f", vendedor.getTicketMedio())
                };
                tableModel.addRow(row);
                posicao++;
            }
            
            // Destacar o primeiro lugar
            if (vendedores.size() > 0) {
                // Aqui você poderia adicionar formatação especial para o primeiro lugar
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar ranking: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
