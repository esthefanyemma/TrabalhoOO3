package com.faculdade.oo.util;

import com.faculdade.oo.dao.*;
import com.faculdade.oo.model.*;

public class InicializadorDados {
    
    private UsuarioDAO usuarioDAO;
    private FranquiaDAO franquiaDAO;
    private ProdutoDAO produtoDAO;
    private PedidoDAO pedidoDAO;
    
    public InicializadorDados() {
        this.usuarioDAO = new UsuarioDAO();
        this.franquiaDAO = new FranquiaDAO();
        this.produtoDAO = new ProdutoDAO();
        this.pedidoDAO = new PedidoDAO();
    }
    
    public void criarDadosExemplo() {
        try {
            // Verificar se já existem dados
            if (!usuarioDAO.listarTodos().isEmpty()) {
                return; // Já existem dados
            }
            
            System.out.println("Criando dados de exemplo...");
            
            // Criar Dono
            Dono dono = new Dono(1, "João Silva", "123.456.789-00", "joao@franquia.com", "123456");
            usuarioDAO.salvar(dono);
            
            // Criar Gerentes
            Gerente gerente1 = new Gerente(2, "Maria Santos", "987.654.321-00", "maria@franquia.com", "123456");
            Gerente gerente2 = new Gerente(3, "Pedro Costa", "111.222.333-44", "pedro@franquia.com", "123456");
            usuarioDAO.salvar(gerente1);
            usuarioDAO.salvar(gerente2);
            
            // Criar Endereços
            Endereco endereco1 = new Endereco("Rua das Flores", "100", "Centro", "Juiz de Fora", "MG", "36010-000");
            Endereco endereco2 = new Endereco("Av. Brasil", "500", "Bairro Industrial", "Juiz de Fora", "MG", "36020-000");
            
            // Criar Franquias
            Franquia franquia1 = new Franquia(1, "Franquia Centro", endereco1, 2);
            franquia1.setReceitaAcumulada(15000.0);
            franquia1.setTotalPedidos(150);
            
            Franquia franquia2 = new Franquia(2, "Franquia Industrial", endereco2, 3);
            franquia2.setReceitaAcumulada(12000.0);
            franquia2.setTotalPedidos(120);
            
            franquiaDAO.salvar(franquia1);
            franquiaDAO.salvar(franquia2);
            
            // Atualizar gerentes com franquias
            gerente1.setFranquiaId(1);
            gerente2.setFranquiaId(2);
            usuarioDAO.atualizar(gerente1);
            usuarioDAO.atualizar(gerente2);
            
            // Criar Vendedores
            Vendedor vendedor1 = new Vendedor(4, "Ana Oliveira", "555.666.777-88", "ana@franquia.com", "123456", 1);
            vendedor1.setTotalVendas(5000.0);
            vendedor1.setQuantidadeVendas(50);
            
            Vendedor vendedor2 = new Vendedor(5, "Carlos Lima", "999.888.777-66", "carlos@franquia.com", "123456", 1);
            vendedor2.setTotalVendas(7000.0);
            vendedor2.setQuantidadeVendas(70);
            
            Vendedor vendedor3 = new Vendedor(6, "Lucia Rocha", "444.333.222-11", "lucia@franquia.com", "123456", 2);
            vendedor3.setTotalVendas(6000.0);
            vendedor3.setQuantidadeVendas(60);
            
            usuarioDAO.salvar(vendedor1);
            usuarioDAO.salvar(vendedor2);
            usuarioDAO.salvar(vendedor3);
            
            // Atualizar franquias com vendedores
            franquia1.adicionarVendedor(4);
            franquia1.adicionarVendedor(5);
            franquia2.adicionarVendedor(6);
            
            franquiaDAO.atualizar(franquia1);
            franquiaDAO.atualizar(franquia2);
            
            System.out.println("Dados de exemplo criados com sucesso!");
            System.out.println("\\n=== USUÁRIOS DE TESTE ===");
            System.out.println("DONO: joao@franquia.com / 123456");
            System.out.println("GERENTE 1: maria@franquia.com / 123456");
            System.out.println("GERENTE 2: pedro@franquia.com / 123456");
            System.out.println("VENDEDOR 1: ana@franquia.com / 123456");
            System.out.println("VENDEDOR 2: carlos@franquia.com / 123456");
            System.out.println("VENDEDOR 3: lucia@franquia.com / 123456");
            System.out.println("========================");
            
            // Criar produtos de exemplo
            criarProdutosExemplo();
            
            // Criar pedidos de exemplo
            criarPedidosExemplo();
            
        } catch (Exception e) {
            System.err.println("Erro ao criar dados de exemplo: " + e.getMessage());
        }
    }
    
    private void criarProdutosExemplo() throws Exception {
        System.out.println("Criando produtos de exemplo...");
        
        // Produtos para Franquia 1
        Produto produto1 = new Produto(1, "Notebook Dell Inspiron", "Notebook para uso profissional", 2500.0, 15, 1);
        produto1.setEstoqueMinimo(5);
        produtoDAO.salvar(produto1);
        
        Produto produto2 = new Produto(2, "Mouse Wireless Logitech", "Mouse sem fio ergonômico", 80.0, 50, 1);
        produto2.setEstoqueMinimo(10);
        produtoDAO.salvar(produto2);
        
        Produto produto3 = new Produto(3, "Teclado Mecânico Gamer", "Teclado com switches mecânicos", 350.0, 25, 1);
        produto3.setEstoqueMinimo(5);
        produtoDAO.salvar(produto3);
        
        Produto produto4 = new Produto(4, "Monitor 24 Samsung", "Monitor Full HD 24 polegadas", 800.0, 20, 1);
        produto4.setEstoqueMinimo(3);
        produtoDAO.salvar(produto4);
        
        Produto produto5 = new Produto(5, "Impressora HP LaserJet", "Impressora laser multifuncional", 1200.0, 8, 1);
        produto5.setEstoqueMinimo(2);
        produtoDAO.salvar(produto5);
        
        // Produtos para Franquia 2  
        Produto produto6 = new Produto(6, "Smartphone Samsung Galaxy", "Smartphone Android avançado", 1800.0, 30, 2);
        produto6.setEstoqueMinimo(5);
        produtoDAO.salvar(produto6);
        
        Produto produto7 = new Produto(7, "Fone Bluetooth JBL", "Fone de ouvido sem fio premium", 200.0, 40, 2);
        produto7.setEstoqueMinimo(8);
        produtoDAO.salvar(produto7);
        
        Produto produto8 = new Produto(8, "Tablet iPad Air", "Tablet para produtividade", 2200.0, 12, 2);
        produto8.setEstoqueMinimo(3);
        produtoDAO.salvar(produto8);
        
        Produto produto9 = new Produto(9, "Carregador Portátil Anker", "Power bank 10000mAh", 120.0, 60, 2);
        produto9.setEstoqueMinimo(10);
        produtoDAO.salvar(produto9);
        
        Produto produto10 = new Produto(10, "Cabo USB-C Premium", "Cabo de alta velocidade 2m", 45.0, 100, 2);
        produto10.setEstoqueMinimo(15);
        produtoDAO.salvar(produto10);
        
        System.out.println("Produtos de exemplo criados!");
    }
    
    private void criarPedidosExemplo() throws Exception {
        System.out.println("Criando pedidos de exemplo...");
        
        // Pedido 1 - Franquia 1
        Pedido pedido1 = new Pedido(1, "João Silva Santos", 4, 1);
        pedido1.setEmailCliente("joao.santos@email.com");
        pedido1.setTelefoneCliente("11999887766");
        pedido1.setEnderecoEntrega(new Endereco("Rua das Flores", "123", "", "Centro", "São Paulo", "SP", "01234567"));
        pedido1.adicionarItem(new ItemPedido(1, "Notebook Dell Inspiron", 2500.0, 5)); // 5 Notebooks
        pedido1.adicionarItem(new ItemPedido(2, "Mouse Wireless Logitech", 80.0, 2));   // 2 Mouses
        pedido1.setStatus(StatusPedido.PENDENTE);
        pedidoDAO.salvar(pedido1);
        
        // Pedido 2 - Franquia 1  
        Pedido pedido2 = new Pedido(2, "Maria Oliveira Costa", 4, 1);
        pedido2.setEmailCliente("maria.costa@email.com");
        pedido2.setTelefoneCliente("11888776655");
        pedido2.setEnderecoEntrega(new Endereco("Av. Paulista", "1000", "", "Bela Vista", "São Paulo", "SP", "01310100"));
        pedido2.adicionarItem(new ItemPedido(3, "Teclado Mecânico Gamer", 350.0, 2)); // 2 Teclados
        pedido2.adicionarItem(new ItemPedido(4, "Monitor 24 Samsung", 800.0, 1)); // 1 Monitor
        pedido2.setStatus(StatusPedido.CONFIRMADO);
        pedidoDAO.salvar(pedido2);
        
        // Pedido 3 - Franquia 2
        Pedido pedido3 = new Pedido(3, "Pedro Santos Lima", 6, 2);
        pedido3.setEmailCliente("pedro.lima@email.com");
        pedido3.setTelefoneCliente("11777665544");
        pedido3.setEnderecoEntrega(new Endereco("Rua Augusta", "500", "", "Consolação", "São Paulo", "SP", "01305000"));
        pedido3.adicionarItem(new ItemPedido(6, "Smartphone Samsung Galaxy", 1800.0, 2)); // 2 Smartphones
        pedido3.adicionarItem(new ItemPedido(7, "Fone Bluetooth JBL", 200.0, 1));  // 1 Fone
        pedido3.setStatus(StatusPedido.ENTREGUE);
        pedidoDAO.salvar(pedido3);
        
        // Pedido 4 - Franquia 1 (mais recente)
        Pedido pedido4 = new Pedido(4, "Ana Carolina Silva", 5, 1);
        pedido4.setEmailCliente("ana.silva@email.com");
        pedido4.setTelefoneCliente("11666554433");
        pedido4.setEnderecoEntrega(new Endereco("Rua XV de Novembro", "789", "", "Centro", "Campinas", "SP", "13010000"));
        pedido4.adicionarItem(new ItemPedido(5, "Impressora HP LaserJet", 1200.0, 1)); // 1 Impressora
        pedido4.adicionarItem(new ItemPedido(2, "Mouse Wireless Logitech", 80.0, 3));   // 3 Mouses
        pedido4.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
        pedidoDAO.salvar(pedido4);
        
        // Pedido 5 - Franquia 2 (cancelado)
        Pedido pedido5 = new Pedido(5, "Roberto Santos", 6, 2);
        pedido5.setEmailCliente("roberto@email.com");
        pedido5.setTelefoneCliente("11555443322");
        pedido5.setEnderecoEntrega(new Endereco("Av. Faria Lima", "2000", "", "Itaim Bibi", "São Paulo", "SP", "04538000"));
        pedido5.adicionarItem(new ItemPedido(8, "Tablet iPad Air", 2200.0, 1)); // 1 Tablet
        pedido5.setStatus(StatusPedido.CANCELADO);
        pedidoDAO.salvar(pedido5);
        
        System.out.println("Pedidos de exemplo criados!");
    }
}