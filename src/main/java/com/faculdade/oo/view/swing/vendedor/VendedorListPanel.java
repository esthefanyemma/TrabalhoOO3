/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Vendedor;
import com.faculdade.oo.view.swing.MainView;

public class VendedorListPanel extends JPanel {
    
    private SistemaController sistemaController;
    private MainView mainView;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCadastrar, btnEditar, btnRemover, btnAtualizar;
    
    public VendedorListPanel(SistemaController sistemaController) {
        this(sistemaController, null);
    }
    
    public VendedorListPanel(SistemaController sistemaController, MainView mainView) {
        this.sistemaController = sistemaController;
        this.mainView = mainView;
        initializeComponents();
        setupLayout();
        setupListeners();
        loadData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Vendedores da Franquia"));
        
        String[] columnNames = {"ID", "Nome", "Email", "Total Vendas", "Qtd Vendas", "Ticket Médio"};
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
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   
        table.getColumnModel().getColumn(1).setPreferredWidth(200); 
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        
        btnCadastrar = new JButton("Cadastrar");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnAtualizar = new JButton("Atualizar");
        
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
    }
    
    private void setupLayout() {
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnAtualizar);
        
        JScrollPane scrollPane = new JScrollPane(table);
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
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getSelectedRow() != -1;
                btnEditar.setEnabled(hasSelection);
                btnRemover.setEnabled(hasSelection);
            }
        });
        
        btnCadastrar.addActionListener(e -> cadastrarVendedor());
        btnEditar.addActionListener(e -> editarVendedor());
        btnRemover.addActionListener(e -> removerVendedor());
        btnAtualizar.addActionListener(e -> loadData());
    }
    
    private void cadastrarVendedor() {
        if (mainView != null) {
            mainView.mostrarCadastroVendedor();
            SwingUtilities.invokeLater(() -> loadData());
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void editarVendedor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um vendedor para editar.");
            return;
        }
        
        int vendedorId = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        if (mainView != null) {
            mainView.mostrarEdicaoVendedor(vendedorId);
            SwingUtilities.invokeLater(() -> loadData());
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void removerVendedor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um vendedor para remover.");
            return;
        }
        
        int vendedorId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String nomeVendedor = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja remover o vendedor \"" + nomeVendedor + "\"?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                sistemaController.removerVendedor(vendedorId);
                JOptionPane.showMessageDialog(this, "Vendedor removido com sucesso!");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao remover vendedor: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadData() {
        try {
            List<Vendedor> vendedores = sistemaController.listarVendedoresPorVendas();
            
            tableModel.setRowCount(0);
            
            for (Vendedor vendedor : vendedores) {
                Object[] row = {
                    vendedor.getId(),
                    vendedor.getNome(),
                    vendedor.getEmail(),
                    String.format("R$ %.2f", vendedor.getTotalVendas()),
                    vendedor.getQuantidadeVendas(),
                    String.format("R$ %.2f", vendedor.getTicketMedio())
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
    
    public void atualizarLista() {
        loadData();
    }
}



