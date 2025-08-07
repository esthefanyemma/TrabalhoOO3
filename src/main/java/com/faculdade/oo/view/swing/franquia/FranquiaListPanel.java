/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.franquia;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Franquia;
import com.faculdade.oo.view.swing.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FranquiaListPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCadastrar, btnEditar, btnRemover, btnAtualizar;
    private MainView mainView;
    
    public FranquiaListPanel(SistemaController sistemaController, MainView mainView) {
        this.sistemaController = sistemaController;
        this.mainView = mainView;
        initializeComponents();
        setupLayout();
        loadData();
    }
    
    public FranquiaListPanel(SistemaController sistemaController) {
        this(sistemaController, null);
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Gerenciar Franquias"));
        
        String[] columnNames = {"ID", "Nome", "Cidade", "Estado", "Gerente ID", "Receita", "Pedidos", "Ticket Médio"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50); 
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(50); 
        
        btnCadastrar = new JButton("Cadastrar Franquia");
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 450));
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
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        JLabel lblInfo = new JLabel("Selecione uma franquia para editar ou remover");
        lblInfo.setForeground(new Color(100, 100, 100));
        lblInfo.setFont(lblInfo.getFont().deriveFont(Font.PLAIN, 12f));
        panelInfo.add(lblInfo);
        
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getSelectedRow() != -1;
                btnEditar.setEnabled(hasSelection);
                btnRemover.setEnabled(hasSelection);
            }
        });
        
        btnCadastrar.addActionListener(e -> cadastrarFranquia());
        btnEditar.addActionListener(e -> editarFranquia());
        btnRemover.addActionListener(e -> removerFranquia());
        btnAtualizar.addActionListener(e -> loadData());
    }
    
    private void cadastrarFranquia() {
        if (mainView != null) {
            mainView.mostrarCadastroFranquia();
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void editarFranquia() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma franquia para editar.");
            return;
        }
        
        int franquiaId = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        if (mainView != null) {
            mainView.mostrarEdicaoFranquia(franquiaId);
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void removerFranquia() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma franquia para remover.");
            return;
        }
        
        String nomeFranquia = (String) tableModel.getValueAt(selectedRow, 1);
        int franquiaId = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "<html>Tem certeza que deseja remover a franquia:<br><b>" + nomeFranquia + "</b>?<br><br>" +
            "Esta ação não pode ser desfeita e removerá todos os dados associados.</html>",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                sistemaController.removerFranquia(franquiaId);
                JOptionPane.showMessageDialog(this, 
                    "Franquia removida com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao remover franquia: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadData() {
        try {
            List<Franquia> franquias = sistemaController.listarFranquias();
            
            tableModel.setRowCount(0);
            
            for (Franquia franquia : franquias) {
                Object[] row = {
                    franquia.getId(),
                    franquia.getNome(),
                    franquia.getEndereco().getCidade(),
                    franquia.getEndereco().getEstado(),
                    franquia.getGerenteId() > 0 ? franquia.getGerenteId() : "N/A",
                    String.format("R$ %.2f", franquia.getReceitaAcumulada()),
                    franquia.getTotalPedidos(),
                    String.format("R$ %.2f", franquia.getTicketMedio())
                };
                tableModel.addRow(row);
            }
            
            if (franquias.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhuma franquia encontrada.", 
                    "Informação", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar franquias: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void atualizarLista() {
        loadData();
    }
}



