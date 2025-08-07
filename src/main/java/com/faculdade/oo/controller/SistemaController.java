/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.controller;

import com.faculdade.oo.dao.*;
import com.faculdade.oo.exceptions.ValidacaoException;
import com.faculdade.oo.model.*;
import com.faculdade.oo.util.ValidadorUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SistemaController {
    
    private UsuarioDAO usuarioDAO;
    private FranquiaDAO franquiaDAO;
    private ProdutoDAO produtoDAO;
    private PedidoDAO pedidoDAO;
    private AutenticacaoController authController;
    
    public SistemaController(AutenticacaoController authController) {
        this.usuarioDAO = new UsuarioDAO();
        this.franquiaDAO = new FranquiaDAO();
        this.produtoDAO = new ProdutoDAO();
        this.pedidoDAO = new PedidoDAO();
        this.authController = authController;
    }
    
    public AutenticacaoController getAuthController() {
        return authController;
    }
    
    // ========== MÉTODOS PARA DONO ==========
    
    public void cadastrarFranquia(String nome, Endereco endereco, int gerenteId) throws Exception {
        verificarPermissaoDono();
        
        ValidadorUtil.validarStringObrigatoria(nome, "nome da franquia");
        
        if (endereco == null) {
            throw new ValidacaoException("Endereço é obrigatório");
        }
        
        Usuario gerente = usuarioDAO.buscarPorId(gerenteId);
        if (gerente == null || !(gerente instanceof Gerente)) {
            throw new ValidacaoException("Gerente não encontrado");
        }
        
        if (franquiaDAO.buscarPorGerente(gerenteId) != null) {
            throw new ValidacaoException("Este gerente já está alocado em outra franquia");
        }
        
        int novoId = franquiaDAO.obterProximoId();
        Franquia franquia = new Franquia(novoId, nome, endereco, gerenteId);
        
        franquiaDAO.salvar(franquia);
        
        ((Gerente) gerente).setFranquiaId(novoId);
        usuarioDAO.atualizar(gerente);
    }
    
    public void removerFranquia(int franquiaId) throws Exception {
        verificarPermissaoDono();
        
        Franquia franquia = franquiaDAO.buscarPorId(franquiaId);
        if (franquia == null) {
            throw new ValidacaoException("Franquia não encontrada");
        }
        
        if (franquia.getGerenteId() > 0) {
            Usuario gerente = usuarioDAO.buscarPorId(franquia.getGerenteId());
            if (gerente instanceof Gerente) {
                ((Gerente) gerente).setFranquiaId(0);
                usuarioDAO.atualizar(gerente);
            }
        }
        
        for (int vendedorId : franquia.getVendedoresIds()) {
            Usuario vendedor = usuarioDAO.buscarPorId(vendedorId);
            if (vendedor instanceof Vendedor) {
                ((Vendedor) vendedor).setFranquiaId(0);
                usuarioDAO.atualizar(vendedor);
            }
        }
        
        franquiaDAO.remover(franquiaId);
    }
    
    public void editarFranquia(int franquiaId, String nome, Endereco endereco, int novoGerenteId) throws Exception {
        verificarPermissaoDono();
        
        ValidadorUtil.validarStringObrigatoria(nome, "nome");
        if (endereco == null) {
            throw new ValidacaoException("Endereço é obrigatório");
        }
        
        Franquia franquia = franquiaDAO.buscarPorId(franquiaId);
        if (franquia == null) {
            throw new ValidacaoException("Franquia não encontrada");
        }
        
        if (franquia.getGerenteId() != novoGerenteId) {
            if (franquia.getGerenteId() > 0) {
                Usuario gerenteAnterior = usuarioDAO.buscarPorId(franquia.getGerenteId());
                if (gerenteAnterior instanceof Gerente) {
                    ((Gerente) gerenteAnterior).setFranquiaId(0);
                    usuarioDAO.atualizar(gerenteAnterior);
                }
            }
            
            if (novoGerenteId > 0) {
                Usuario novoGerente = usuarioDAO.buscarPorId(novoGerenteId);
                if (!(novoGerente instanceof Gerente)) {
                    throw new ValidacaoException("Usuário informado não é um gerente");
                }
                
                Gerente gerente = (Gerente) novoGerente;
                if (gerente.getFranquiaId() > 0 && gerente.getFranquiaId() != franquiaId) {
                    throw new ValidacaoException("Gerente já está associado a outra franquia");
                }
                
                gerente.setFranquiaId(franquiaId);
                usuarioDAO.atualizar(gerente);
            }
        }
        
        franquia.setNome(nome);
        franquia.setEndereco(endereco);
        franquia.setGerenteId(novoGerenteId);
        
        franquiaDAO.atualizar(franquia);
    }
    
    public List<Franquia> listarFranquias() throws Exception {
        verificarPermissaoDono();
        return franquiaDAO.listarTodos();
    }
    
    public Franquia buscarFranquiaGerente() throws Exception {
        verificarPermissaoGerente();
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        return franquiaDAO.buscarPorGerente(gerente.getId());
    }
    
    public void cadastrarGerente(String nome, String cpf, String email, String senha) throws Exception {
        verificarPermissaoDono();
        
        ValidadorUtil.validarStringObrigatoria(nome, "nome");
        ValidadorUtil.validarCPF(cpf);
        ValidadorUtil.validarEmail(email);
        ValidadorUtil.validarSenha(senha);
        
        int novoId = usuarioDAO.obterProximoId();
        Gerente gerente = new Gerente(novoId, nome, cpf, email, senha);
        
        usuarioDAO.salvar(gerente);
    }
    
    public void removerGerente(int gerenteId) throws Exception {
        verificarPermissaoDono();
        
        Usuario usuario = usuarioDAO.buscarPorId(gerenteId);
        if (usuario == null || !(usuario instanceof Gerente)) {
            throw new ValidacaoException("Gerente não encontrado");
        }
        
        Gerente gerente = (Gerente) usuario;
        
        if (gerente.getFranquiaId() > 0) {
            Franquia franquia = franquiaDAO.buscarPorId(gerente.getFranquiaId());
            if (franquia != null) {
                franquia.setGerenteId(0);
                franquiaDAO.atualizar(franquia);
            }
        }
        
        usuarioDAO.remover(gerenteId);
    }
    
    public void editarGerente(int gerenteId, String nome, String email, String novaSenha) throws Exception {
        verificarPermissaoDono();
        
        ValidadorUtil.validarStringObrigatoria(nome, "nome");
        ValidadorUtil.validarEmail(email);
        if (novaSenha != null && !novaSenha.isEmpty()) {
            ValidadorUtil.validarSenha(novaSenha);
        }
        
        Usuario usuario = usuarioDAO.buscarPorId(gerenteId);
        if (usuario == null) {
            throw new ValidacaoException("Gerente não encontrado");
        }
        
        if (!(usuario instanceof Gerente)) {
            throw new ValidacaoException("Usuário não é um gerente");
        }
        
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            if (u.getId() != gerenteId && u.getEmail().equalsIgnoreCase(email)) {
                throw new ValidacaoException("Email já está sendo usado por outro usuário");
            }
        }
        
        Gerente gerente = (Gerente) usuario;
        gerente.setNome(nome);
        gerente.setEmail(email);
        
        if (novaSenha != null && !novaSenha.isEmpty()) {
            gerente.setSenha(novaSenha);
        }
        
        usuarioDAO.atualizar(gerente);
    }
    
    public List<Gerente> listarGerentesSemFranquia() throws Exception {
        verificarPermissaoDono();
        
        return usuarioDAO.buscarPorTipo(TipoUsuario.GERENTE)
                        .stream()
                        .map(u -> (Gerente) u)
                        .filter(g -> g.getFranquiaId() == 0)
                        .collect(Collectors.toList());
    }
    
    public List<Gerente> listarTodosGerentes() throws Exception {
        verificarPermissaoDono();
        
        return usuarioDAO.buscarPorTipo(TipoUsuario.GERENTE)
                        .stream()
                        .map(u -> (Gerente) u)
                        .collect(Collectors.toList());
    }
    
    // ========== MÉTODOS PARA GERENTE ==========
    
    public void cadastrarVendedor(String nome, String cpf, String email, String senha) throws Exception {
        verificarPermissaoGerente();
        
        ValidadorUtil.validarStringObrigatoria(nome, "nome");
        ValidadorUtil.validarCPF(cpf);
        ValidadorUtil.validarEmail(email);
        ValidadorUtil.validarSenha(senha);
        
        Gerente gerenteLogado = (Gerente) authController.getUsuarioLogado();
        
        if (gerenteLogado.getFranquiaId() == 0) {
            throw new ValidacaoException("Gerente não está associado a nenhuma franquia");
        }
        
        int novoId = usuarioDAO.obterProximoId();
        Vendedor vendedor = new Vendedor(novoId, nome, cpf, email, senha, gerenteLogado.getFranquiaId());
        
        usuarioDAO.salvar(vendedor);
        
        Franquia franquia = franquiaDAO.buscarPorId(gerenteLogado.getFranquiaId());
        if (franquia != null) {
            franquia.adicionarVendedor(novoId);
            franquiaDAO.atualizar(franquia);
        }
    }
    
    public void removerVendedor(int vendedorId) throws Exception {
        verificarPermissaoGerente();
        
        Usuario usuario = usuarioDAO.buscarPorId(vendedorId);
        if (usuario == null || !(usuario instanceof Vendedor)) {
            throw new ValidacaoException("Vendedor não encontrado");
        }
        
        Vendedor vendedor = (Vendedor) usuario;
        Gerente gerenteLogado = (Gerente) authController.getUsuarioLogado();
        
        if (vendedor.getFranquiaId() != gerenteLogado.getFranquiaId()) {
            throw new ValidacaoException("Vendedor não pertence à sua franquia");
        }
        
        usuarioDAO.remover(vendedorId);
        
        Franquia franquia = franquiaDAO.buscarPorId(gerenteLogado.getFranquiaId());
        if (franquia != null) {
            franquia.removerVendedor(vendedorId);
            franquiaDAO.atualizar(franquia);
        }
    }
    
    public Vendedor buscarVendedorPorId(int vendedorId) throws Exception {
        verificarPermissaoGerente();
        
        Usuario usuario = usuarioDAO.buscarPorId(vendedorId);
        if (usuario == null || !(usuario instanceof Vendedor)) {
            throw new ValidacaoException("Vendedor não encontrado");
        }
        
        Vendedor vendedor = (Vendedor) usuario;
        Gerente gerenteLogado = (Gerente) authController.getUsuarioLogado();
        
        if (vendedor.getFranquiaId() != gerenteLogado.getFranquiaId()) {
            throw new ValidacaoException("Vendedor não pertence à sua franquia");
        }
        
        return vendedor;
    }
    
    public void editarVendedor(int vendedorId, String nome, String cpf, String email, String novaSenha) throws Exception {
        verificarPermissaoGerente();
        
        Vendedor vendedor = buscarVendedorPorId(vendedorId);
        
        if (nome != null && !nome.trim().isEmpty()) {
            vendedor.setNome(nome.trim());
        }
        
        if (email != null && !email.trim().isEmpty()) {
            // Verificar se o novo email já não está sendo usado por outro usuário
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            for (Usuario u : usuarios) {
                if (u.getId() != vendedorId && u.getEmail().equals(email.trim())) {
                    throw new ValidacaoException("Email já está sendo usado por outro usuário");
                }
            }
            vendedor.setEmail(email.trim());
        }
        
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            vendedor.setSenha(novaSenha.trim());
        }
        
        usuarioDAO.atualizar(vendedor);
    }
    
    public List<Vendedor> listarVendedoresPorVendas() throws Exception {
        verificarPermissaoGerente();
        
        Gerente gerenteLogado = (Gerente) authController.getUsuarioLogado();
        
        return usuarioDAO.buscarPorTipo(TipoUsuario.VENDEDOR)
                        .stream()
                        .map(u -> (Vendedor) u)
                        .filter(v -> v.getFranquiaId() == gerenteLogado.getFranquiaId())
                        .sorted((v1, v2) -> Double.compare(v2.getTotalVendas(), v1.getTotalVendas()))
                        .collect(Collectors.toList());
    }
    
    // ========== MÉTODOS UTILITÁRIOS ==========
    
    private void verificarPermissaoDono() throws ValidacaoException {
        if (!authController.isLogado()) {
            throw new ValidacaoException("Usuário não está logado");
        }
        
        if (!authController.isUsuarioTipo(Dono.class)) {
            throw new ValidacaoException("Acesso negado: apenas Donos podem realizar esta operação");
        }
    }
    
    private void verificarPermissaoGerente() throws ValidacaoException {
        if (!authController.isLogado()) {
            throw new ValidacaoException("Usuário não está logado");
        }
        
        if (!authController.isUsuarioTipo(Gerente.class)) {
            throw new ValidacaoException("Acesso negado: apenas Gerentes podem realizar esta operação");
        }
    }
    
    private void verificarPermissaoVendedor() throws ValidacaoException {
        if (!authController.isLogado()) {
            throw new ValidacaoException("Usuário não está logado");
        }
        
        if (!authController.isUsuarioTipo(Vendedor.class)) {
            throw new ValidacaoException("Acesso negado: apenas Vendedores podem realizar esta operação");
        }
    }
    
    // ========== MÉTODOS DE GESTÃO DE PRODUTOS ==========
    
    public void cadastrarProduto(String nome, String descricao, double preco, int quantidadeEstoque, int estoqueMinimo, int franquiaId) throws Exception {
        verificarPermissaoGerente();
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        Franquia franquia = franquiaDAO.buscarPorId(franquiaId);
        
        if (franquia == null || franquia.getGerenteId() != gerente.getId()) {
            throw new ValidacaoException("Você só pode cadastrar produtos na sua franquia");
        }
        
        ValidadorUtil.validarStringObrigatoria(nome, "Nome do produto");
        ValidadorUtil.validarStringObrigatoria(descricao, "Descrição");
        ValidadorUtil.validarValorPositivo(preco, "Preço");
        ValidadorUtil.validarFaixaInteira(quantidadeEstoque, "Quantidade em estoque", 0, 999999);
        ValidadorUtil.validarFaixaInteira(estoqueMinimo, "Estoque mínimo", 0, 999999);
        
        int proximoId = produtoDAO.obterProximoId();
        Produto produto = new Produto(proximoId, nome, descricao, preco, quantidadeEstoque, franquiaId);
        produto.setEstoqueMinimo(estoqueMinimo);
        
        produtoDAO.salvar(produto);
    }
    
    public void atualizarProduto(int produtoId, String nome, String descricao, double preco, int quantidadeEstoque, int estoqueMinimo) throws Exception {
        verificarPermissaoGerente();
        
        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto == null) {
            throw new ValidacaoException("Produto não encontrado");
        }
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        Franquia franquia = franquiaDAO.buscarPorId(produto.getFranquiaId());
        
        if (franquia == null || franquia.getGerenteId() != gerente.getId()) {
            throw new ValidacaoException("Você só pode atualizar produtos da sua franquia");
        }
        
        ValidadorUtil.validarStringObrigatoria(nome, "Nome do produto");
        ValidadorUtil.validarStringObrigatoria(descricao, "Descrição");
        ValidadorUtil.validarValorPositivo(preco, "Preço");
        ValidadorUtil.validarFaixaInteira(quantidadeEstoque, "Quantidade em estoque", 0, 999999);
        ValidadorUtil.validarFaixaInteira(estoqueMinimo, "Estoque mínimo", 0, 999999);
        
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);
        produto.setEstoqueMinimo(estoqueMinimo);
        
        produtoDAO.atualizar(produto);
    }
    
    public void removerProduto(int produtoId) throws Exception {
        verificarPermissaoGerente();
        
        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto == null) {
            throw new ValidacaoException("Produto não encontrado");
        }
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        Franquia franquia = franquiaDAO.buscarPorId(produto.getFranquiaId());
        
        if (franquia == null || franquia.getGerenteId() != gerente.getId()) {
            throw new ValidacaoException("Você só pode remover produtos da sua franquia");
        }
        
        produtoDAO.remover(produtoId);
    }
    
    public List<Produto> listarProdutosPorFranquia(int franquiaId) throws Exception {
        if (authController.isUsuarioTipo(Gerente.class)) {
            Gerente gerente = (Gerente) authController.getUsuarioLogado();
            Franquia franquia = franquiaDAO.buscarPorId(franquiaId);
            
            if (franquia == null || franquia.getGerenteId() != gerente.getId()) {
                throw new ValidacaoException("Você só pode visualizar produtos da sua franquia");
            }
        } else if (authController.isUsuarioTipo(Vendedor.class)) {
            Vendedor vendedor = (Vendedor) authController.getUsuarioLogado();
            if (vendedor.getFranquiaId() != franquiaId) {
                throw new ValidacaoException("Você só pode visualizar produtos da sua franquia");
            }
        } else if (!authController.isUsuarioTipo(Dono.class)) {
            throw new ValidacaoException("Acesso negado");
        }
        
        return produtoDAO.buscarPorFranquia(franquiaId);
    }
    
    public List<Produto> listarProdutosComEstoqueBaixo() throws Exception {
        verificarPermissaoGerente();
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        Franquia franquia = franquiaDAO.listarTodos().stream()
                .filter(f -> f.getGerenteId() == gerente.getId())
                .findFirst()
                .orElseThrow(() -> new ValidacaoException("Franquia não encontrada"));
        
        return produtoDAO.buscarComEstoqueBaixo(franquia.getId());
    }
    
    // ========== MÉTODOS DE GESTÃO DE PEDIDOS ==========
    
    public int criarPedido(String nomeCliente, String telefoneCliente, String emailCliente, 
                          Endereco enderecoEntrega, FormaPagamento formaPagamento, 
                          ModoEntrega modoEntrega, String observacoes) throws Exception {
        verificarPermissaoVendedor();
        
        Vendedor vendedor = (Vendedor) authController.getUsuarioLogado();
        
        ValidadorUtil.validarStringObrigatoria(nomeCliente, "Nome do cliente");
        ValidadorUtil.validarStringObrigatoria(telefoneCliente, "Telefone do cliente");
        ValidadorUtil.validarEmail(emailCliente);
        
        int proximoId = pedidoDAO.obterProximoId();
        Pedido pedido = new Pedido();
        pedido.setId(proximoId);
        pedido.setVendedorId(vendedor.getId());
        pedido.setFranquiaId(vendedor.getFranquiaId());
        pedido.setNomeCliente(nomeCliente);
        pedido.setTelefoneCliente(telefoneCliente);
        pedido.setEmailCliente(emailCliente);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setModoEntrega(modoEntrega);
        pedido.setObservacoes(observacoes);
        pedido.setStatus(StatusPedido.PENDENTE);
        
        pedidoDAO.salvar(pedido);
        return proximoId;
    }
    
    public void adicionarItemPedido(int pedidoId, int produtoId, int quantidade) throws Exception {
        verificarPermissaoVendedor();
        
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new ValidacaoException("Pedido não encontrado");
        }
        
        Vendedor vendedor = (Vendedor) authController.getUsuarioLogado();
        if (pedido.getVendedorId() != vendedor.getId()) {
            throw new ValidacaoException("Você só pode adicionar itens aos seus pedidos");
        }
        
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new ValidacaoException("Só é possível adicionar itens a pedidos pendentes");
        }
        
        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto == null) {
            throw new ValidacaoException("Produto não encontrado");
        }
        
        if (produto.getFranquiaId() != vendedor.getFranquiaId()) {
            throw new ValidacaoException("Produto não disponível na sua franquia");
        }
        
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new ValidacaoException("Estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque());
        }
        
        ValidadorUtil.validarFaixaInteira(quantidade, "Quantidade", 0, 999999);
        
        ItemPedido item = new ItemPedido();
        item.setProdutoId(produtoId);
        item.setNomeProduto(produto.getNome());
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        
        pedido.adicionarItem(item);
        pedido.calcularTotal();
        
        pedidoDAO.atualizar(pedido);
    }
    
    public void finalizarPedido(int pedidoId) throws Exception {
        verificarPermissaoVendedor();
        
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new ValidacaoException("Pedido não encontrado");
        }
        
        Vendedor vendedor = (Vendedor) authController.getUsuarioLogado();
        if (pedido.getVendedorId() != vendedor.getId()) {
            throw new ValidacaoException("Você só pode finalizar seus pedidos");
        }
        
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new ValidacaoException("Pedido já foi finalizado");
        }
        
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new ValidacaoException("Pedido deve ter pelo menos um item");
        }
        
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoDAO.buscarPorId(item.getProdutoId());
            produto.diminuirEstoque(item.getQuantidade());
            produtoDAO.atualizar(produto);
        }
        
        vendedor.adicionarVenda(pedido.getValorTotal());
        usuarioDAO.atualizar(vendedor);
        
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedidoDAO.atualizar(pedido);
    }
    
    public List<Pedido> listarPedidosVendedor() throws Exception {
        verificarPermissaoVendedor();
        
        Vendedor vendedor = (Vendedor) authController.getUsuarioLogado();
        return pedidoDAO.buscarPorVendedor(vendedor.getId());
    }
    
    public List<Pedido> listarPedidosFranquia(int franquiaId) throws Exception {
        if (authController.isUsuarioTipo(Gerente.class)) {
            verificarPermissaoGerente();
            Gerente gerente = (Gerente) authController.getUsuarioLogado();
            Franquia franquia = franquiaDAO.buscarPorId(franquiaId);
            
            if (franquia == null || franquia.getGerenteId() != gerente.getId()) {
                throw new ValidacaoException("Você só pode visualizar pedidos da sua franquia");
            }
        } else if (!authController.isUsuarioTipo(Dono.class)) {
            throw new ValidacaoException("Acesso negado");
        }
        
        return pedidoDAO.buscarPorFranquia(franquiaId);
    }
    
    public void atualizarStatusPedido(int pedidoId, StatusPedido novoStatus) throws Exception {
        verificarPermissaoGerente();
        
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new ValidacaoException("Pedido não encontrado");
        }
        
        Gerente gerente = (Gerente) authController.getUsuarioLogado();
        if (pedido.getFranquiaId() != gerente.getFranquiaId()) {
            throw new ValidacaoException("Você só pode atualizar pedidos da sua franquia");
        }
        
        pedido.setStatus(novoStatus);
        pedidoDAO.atualizar(pedido);
    }
}


