package com.faculdade.oo.util;

import com.faculdade.oo.exceptions.*;
import com.faculdade.oo.model.*;
import java.util.regex.Pattern;
import java.time.LocalDateTime;


public class ValidadorUtil {
    // Validação de dados
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
}
