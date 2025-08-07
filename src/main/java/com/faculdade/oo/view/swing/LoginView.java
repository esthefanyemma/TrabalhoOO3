/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing;

import com.faculdade.oo.controller.AutenticacaoController;
import com.faculdade.oo.exceptions.AutenticacaoException;
import com.faculdade.oo.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JLabel statusLabel;
    private AutenticacaoController authController;
    
    public LoginView() {
        this.authController = new AutenticacaoController();
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        setTitle("Sistema de Gerenciamento de Franquias - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        emailField = new JTextField(25);
        emailField.setPreferredSize(new Dimension(200, 25));
        
        senhaField = new JPasswordField(25);
        senhaField.setPreferredSize(new Dimension(200, 25));
        
        loginButton = new JButton("Entrar");
        
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Sistema de Franquias");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(senhaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        add(createInfoPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(new Color(245, 245, 245));
        
        JLabel titleInfo = new JLabel("Usuários de Teste:");
        titleInfo.setFont(new Font("Arial", Font.BOLD, 11));
        
        JLabel infoLabel = new JLabel("<html>DONO: joao@franquia.com / 123456<br/>" +
                                     "GERENTE: maria@franquia.com / 123456<br/>" +
                                     "VENDEDOR: ana@franquia.com / 123456</html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        infoLabel.setForeground(Color.DARK_GRAY);
        
        JPanel content = new JPanel(new BorderLayout(0, 5));
        content.setBackground(new Color(245, 245, 245));
        content.add(titleInfo, BorderLayout.NORTH);
        content.add(infoLabel, BorderLayout.CENTER);
        
        panel.add(content, BorderLayout.WEST);
        return panel;
    }
    
    private void setupEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        ActionListener enterAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        };
        emailField.addActionListener(enterAction);
        senhaField.addActionListener(enterAction);
    }
    
    private void realizarLogin() {
        String email = emailField.getText().trim();
        String senha = new String(senhaField.getPassword());
        
        if (email.isEmpty() || senha.isEmpty()) {
            mostrarStatus("Por favor, preencha todos os campos", Color.RED);
            return;
        }
        
        try {
            Usuario usuario = authController.autenticar(email, senha);
            mostrarStatus("Login realizado com sucesso!", Color.GREEN);
            
            SwingUtilities.invokeLater(() -> {
                abrirTelaPrincipal(usuario);
                this.dispose();
            });
            
        } catch (AutenticacaoException e) {
            mostrarStatus("Erro: " + e.getMessage(), Color.RED);
            senhaField.setText("");
        }
    }
    
    private void abrirTelaPrincipal(Usuario usuario) {
        MainView mainView = new MainView(authController);
        mainView.setVisible(true);
    }
    
    private void mostrarStatus(String mensagem, Color cor) {
        statusLabel.setText(mensagem);
        statusLabel.setForeground(cor);
        
        Timer timer = new Timer(5000, e -> statusLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}



