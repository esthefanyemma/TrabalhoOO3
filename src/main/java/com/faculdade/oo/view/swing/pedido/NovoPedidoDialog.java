package com.faculdade.oo.view.swing.pedido;

import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.*;

import javax.swing.*;
import java.awt.*;

public class NovoPedidoDialog extends JDialog {
    
    private SistemaController sistemaController;
    private boolean pedidoCriado = false;
    private int pedidoId = 0;
    
    private JTextField txtNomeCliente, txtTelefoneCliente, txtEmailCliente;
    private JTextField txtRua, txtNumero, txtCidade, txtEstado, txtCep;
    private JComboBox<FormaPagamento> cbFormaPagamento;
    private JComboBox<ModoEntrega> cbModoEntrega;
    private JTextArea txtObservacoes;
    private JButton btnCriar, btnCancelar;
    
    public NovoPedidoDialog(JFrame parent, SistemaController sistemaController) {
        super(parent, "Novo Pedido", true);
        this.sistemaController = sistemaController;
        
        initializeComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initializeComponents() {
        txtNomeCliente = new JTextField(30);
        txtNomeCliente.setPreferredSize(new Dimension(250, 25));
        
        txtTelefoneCliente = new JTextField(20);
        txtTelefoneCliente.setPreferredSize(new Dimension(200, 25));
        
        txtEmailCliente = new JTextField(30);
        txtEmailCliente.setPreferredSize(new Dimension(250, 25));
        
        txtRua = new JTextField(35);
        txtRua.setPreferredSize(new Dimension(300, 25));
        
        txtNumero = new JTextField(10);
        txtNumero.setPreferredSize(new Dimension(100, 25));
        
        txtCidade = new JTextField(25);
        txtCidade.setPreferredSize(new Dimension(200, 25));
        
        txtEstado = new JTextField(15);
        txtEstado.setPreferredSize(new Dimension(120, 25));
        
        txtCep = new JTextField(15);
        txtCep.setPreferredSize(new Dimension(120, 25));
        
        cbFormaPagamento = new JComboBox<>(FormaPagamento.values());
        cbFormaPagamento.setPreferredSize(new Dimension(200, 25));
        
        cbModoEntrega = new JComboBox<>(ModoEntrega.values());
        cbModoEntrega.setPreferredSize(new Dimension(200, 25));
        
        txtObservacoes = new JTextArea(4, 40);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        
        btnCriar = new JButton("Criar Pedido");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        
        JLabel titleLabel = new JLabel("Novo Pedido");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        addFormField(mainPanel, gbc, 1, "Nome Cliente:*", txtNomeCliente);
        addFormField(mainPanel, gbc, 2, "Telefone:*", txtTelefoneCliente);
        addFormField(mainPanel, gbc, 3, "Email:*", txtEmailCliente);
        
        addFormField(mainPanel, gbc, 4, "Rua:*", txtRua);
        addFormField(mainPanel, gbc, 5, "Número:*", txtNumero);
        addFormField(mainPanel, gbc, 6, "Cidade:*", txtCidade);
        addFormField(mainPanel, gbc, 7, "Estado:*", txtEstado);
        addFormField(mainPanel, gbc, 8, "CEP:*", txtCep);
        
        addFormField(mainPanel, gbc, 9, "Pagamento:*", cbFormaPagamento);
        addFormField(mainPanel, gbc, 10, "Entrega:*", cbModoEntrega);
        
        gbc.gridx = 0; gbc.gridy = 11;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(txtObservacoes), gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnCriar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel panelLegenda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLegenda.add(new JLabel("* Campos obrigatórios"));
        add(panelLegenda, BorderLayout.NORTH);
        
        setSize(600, 500);
        setLocationRelativeTo(getParent());
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.3;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }
    
    private void setupListeners() {
        btnCriar.addActionListener(e -> criarPedido());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void criarPedido() {
        try {
            String nomeCliente = txtNomeCliente.getText().trim();
            String telefoneCliente = txtTelefoneCliente.getText().trim();
            String emailCliente = txtEmailCliente.getText().trim();
            String rua = txtRua.getText().trim();
            String numero = txtNumero.getText().trim();
            String cidade = txtCidade.getText().trim();
            String estado = txtEstado.getText().trim();
            String cep = txtCep.getText().trim();
            String observacoes = txtObservacoes.getText().trim();
            
            FormaPagamento formaPagamento = (FormaPagamento) cbFormaPagamento.getSelectedItem();
            ModoEntrega modoEntrega = (ModoEntrega) cbModoEntrega.getSelectedItem();
            
            if (nomeCliente.isEmpty() || telefoneCliente.isEmpty() || emailCliente.isEmpty() ||
                rua.isEmpty() || numero.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos.");
                return;
            }
            
            Endereco endereco = new Endereco(rua, numero, "", cidade, estado, cep);
            
            pedidoId = sistemaController.criarPedido(nomeCliente, telefoneCliente, emailCliente, 
                                                     endereco, formaPagamento, modoEntrega, observacoes);
            
            JOptionPane.showMessageDialog(this, "Pedido criado com sucesso! ID: " + pedidoId);
            
            pedidoCriado = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar pedido: " + e.getMessage());
        }
    }
    
    public boolean isPedidoCriado() {
        return pedidoCriado;
    }
    
    public int getPedidoId() {
        return pedidoId;
    }
}
