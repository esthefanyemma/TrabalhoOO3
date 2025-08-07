/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Testes da classe Endereco")
class EnderecoTest {
    
    private Endereco endereco;
    
    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua das Flores", "123", "Apto 45", "Centro", "São Paulo", "SP", "01234567");
    }
    
    @Nested
    @DisplayName("Testes de construtor")
    class ConstrutorTest {
        
        @Test
        @DisplayName("Deve criar endereço com construtor completo")
        void testConstrutorCompleto_DeveCriarEndereco() {
            Endereco end = new Endereco("Av. Paulista", "1000", "Conj 101", "Bela Vista", "São Paulo", "SP", "01310100");
            
            assertEquals("Av. Paulista", end.getRua());
            assertEquals("1000", end.getNumero());
            assertEquals("Conj 101", end.getComplemento());
            assertEquals("Bela Vista", end.getBairro());
            assertEquals("São Paulo", end.getCidade());
            assertEquals("SP", end.getEstado());
            assertEquals("01310100", end.getCep());
        }
        
        @Test
        @DisplayName("Deve criar endereço com construtor sem complemento")
        void testConstrutorSemComplemento_DeveCriarEndereco() {
            Endereco end = new Endereco("Rua Principal", "456", "Centro", "Rio de Janeiro", "RJ", "20040020");
            
            assertEquals("Rua Principal", end.getRua());
            assertEquals("456", end.getNumero());
            assertEquals("Centro", end.getBairro());
            assertEquals("Rio de Janeiro", end.getCidade());
            assertEquals("RJ", end.getEstado());
            assertEquals("20040020", end.getCep());
            assertNull(end.getComplemento());
        }
        
        @Test
        @DisplayName("Deve criar endereço com construtor vazio")
        void testConstrutorVazio_DeveCriarEnderecoVazio() {
            Endereco enderecoVazio = new Endereco();
            
            assertNull(enderecoVazio.getRua());
            assertNull(enderecoVazio.getNumero());
            assertNull(enderecoVazio.getComplemento());
            assertNull(enderecoVazio.getBairro());
            assertNull(enderecoVazio.getCidade());
            assertNull(enderecoVazio.getEstado());
            assertNull(enderecoVazio.getCep());
            assertNull(enderecoVazio.getTipo());
        }
    }
    
    @Nested
    @DisplayName("Testes de getters e setters")
    class GettersSettersTest {
        
        @Test
        @DisplayName("Deve permitir alteração de rua")
        void testSetRua_DeveAlterarRua() {
            endereco.setRua("Rua Nova");
            
            assertEquals("Rua Nova", endereco.getRua());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de número")
        void testSetNumero_DeveAlterarNumero() {
            endereco.setNumero("456");
            
            assertEquals("456", endereco.getNumero());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de complemento")
        void testSetComplemento_DeveAlterarComplemento() {
            endereco.setComplemento("Casa 2");
            
            assertEquals("Casa 2", endereco.getComplemento());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de bairro")
        void testSetBairro_DeveAlterarBairro() {
            endereco.setBairro("Vila Nova");
            
            assertEquals("Vila Nova", endereco.getBairro());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de cidade")
        void testSetCidade_DeveAlterarCidade() {
            endereco.setCidade("Rio de Janeiro");
            
            assertEquals("Rio de Janeiro", endereco.getCidade());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de estado")
        void testSetEstado_DeveAlterarEstado() {
            endereco.setEstado("RJ");
            
            assertEquals("RJ", endereco.getEstado());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de CEP")
        void testSetCep_DeveAlterarCep() {
            endereco.setCep("20040020");
            
            assertEquals("20040020", endereco.getCep());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de tipo")
        void testSetTipo_DeveAlterarTipo() {
            endereco.setTipo(TipoEndereco.COMERCIAL);
            
            assertEquals(TipoEndereco.COMERCIAL, endereco.getTipo());
        }
        
        @Test
        @DisplayName("Deve aceitar valores nulos")
        void testSetters_ComValoresNulos() {
            assertDoesNotThrow(() -> {
                endereco.setRua(null);
                endereco.setNumero(null);
                endereco.setComplemento(null);
                endereco.setBairro(null);
                endereco.setCidade(null);
                endereco.setEstado(null);
                endereco.setCep(null);
                endereco.setTipo(null);
            });
            
            assertNull(endereco.getRua());
            assertNull(endereco.getNumero());
            assertNull(endereco.getComplemento());
            assertNull(endereco.getBairro());
            assertNull(endereco.getCidade());
            assertNull(endereco.getEstado());
            assertNull(endereco.getCep());
            assertNull(endereco.getTipo());
        }
    }
    
    @Nested
    @DisplayName("Testes de comportamentos especiais")
    class ComportamentosEspeciais {
        
        @Test
        @DisplayName("Deve implementar toString com todas as informações")
        void testToString_DeveConterTodasInformacoes() {
            String resultado = endereco.toString();
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
            
           assertEquals("Rua das Flores", endereco.getRua());
            assertEquals("123", endereco.getNumero());
            assertEquals("Apto 45", endereco.getComplemento());
            assertEquals("Centro", endereco.getBairro());
            assertEquals("São Paulo", endereco.getCidade());
            assertEquals("SP", endereco.getEstado());
            assertEquals("01234567", endereco.getCep());
        }
        
        @Test
        @DisplayName("Deve implementar toString sem complemento")
        void testToString_SemComplemento() {
            Endereco enderecoSemComplemento = new Endereco("Rua Principal", "100", "Centro", "Cidade", "UF", "12345678");
            
            String resultado = enderecoSemComplemento.toString();
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
            
            assertNull(enderecoSemComplemento.getComplemento());
            
           assertEquals("Rua Principal", enderecoSemComplemento.getRua());
            assertEquals("100", enderecoSemComplemento.getNumero());
            assertEquals("Centro", enderecoSemComplemento.getBairro());
            assertEquals("Cidade", enderecoSemComplemento.getCidade());
            assertEquals("UF", enderecoSemComplemento.getEstado());
            assertEquals("12345678", enderecoSemComplemento.getCep());
        }
        
        @Test
        @DisplayName("Deve tratar toString com campos nulos")
        void testToString_ComCamposNulos() {
            Endereco enderecoComNulos = new Endereco(null, null, null, null, null, null, null);
            
            assertDoesNotThrow(() -> enderecoComNulos.toString());
            
            String resultado = enderecoComNulos.toString();
            assertNotNull(resultado);
        }
        
        @Test
        @DisplayName("Deve aceitar strings vazias")
        void testSetters_ComStringsVazias() {
            endereco.setRua("");
            endereco.setNumero("");
            endereco.setComplemento("");
            endereco.setBairro("");
            endereco.setCidade("");
            endereco.setEstado("");
            endereco.setCep("");
            
            assertEquals("", endereco.getRua());
            assertEquals("", endereco.getNumero());
            assertEquals("", endereco.getComplemento());
            assertEquals("", endereco.getBairro());
            assertEquals("", endereco.getCidade());
            assertEquals("", endereco.getEstado());
            assertEquals("", endereco.getCep());
        }
        
        @Test
        @DisplayName("Deve tratar complemento vazio em toString")
        void testToString_ComplementoVazio() {
            endereco.setComplemento("");
            
            String resultado = endereco.toString();
            
            assertEquals("", endereco.getComplemento());
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
  
            String resultado2 = endereco.toString();
            assertEquals(resultado, resultado2);
        }
        
        @Test
        @DisplayName("Deve tratar complemento com espaços em branco")
        void testToString_ComplementoComEspacos() {
            endereco.setComplemento("   ");
            
            String resultado = endereco.toString();
            
            assertEquals("   ", endereco.getComplemento());
            assertTrue(endereco.getComplemento().trim().isEmpty());
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
        }
        
        @Test
        @DisplayName("Deve permitir endereço com apenas campos obrigatórios")
        void testEndereco_ApenasObrigatorios() {
            Endereco enderecoMinimo = new Endereco("Rua Principal", "1", "Centro", "Cidade", "UF", "12345678");
            
            assertEquals("Rua Principal", enderecoMinimo.getRua());
            assertEquals("1", enderecoMinimo.getNumero());
            assertEquals("Centro", enderecoMinimo.getBairro());
            assertEquals("Cidade", enderecoMinimo.getCidade());
            assertEquals("UF", enderecoMinimo.getEstado());
            assertEquals("12345678", enderecoMinimo.getCep());
            assertNull(enderecoMinimo.getComplemento());
        }
    }
}




