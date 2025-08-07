/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.view.swing;

import com.faculdade.oo.controller.AutenticacaoController;
import com.faculdade.oo.controller.SistemaController;
import com.faculdade.oo.model.*;
import com.faculdade.oo.view.swing.franquia.*;
import com.faculdade.oo.view.swing.gerente.*;
import com.faculdade.oo.view.swing.vendedor.*;
import com.faculdade.oo.view.swing.produto.*;
import com.faculdade.oo.view.swing.pedido.*;
import com.faculdade.oo.view.swing.item.*;



import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainView extends JFrame {
    
    private AutenticacaoController authController;
    private SistemaController sistemaController;
    private JPanel contentPanel;
    private JLabel userInfoLabel;
    
    public MainView(AutenticacaoController authController) {
        this.authController = authController;
        this.sistemaController = new SistemaController(authController);
        
        initializeComponents();
        setupLayout();
        setupMenus();
        updateContent();
    }
    
    private void initializeComponents() {
        setTitle("Sistema de Gerenciamento de Franquias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        contentPanel = new JPanel(new BorderLayout());
        userInfoLabel = new JLabel();
        updateUserInfo();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        topPanel.add(userInfoLabel, BorderLayout.WEST);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
        
        updateContent();
    }
    
    private void setupMenus() {
        JMenuBar menuBar = new JMenuBar();
        
        Usuario usuario = authController.getUsuarioLogado();
        
        if (usuario instanceof Dono) {
            setupMenuDono(menuBar);
        } else if (usuario instanceof Gerente) {
            setupMenuGerente(menuBar);
        } else if (usuario instanceof Vendedor) {
            setupMenuVendedor(menuBar);
        }
        
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        aboutItem.addActionListener(e -> mostrarSobre());
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupMenuDono(JMenuBar menuBar) {
        JMenu franquiasMenu = new JMenu("Franquias");
        
        JMenuItem gerenciarFranquias = new JMenuItem("Gerenciar Franquias");
        gerenciarFranquias.addActionListener(e -> mostrarListaFranquias());
        franquiasMenu.add(gerenciarFranquias);
        
        menuBar.add(franquiasMenu);
        
        JMenu gerentesMenu = new JMenu("Gerentes");
        
        JMenuItem gerenciarGerentes = new JMenuItem("Gerenciar Gerentes");
        gerenciarGerentes.addActionListener(e -> mostrarListaGerentes());
        gerentesMenu.add(gerenciarGerentes);
        
        menuBar.add(gerentesMenu);
        
        JMenu relatoriosMenu = new JMenu("Relatórios");
        
        JMenuItem indicadores = new JMenuItem("Indicadores Gerais");
        indicadores.addActionListener(e -> mostrarIndicadores());
        relatoriosMenu.add(indicadores);
        
        menuBar.add(relatoriosMenu);
    }
    
    private void setupMenuGerente(JMenuBar menuBar) {
        JMenu vendedoresMenu = new JMenu("Vendedores");
        
        JMenuItem listarVendedores = new JMenuItem("Listar Vendedores");
        listarVendedores.addActionListener(e -> mostrarListaVendedores());
        vendedoresMenu.add(listarVendedores);
        
        JMenuItem cadastrarVendedor = new JMenuItem("Cadastrar Vendedor");
        cadastrarVendedor.addActionListener(e -> mostrarCadastroVendedor());
        vendedoresMenu.add(cadastrarVendedor);
        
        JMenuItem rankingVendedores = new JMenuItem("Ranking de Vendas");
        rankingVendedores.addActionListener(e -> mostrarRankingVendedores());
        vendedoresMenu.add(rankingVendedores);
        
        menuBar.add(vendedoresMenu);
        
        JMenu produtosMenu = new JMenu("Produtos");
        
        JMenuItem gerenciarProdutos = new JMenuItem("Gerenciar Produtos");
        gerenciarProdutos.addActionListener(e -> mostrarGerenciarProdutos());
        produtosMenu.add(gerenciarProdutos);
        
        JMenuItem estoqueBaixo = new JMenuItem("Produtos com Estoque Baixo");
        estoqueBaixo.addActionListener(e -> mostrarProdutosEstoqueBaixo());
        produtosMenu.add(estoqueBaixo);
        
        menuBar.add(produtosMenu);
        
        JMenu pedidosMenu = new JMenu("Pedidos");
        
        JMenuItem visualizarPedidos = new JMenuItem("Visualizar Pedidos");
        visualizarPedidos.addActionListener(e -> mostrarPedidosFranquia());
        pedidosMenu.add(visualizarPedidos);
        
        menuBar.add(pedidosMenu);
    }
    
    private void setupMenuVendedor(JMenuBar menuBar) {
        JMenu vendasMenu = new JMenu("Vendas");
        
        JMenuItem novoPedido = new JMenuItem("Novo Pedido");
        novoPedido.addActionListener(e -> mostrarNovoPedido());
        vendasMenu.add(novoPedido);
        
        JMenuItem meusPedidos = new JMenuItem("Meus Pedidos");
        meusPedidos.addActionListener(e -> mostrarMeusPedidos());
        vendasMenu.add(meusPedidos);
        
        menuBar.add(vendasMenu);
    }
    
    private void updateContent() {
        contentPanel.removeAll();
        
        Usuario usuario = authController.getUsuarioLogado();
        
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento de Franquias");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setBorder(null);
        
        StringBuilder info = new StringBuilder();
        info.append("Usuário: ").append(usuario.getNome()).append(" (").append(usuario.getTipo()).append(")\n\n");
        
        if (usuario instanceof Dono) {
            info.append("Como DONO, você pode:\n");
            info.append("• Gerenciar franquias e gerentes\n");
            info.append("• Visualizar relatórios consolidados\n");
            info.append("• Acompanhar desempenho da rede\n");
        } else if (usuario instanceof Gerente) {
            info.append("Como GERENTE, você pode:\n");
            info.append("• Gerenciar vendedores da sua franquia\n");
            info.append("• Controlar estoque e produtos\n");
            info.append("• Visualizar e gerenciar pedidos\n");
        } else if (usuario instanceof Vendedor) {
            info.append("Como VENDEDOR, você pode:\n");
            info.append("• Cadastrar novos pedidos\n");
            info.append("• Visualizar seus pedidos\n");
            info.append("• Gerenciar vendas\n");
        }
        
        info.append("\nUse o menu superior para navegar pelas funcionalidades.");
        infoArea.setText(info.toString());
        
        infoPanel.add(infoArea, BorderLayout.CENTER);
        
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 30));
        welcomePanel.add(spacer, BorderLayout.CENTER);
        welcomePanel.add(infoPanel, BorderLayout.SOUTH);
        
        contentPanel.add(welcomePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void updateUserInfo() {
        Usuario usuario = authController.getUsuarioLogado();
        if (usuario != null) {
            userInfoLabel.setText("Logado como: " + usuario.getNome() + " (" + usuario.getTipo() + ")");
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente fazer logout?",
            "Confirmar Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            authController.logout();
            this.dispose();
            
            SwingUtilities.invokeLater(() -> {
                new LoginView().setVisible(true);
            });
        }
    }
    
    private void mostrarSobre() {
        String mensagem = "<html>Sistema de Gerenciamento de Franquias<br>" +
                         "Versão 1.0<br><br>" +
                         "Trabalho Prático de Orientação a Objetos<br>" +
                         "DCC025 - UFJF<br><br>" +
                         "Desenvolvido com Java Swing</html>";
        
        JOptionPane.showMessageDialog(this, mensagem, "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void mostrarListaFranquias() {
        contentPanel.removeAll();
        contentPanel.add(new FranquiaListPanel(sistemaController, this), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public void mostrarCadastroFranquia() {
        CadastroFranquiaDialog dialog = new CadastroFranquiaDialog(this, sistemaController);
        dialog.setVisible(true);
        if (dialog.isFranquiaCadastrada()) {
            mostrarListaFranquias();
        }
    }
    
    public void mostrarCadastroGerente() {
        CadastroGerenteDialog dialog = new CadastroGerenteDialog(this, sistemaController);
        dialog.setVisible(true);
    }
    
    private void mostrarListaGerentes() {
        contentPanel.removeAll();
        contentPanel.add(new GerenteListPanel(sistemaController, this), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public void mostrarCadastroVendedor() {
        CadastroVendedorDialog dialog = new CadastroVendedorDialog(this, sistemaController);
        dialog.setVisible(true);
    }
    
    public void mostrarEdicaoVendedor(int vendedorId) {
        try {
            Vendedor vendedor = sistemaController.buscarVendedorPorId(vendedorId);
            if (vendedor != null) {
                CadastroVendedorDialog dialog = new CadastroVendedorDialog(this, sistemaController, vendedor);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vendedor não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar vendedor: " + e.getMessage());
        }
    }
    
    private void mostrarListaVendedores() {
        contentPanel.removeAll();
        contentPanel.add(new VendedorListPanel(sistemaController, this), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void mostrarRankingVendedores() {
        contentPanel.removeAll();
        contentPanel.add(new RankingVendedoresPanel(sistemaController), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void mostrarRemocaoFranquia() {
        try {
            List<Franquia> franquias = sistemaController.listarFranquias();
            
            if (franquias.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Não há franquias cadastradas para remover.",
                    "Nenhuma Franquia",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] opcoes = franquias.stream()
                .map(f -> f.getId() + " - " + f.getNome() + " (" + f.getEndereco().getCidade() + ")")
                .toArray(String[]::new);
            
            String selecao = (String) JOptionPane.showInputDialog(
                this,
                "Selecione a franquia que deseja remover:",
                "Remover Franquia",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );
            
            if (selecao != null) {
                int franquiaId = Integer.parseInt(selecao.split(" - ")[0]);
                
                Franquia franquia = franquias.stream()
                    .filter(f -> f.getId() == franquiaId)
                    .findFirst()
                    .orElse(null);
                
                if (franquia != null) {
                    int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja remover a franquia '" + franquia.getNome() + "'?\n" +
                        "Esta ação não pode ser desfeita.",
                        "Confirmar Remoção",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        sistemaController.removerFranquia(franquiaId);
                        JOptionPane.showMessageDialog(this,
                            "Franquia removida com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        mostrarListaFranquias();
                    }
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao remover franquia: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarEdicaoFranquia() {
        try {
            List<Franquia> franquias = sistemaController.listarFranquias();
            
            if (franquias.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Não há franquias cadastradas para editar.",
                    "Nenhuma Franquia",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] opcoes = franquias.stream()
                .map(f -> f.getId() + " - " + f.getNome() + " (" + f.getEndereco().getCidade() + ")")
                .toArray(String[]::new);
            
            String selecao = (String) JOptionPane.showInputDialog(
                this,
                "Selecione a franquia para editar:",
                "Editar Franquia",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );
            
            if (selecao != null) {
                int franquiaId = Integer.parseInt(selecao.split(" - ")[0]);
                mostrarEdicaoFranquia(franquiaId);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao listar franquias: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarEdicaoFranquia(int franquiaId) {
        try {
            List<Franquia> franquias = sistemaController.listarFranquias();
            Franquia franquia = franquias.stream()
                .filter(f -> f.getId() == franquiaId)
                .findFirst()
                .orElse(null);
                
            if (franquia != null) {
                EdicaoFranquiaDialog dialog = new EdicaoFranquiaDialog(this, sistemaController, franquia);
                dialog.setVisible(true);
                
                if (dialog.isFranquiaEditada()) {
                    mostrarListaFranquias();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Franquia não encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar franquia: " + e.getMessage());
        }
    }
    
    private void mostrarEdicaoGerente() {
        try {
            List<Gerente> gerentes = sistemaController.listarTodosGerentes();
            
            if (gerentes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Não há gerentes cadastrados para editar.",
                    "Nenhum Gerente",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] opcoes = gerentes.stream()
                .map(g -> g.getId() + " - " + g.getNome() + " (" + g.getEmail() + ")")
                .toArray(String[]::new);
            
            String selecao = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o gerente que deseja editar:",
                "Editar Gerente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );
            
            if (selecao != null) {
                int gerenteId = Integer.parseInt(selecao.split(" - ")[0]);
                mostrarEdicaoGerente(gerenteId);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao listar gerentes: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarEdicaoGerente(int gerenteId) {
        try {
            List<Gerente> gerentes = sistemaController.listarTodosGerentes();
            Gerente gerente = gerentes.stream()
                .filter(g -> g.getId() == gerenteId)
                .findFirst()
                .orElse(null);
                
            if (gerente != null) {
                EdicaoGerenteDialog dialog = new EdicaoGerenteDialog(this, sistemaController, gerente);
                dialog.setVisible(true);
                
                if (dialog.isGerenteEditado()) {
                    mostrarListaGerentes();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gerente não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar gerente: " + e.getMessage());
        }
    }
    
    private void mostrarRemocaoGerente() {
        try {
            List<Gerente> gerentes = sistemaController.listarTodosGerentes();
            
            if (gerentes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Não há gerentes cadastrados para remover.",
                    "Nenhum Gerente",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] opcoes = gerentes.stream()
                .map(g -> g.getId() + " - " + g.getNome() + " (" + g.getEmail() + ")")
                .toArray(String[]::new);
            
            String selecao = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o gerente que deseja remover:",
                "Remover Gerente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );
            
            if (selecao != null) {
                int gerenteId = Integer.parseInt(selecao.split(" - ")[0]);
                
                Gerente gerente = gerentes.stream()
                    .filter(g -> g.getId() == gerenteId)
                    .findFirst()
                    .orElse(null);
                
                if (gerente != null) {
                    if (gerente.getFranquiaId() > 0) {
                        JOptionPane.showMessageDialog(this,
                            "Não é possível remover um gerente que possui franquia associada.\n" +
                            "Remova ou transfira a franquia primeiro.",
                            "Gerente com Franquia",
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja remover o gerente '" + gerente.getNome() + "'?\n" +
                        "Esta ação não pode ser desfeita.",
                        "Confirmar Remoção",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        sistemaController.removerGerente(gerenteId);
                        JOptionPane.showMessageDialog(this,
                            "Gerente removido com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        mostrarListaGerentes();
                    }
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao remover gerente: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarIndicadores() {
        try {
            List<Franquia> franquias = sistemaController.listarFranquias();
            List<Gerente> gerentes = sistemaController.listarTodosGerentes();
            
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("=== RELATÓRIO DE INDICADORES GERAIS ===\n\n");
            
            relatorio.append("RESUMO GERAL:\n");
            relatorio.append("• Total de Franquias: ").append(franquias.size()).append("\n");
            relatorio.append("• Total de Gerentes: ").append(gerentes.size()).append("\n");
            relatorio.append("• Gerentes Disponíveis: ").append(gerentes.stream()
                .mapToInt(g -> g.getFranquiaId() == 0 ? 1 : 0).sum()).append("\n\n");
            
            if (!franquias.isEmpty()) {
                double receitaTotal = franquias.stream()
                    .mapToDouble(Franquia::getReceitaAcumulada)
                    .sum();
                
                int totalPedidos = franquias.stream()
                    .mapToInt(Franquia::getTotalPedidos)
                    .sum();
                
                double ticketMedio = totalPedidos > 0 ? receitaTotal / totalPedidos : 0;
                
                relatorio.append("INDICADORES FINANCEIROS:\n");
                relatorio.append("• Receita Total da Rede: R$ ").append(String.format("%.2f", receitaTotal)).append("\n");
                relatorio.append("• Total de Pedidos: ").append(totalPedidos).append("\n");
                relatorio.append("• Ticket Médio da Rede: R$ ").append(String.format("%.2f", ticketMedio)).append("\n\n");
                
                relatorio.append("TOP 3 FRANQUIAS POR RECEITA:\n");
                franquias.stream()
                    .sorted((f1, f2) -> Double.compare(f2.getReceitaAcumulada(), f1.getReceitaAcumulada()))
                    .limit(3)
                    .forEach(f -> relatorio.append("• ").append(f.getNome())
                        .append(": R$ ").append(String.format("%.2f", f.getReceitaAcumulada()))
                        .append(" (").append(f.getTotalPedidos()).append(" pedidos)\n"));
            }
            
            JTextArea textArea = new JTextArea(relatorio.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Indicadores Gerais", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
        
    private void mostrarGerenciarProdutos() {
        contentPanel.removeAll();
        contentPanel.add(new GerenciarProdutosPanel(sistemaController), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void mostrarProdutosEstoqueBaixo() {
        try {
            List<Produto> produtosEstoqueBaixo = sistemaController.listarProdutosComEstoqueBaixo();
            
            if (produtosEstoqueBaixo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nenhum produto com estoque baixo encontrado.",
                    "Estoque OK",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder mensagem = new StringBuilder("<html>Produtos com estoque baixo:<br><br>");
            for (Produto produto : produtosEstoqueBaixo) {
                mensagem.append(String.format("• %s - Estoque: %d (Mínimo: %d)<br>",
                    produto.getNome(), produto.getQuantidadeEstoque(), produto.getEstoqueMinimo()));
            }
            mensagem.append("</html>");
            
            JOptionPane.showMessageDialog(this,
                mensagem.toString(),
                "Produtos com Estoque Baixo",
                JOptionPane.WARNING_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao verificar estoque: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarPedidosFranquia() {
        contentPanel.removeAll();
        contentPanel.add(new PedidoListPanel(sistemaController), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void mostrarNovoPedido() {
        NovoPedidoDialog dialog = new NovoPedidoDialog(this, sistemaController);
        dialog.setVisible(true);
    }
    
    private void mostrarMeusPedidos() {
        contentPanel.removeAll();
        contentPanel.add(new MeusPedidosPanel(sistemaController), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}



