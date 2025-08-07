package com.faculdade.oo.view.swing.gerente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.faculdade.oo.controller.SistemaController;

public class CadastroGerenteDialog extends JDialog {
    
    private SistemaController sistemaController;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField emailField;
    private JPasswordField senhaField;
    
    public CadastroGerenteDialog(Frame parent, SistemaController sistemaController) {
        super(parent, "Cadastrar Gerente", true);
        this.sistemaController = sistemaController;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        setSize(450, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        nomeField = createStyledTextField(20);
        cpfField = createStyledTextField(20);
        emailField = createStyledTextField(20);
        senhaField = createStyledPasswordField(20);
    }
    
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
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
                    0, 0, new Color(59, 130, 246),
                    0, getHeight(), new Color(37, 99, 235)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("👔 Cadastro de Novo Gerente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        addStyledFormField(mainPanel, gbc, 0, "Nome:", nomeField);
        addStyledFormField(mainPanel, gbc, 1, "CPF:", cpfField);
        addStyledFormField(mainPanel, gbc, 2, "Email:", emailField);
        addStyledFormField(mainPanel, gbc, 3, "Senha:", senhaField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton salvarButton = createStyledButton("💾 Salvar", new Color(34, 197, 94), Color.WHITE);
        salvarButton.addActionListener(e -> salvarGerente());
        buttonPanel.add(salvarButton);
        
        JButton cancelarButton = createStyledButton("❌ Cancelar", new Color(107, 114, 128), Color.WHITE);
        cancelarButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelarButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addStyledFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(55, 65, 81));
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 5, 2, 5);
        panel.add(label, gbc);
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(25, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, bgColor.brighter(),
                    0, getHeight(), bgColor
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2.setColor(textColor);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(120, 35));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void salvarGerente() {
        try {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());
            
            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            sistemaController.cadastrarGerente(nome, cpf, email, senha);
            
            JOptionPane.showMessageDialog(this, "Gerente cadastrado com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar gerente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
