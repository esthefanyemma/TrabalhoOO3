/*
 * Giovana Maieli da Concei��o Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * �AAurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.util;

import com.faculdade.oo.exceptions.*;
import com.faculdade.oo.model.*;
import java.util.regex.Pattern;
import java.time.LocalDateTime;


public class ValidadorUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
        "^\\(?([0-9]{2})\\)?[-. ]?([0-9]{4,5})[-. ]?([0-9]{4})$"
    );
    
    private static final Pattern CEP_PATTERN = Pattern.compile(
        "^[0-9]{5}-?[0-9]{3}$"
    );
    
    private static final Pattern NOME_PATTERN = Pattern.compile(
        "^[A-Za-zÀ-ÿ\\s'-]{2,}$"
    );

    // Valida se uma string não é nula ou vazia
    public static void validarStringObrigatoria(String valor, String campo) throws CampoObrigatorioException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CampoObrigatorioException(campo);
        }
    }

    // Valida string com tamanho mínimo e máximo
    public static void validarTamanhoString(String valor, String campo, int minimo, int maximo) 
            throws TamanhoInvalidoException, CampoObrigatorioException {
        validarStringObrigatoria(valor, campo);
        
        int tamanho = valor.trim().length();
        if (tamanho < minimo || tamanho > maximo) {
            throw new TamanhoInvalidoException(campo, tamanho, minimo, maximo);
        }
    }

    // Valida se um valor numérico está dentro de uma faixa
    public static void validarFaixaNumerica(double valor, String campo, double minimo, double maximo) 
            throws FaixaInvalidaException {
        if (valor < minimo || valor > maximo) {
            throw new FaixaInvalidaException(campo, valor, minimo, maximo);
        }
    }

    // Valida se um valor inteiro está dentro de uma faixa
    public static void validarFaixaInteira(int valor, String campo, int minimo, int maximo) 
            throws FaixaInvalidaException {
        if (valor < minimo || valor > maximo) {
            throw new FaixaInvalidaException(campo, valor, minimo, maximo);
        }
    }

    //Valida se um valor é positivo
    public static void validarValorPositivo(double valor, String campo) throws FaixaInvalidaException {
        if (valor <= 0) {
            throw new FaixaInvalidaException(String.format("Campo '%s' deve ser maior que zero", campo));
        }
    }

    // Valida se um valor é negativo
    public static void validarValorNaoNegativo(double valor, String campo) throws FaixaInvalidaException {
        if (valor < 0) {
            throw new FaixaInvalidaException(String.format("Campo '%s' não pode ser negativo", campo));
        }
    }

    // Valida formato de email
    public static void validarEmail(String email) throws CampoObrigatorioException, FormatoInvalidoException {
        validarStringObrigatoria(email, "email");
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new FormatoInvalidoException("email", "exemplo@dominio.com");
        }
    }

    // Valida CPF usando algoritmo de validação
    public static void validarCPF(String cpf) throws CampoObrigatorioException, FormatoInvalidoException {
        validarStringObrigatoria(cpf, "CPF");
        
        // Remove caracteres não numéricos
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11) {
            throw new FormatoInvalidoException("CPF", "xxx.xxx.xxx-xx");
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new FormatoInvalidoException("CPF inválido: todos os dígitos são iguais");
        }
        
        // Valida dígitos verificadores
        if (!validarDigitosCPF(cpfLimpo)) {
            throw new FormatoInvalidoException("CPF inválido: dígitos verificadores incorretos");
        }
    }

    // Valida formato de telefone
    public static void validarTelefone(String telefone) throws CampoObrigatorioException, FormatoInvalidoException {
        validarStringObrigatoria(telefone, "telefone");
        
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
            throw new FormatoInvalidoException("telefone", "(xx) xxxxx-xxxx ou (xx) xxxx-xxxx");
        }
    }

    // Valida formato de CEP
    public static void validarCEP(String cep) throws CampoObrigatorioException, FormatoInvalidoException {
        validarStringObrigatoria(cep, "CEP");
        
        if (!CEP_PATTERN.matcher(cep).matches()) {
            throw new FormatoInvalidoException("CEP", "xxxxx-xxx");
        }
    }

    // Valida formato de nome (apenas letras, espaços, acentos e alguns caracteres especiais)
    public static void validarNome(String nome) throws CampoObrigatorioException, FormatoInvalidoException, TamanhoInvalidoException {
        validarStringObrigatoria(nome, "nome");
        validarTamanhoString(nome, "nome", 2, 100);
        
        if (!NOME_PATTERN.matcher(nome.trim()).matches()) {
            throw new FormatoInvalidoException("nome", "apenas letras, espaços e acentos");
        }
    }

    // Valida senha
    public static void validarSenha(String senha) throws ValidacaoException {
        validarStringObrigatoria(senha, "senha");
        validarTamanhoString(senha, "senha", 6, 50);
        
        // Verificar se contém pelo menos uma letra e um número
        boolean temLetra = senha.matches(".*[a-zA-Z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        
        if (!temLetra || !temNumero) {
            throw new FormatoInvalidoException("senha deve conter pelo menos uma letra e um número");
        }
    }

    // Valida dados de usuário
    public static void validarUsuario(Usuario usuario) throws ValidacaoException {
        if (usuario == null) {
            throw new CampoObrigatorioException("usuário");
        }
        
        validarNome(usuario.getNome());
        validarEmail(usuario.getEmail());
        
        if (usuario.getCpf() != null && !usuario.getCpf().trim().isEmpty()) {
            validarCPF(usuario.getCpf());
        }
    }

    // Valida dados de endereço
    public static void validarEndereco(Endereco endereco) throws ValidacaoException {
        if (endereco == null) {
            throw new CampoObrigatorioException("endereço");
        }
        
        validarStringObrigatoria(endereco.getRua(), "rua");
        validarTamanhoString(endereco.getRua(), "rua", 5, 200);
        
        validarStringObrigatoria(endereco.getCidade(), "cidade");
        validarNome(endereco.getCidade());
        
        validarStringObrigatoria(endereco.getEstado(), "estado");
        validarTamanhoString(endereco.getEstado(), "estado", 2, 2);
        
        validarCEP(endereco.getCep());
        
        validarStringObrigatoria(endereco.getBairro(), "bairro");
        validarTamanhoString(endereco.getBairro(), "bairro", 2, 50);
    }

    // Valida dados de franquia
    public static void validarFranquia(Franquia franquia) throws ValidacaoException {
        if (franquia == null) {
            throw new CampoObrigatorioException("franquia");
        }
        
        validarStringObrigatoria(franquia.getNome(), "nome da franquia");
        validarTamanhoString(franquia.getNome(), "nome da franquia", 3, 100);
        
        validarEndereco(franquia.getEndereco());
        
        if (franquia.getGerenteId() <= 0) {
            throw new FaixaInvalidaException("ID do gerente deve ser positivo");
        }
    }

    // Valida dados de produto
    public static void validarProduto(Produto produto) throws ValidacaoException {
        if (produto == null) {
            throw new CampoObrigatorioException("produto");
        }
        
        validarStringObrigatoria(produto.getNome(), "nome do produto");
        validarTamanhoString(produto.getNome(), "nome do produto", 2, 100);
        
        validarValorPositivo(produto.getPreco(), "preço");
        validarFaixaNumerica(produto.getPreco(), "preço", 0.01, 99999.99);
        
        if (produto.getDescricao() != null && !produto.getDescricao().trim().isEmpty()) {
            validarTamanhoString(produto.getDescricao(), "descrição", 1, 500);
        }
    }

    // Valida dados de pedido
    public static void validarPedido(Pedido pedido) throws ValidacaoException {
        if (pedido == null) {
            throw new CampoObrigatorioException("pedido");
        }
        
        validarStringObrigatoria(pedido.getNomeCliente(), "nome do cliente");
        validarNome(pedido.getNomeCliente());
        
        if (pedido.getTelefoneCliente() != null && !pedido.getTelefoneCliente().trim().isEmpty()) {
            validarTelefone(pedido.getTelefoneCliente());
        }
        
        if (pedido.getEmailCliente() != null && !pedido.getEmailCliente().trim().isEmpty()) {
            validarEmail(pedido.getEmailCliente());
        }
        
        validarValorPositivo(pedido.getValorTotal(), "valor total");
        
        if (pedido.getDataHora() == null) {
            throw new CampoObrigatorioException("data/hora do pedido");
        }
        
        // Verifica se a data não é muito antiga ou futura
        LocalDateTime agora = LocalDateTime.now();
        if (pedido.getDataHora().isAfter(agora.plusDays(1))) {
            throw new FaixaInvalidaException("Data do pedido não pode ser mais de 1 dia no futuro");
        }
        
        if (pedido.getDataHora().isBefore(agora.minusYears(1))) {
            throw new FaixaInvalidaException("Data do pedido não pode ser mais de 1 ano no passado");
        }
        
        if (pedido.getFormaPagamento() == null) {
            throw new CampoObrigatorioException("forma de pagamento");
        }
        
        if (pedido.getModoEntrega() == null) {
            throw new CampoObrigatorioException("modo de entrega");
        }
        
        if (pedido.getStatus() == null) {
            throw new CampoObrigatorioException("status do pedido");
        }
    }

    // Valida item de pedido
    public static void validarItemPedido(int produtoId, int quantidade, double precoUnitario) throws ValidacaoException {
        if (produtoId <= 0) {
            throw new FaixaInvalidaException("ID do produto deve ser positivo");
        }
        
        validarFaixaInteira(quantidade, "quantidade", 1, 999);
        validarValorPositivo(precoUnitario, "preço unitário");
    }

    // Valida se um pedido pode ter seus status alterados
    public static void validarAlteracaoStatusPedido(StatusPedido statusAtual, StatusPedido novoStatus) 
            throws EstadoInvalidoException {
        if (statusAtual == null || novoStatus == null) {
            throw new EstadoInvalidoException("Status atual e novo status são obrigatórios");
        }
        
        switch (statusAtual) {
            case PENDENTE:
                if (novoStatus != StatusPedido.CONFIRMADO && novoStatus != StatusPedido.CANCELADO) {
                    throw new EstadoInvalidoException("pedido", statusAtual.name(), "alterar para " + novoStatus.name());
                }
                break;
                
            case CONFIRMADO:
                if (novoStatus != StatusPedido.SAIU_PARA_ENTREGA && novoStatus != StatusPedido.CANCELADO) {
                    throw new EstadoInvalidoException("pedido", statusAtual.name(), "alterar para " + novoStatus.name());
                }
                break;
                
            case SAIU_PARA_ENTREGA:
                if (novoStatus != StatusPedido.ENTREGUE) {
                    throw new EstadoInvalidoException("pedido", statusAtual.name(), "alterar para " + novoStatus.name());
                }
                break;
                
            case ENTREGUE:
            case CANCELADO:
                throw new EstadoInvalidoException("pedido", statusAtual.name(), "alterar status");
                
            default:
                throw new EstadoInvalidoException("Status de pedido desconhecido: " + statusAtual);
        }
    }

    // Valida se os dígitos do CPF estão corretos
    private static boolean validarDigitosCPF(String cpf) {
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        return Character.getNumericValue(cpf.charAt(9)) == primeiroDigito &&
               Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }
}


