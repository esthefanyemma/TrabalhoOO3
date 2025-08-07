/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing.franquia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.Endereco;
import com.faculdade.oo.model.Gerente;

public class CadastroFranquiaDialog extends JDialog {
    
    private SistemaController sistemaController;
    private boolean franquiaCadastrada = false;
    
    private JTextField nomeField;
    private JTextField ruaField;
    private JTextField numeroField;
    private JTextField complementoField;
    private JTextField bairroField;
    private JTextField cidadeField;
    private JTextField estadoField;
    private JTextField cepField;
    private JComboBox<GerenteItem> gerenteCombo;
    
    public CadastroFranquiaDialog(Frame parent, SistemaController sistemaController) {
        super(parent, "Cadastrar Franquia", true);
        this.sistemaController = sistemaController;
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadGerentes();
    }
    
    private void initializeComponents() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        nomeField = new JTextField(20);
        ruaField = new JTextField(20);
        numeroField = new JTextField(10);
        complementoField = new JTextField(20);
        bairroField = new JTextField(20);
        cidadeField = new JTextField(20);
        estadoField = new JTextField(5);
        cepField = new JTextField(10);
        gerenteCombo = new JComboBox<>();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(16, 185, 129),
                    0, getHeight(), new Color(5, 150, 105)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("🏢 Cadastro de Nova Franquia");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        addFormField(mainPanel, gbc, 2, "Nome da Franquia:", nomeField);
        
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(enderecoLabel, gbc);
        
        gbc.gridwidth = 1;
        addFormField(mainPanel, gbc, 4, "Rua:", ruaField);
        addFormField(mainPanel, gbc, 5, "Número:", numeroField);
        addFormField(mainPanel, gbc, 6, "Complemento:", complementoField);
        addFormField(mainPanel, gbc, 7, "Bairro:", bairroField);
        addFormField(mainPanel, gbc, 8, "Cidade:", cidadeField);
        addFormField(mainPanel, gbc, 9, "Estado:", estadoField);
        addFormField(mainPanel, gbc, 10, "CEP:", cepField);
        addFormField(mainPanel, gbc, 11, "Gerente Responsável:", gerenteCombo);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarFranquia());
        buttonPanel.add(salvarButton);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelarButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }
    
    private void setupEventListeners() {
        estadoField.setDocument(new LimitedDocument(2)); 
        cepField.setDocument(new LimitedDocument(9));
        numeroField.setDocument(new LimitedDocument(10));
    }
    
    private void loadGerentes() {
        try {
            List<Gerente> gerentes = sistemaController.listarGerentesSemFranquia();
            
            gerenteCombo.removeAllItems();
            gerenteCombo.addItem(new GerenteItem(null, "Selecione um gerente..."));
            
            for (Gerente gerente : gerentes) {
                gerenteCombo.addItem(new GerenteItem(gerente, 
                    gerente.getNome() + " (ID: " + gerente.getId() + ")"));
            }
            
            if (gerentes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "<html>Não há gerentes disponíveis para alocar a uma franquia.<br>" +
                    "Cadastre um gerente primeiro.</html>",
                    "Nenhum Gerente Disponível",
                    JOptionPane.WARNING_MESSAGE);
                dispose(); 
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar gerentes: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            dispose(); 
        }
    }
    
    private void salvarFranquia() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            String nome = nomeField.getText().trim();
            String rua = ruaField.getText().trim();
            String numero = numeroField.getText().trim();
            String complemento = complementoField.getText().trim();
            String bairro = bairroField.getText().trim();
            String cidade = cidadeField.getText().trim();
            String estado = estadoField.getText().trim().toUpperCase();
            String cep = cepField.getText().trim();
            
            GerenteItem gerenteItem = (GerenteItem) gerenteCombo.getSelectedItem();
            
            if (gerenteItem == null || gerenteItem.getGerente() == null) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um gerente responsável.",
                    "Campo Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Endereco endereco = new Endereco(rua, numero, complemento.isEmpty() ? null : complemento, 
                                           bairro, cidade, estado, cep);
            
            sistemaController.cadastrarFranquia(nome, endereco, gerenteItem.getGerente().getId());
            
            JOptionPane.showMessageDialog(this,
                "Franquia cadastrada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            franquiaCadastrada = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar franquia: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Nome da franquia é obrigatório.");
            nomeField.requestFocus();
            return false;
        }
        
        if (ruaField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Rua é obrigatória.");
            ruaField.requestFocus();
            return false;
        }
        
        if (numeroField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Número é obrigatório.");
            numeroField.requestFocus();
            return false;
        }
        
        if (bairroField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Bairro é obrigatório.");
            bairroField.requestFocus();
            return false;
        }
        
        if (cidadeField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Cidade é obrigatória.");
            cidadeField.requestFocus();
            return false;
        }
        
        if (estadoField.getText().trim().isEmpty()) {
            mostrarErroValidacao("Estado é obrigatório.");
            estadoField.requestFocus();
            return false;
        }
        
        if (cepField.getText().trim().isEmpty()) {
            mostrarErroValidacao("CEP é obrigatório.");
            cepField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void mostrarErroValidacao(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
    }
    
    public boolean isFranquiaCadastrada() {
        return franquiaCadastrada;
    }
    
    // Classe auxiliar para itens do ComboBox
    private static class GerenteItem {
        private Gerente gerente;
        private String displayText;
        
        public GerenteItem(Gerente gerente, String displayText) {
            this.gerente = gerente;
            this.displayText = displayText;
        }
        
        public Gerente getGerente() {
            return gerente;
        }
        
        @Override
        public String toString() {
            return displayText;
        }
    }
    
    // Classe para limitar caracteres em campos de texto
    private static class LimitedDocument extends javax.swing.text.PlainDocument {
        private int limit;
        
        public LimitedDocument(int limit) {
            this.limit = limit;
        }
        
        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) 
                throws javax.swing.text.BadLocationException {
            if (str == null) return;
            
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}


