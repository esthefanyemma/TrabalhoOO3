package com.faculdade.oo.view.swing.gerente;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Gerente;
import com.faculdade.oo.view.swing.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GerenteListPanel extends JPanel {
    
    private SistemaController sistemaController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCadastrar, btnEditar, btnRemover, btnAtualizar;
    private MainView mainView;
    
    public GerenteListPanel(SistemaController sistemaController, MainView mainView) {
        this.sistemaController = sistemaController;
        this.mainView = mainView;
        initializeComponents();
        setupLayout();
        loadData();
    }
    
    public GerenteListPanel(SistemaController sistemaController) {
        this(sistemaController, null);
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Gerenciar Gerentes"));
        
        String[] columnNames = {"ID", "Nome", "Email", "CPF", "Franquia ID", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnCadastrar = new JButton("Cadastrar Gerente");
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
        JLabel lblInfo = new JLabel("Selecione um gerente para editar ou remover");
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
        
        btnCadastrar.addActionListener(e -> cadastrarGerente());
        btnEditar.addActionListener(e -> editarGerente());
        btnRemover.addActionListener(e -> removerGerente());
        btnAtualizar.addActionListener(e -> loadData());
    }
    
    private void cadastrarGerente() {
        if (mainView != null) {
            mainView.mostrarCadastroGerente();
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void editarGerente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um gerente para editar.");
            return;
        }
        
        int gerenteId = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        if (mainView != null) {
            mainView.mostrarEdicaoGerente(gerenteId);
        } else {
            JOptionPane.showMessageDialog(this, "Função disponível apenas através do menu principal.");
        }
    }
    
    private void removerGerente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um gerente para remover.");
            return;
        }
        
        String nomeGerente = (String) tableModel.getValueAt(selectedRow, 1);
        int gerenteId = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "<html>Tem certeza que deseja remover o gerente:<br><b>" + nomeGerente + "</b>?<br><br>" +
            "Esta ação não pode ser desfeita.</html>",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                sistemaController.removerGerente(gerenteId);
                JOptionPane.showMessageDialog(this, 
                    "Gerente removido com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadData(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao remover gerente: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadData() {
        try {
            List<Gerente> gerentes = sistemaController.listarTodosGerentes();
            
            tableModel.setRowCount(0);
            
            for (Gerente gerente : gerentes) {
                Object[] row = {
                    gerente.getId(),
                    gerente.getNome(),
                    gerente.getEmail(),
                    gerente.getCpf(),
                    gerente.getFranquiaId() > 0 ? gerente.getFranquiaId() : "N/A",
                    gerente.getFranquiaId() > 0 ? "Alocado" : "Disponível"
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar gerentes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void atualizarLista() {
        loadData();
    }
}
