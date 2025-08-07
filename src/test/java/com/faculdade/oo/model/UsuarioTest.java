package com.faculdade.oo.model;

import com.faculdade.oo.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes das classes Usuario")
class UsuarioTest {
    
    private Endereco endereco;
    
    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua das Flores, 123", "", "Centro", "São Paulo", "SP", "01234567");
    }
    
    @Nested
    @DisplayName("Testes da classe Usuario base")
    class UsuarioBaseTest {
        
        @Test
        @DisplayName("Deve criar usuário com construtor completo")
        void testConstrutor_ComParametrosCompletos_DeveCriarUsuario() {
            
            Usuario usuario = new Dono(1, "João Silva", "12345678909", "joao@email.com", "senha123");
            
            assertEquals("João Silva", usuario.getNome());
            assertEquals("joao@email.com", usuario.getEmail());
            assertEquals("12345678909", usuario.getCpf());
            assertEquals("senha123", usuario.getSenha());
            assertEquals(TipoUsuario.DONO, usuario.getTipo());
            assertEquals(1, usuario.getId());
        }
        
        @Test
        @DisplayName("Deve gerar usuários com IDs diferentes")
        void testConstrutores_DevePermitirIdsdiferentes() {
            Usuario usuario1 = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            Usuario usuario2 = new Gerente(2, "Maria", "98765432100", "maria@email.com", "senha456");
            
            assertNotEquals(usuario1.getId(), usuario2.getId());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de nome")
        void testSetNome_DeveAlterarNome() {
            Usuario usuario = new Dono(1, "Nome Original", "12345678909", "email@test.com", "senha123");
            
            usuario.setNome("Nome Alterado");
            
            assertEquals("Nome Alterado", usuario.getNome());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de email")
        void testSetEmail_DeveAlterarEmail() {
            Usuario usuario = new Dono(1, "João", "12345678909", "email.original@test.com", "senha123");
            
            usuario.setEmail("email.novo@test.com");
            
            assertEquals("email.novo@test.com", usuario.getEmail());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de CPF")
        void testSetCpf_DeveAlterarCpf() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@test.com", "senha123");
            
            usuario.setCpf("98765432100");
            
            assertEquals("98765432100", usuario.getCpf());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de senha")
        void testSetSenha_DeveAlterarSenha() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@test.com", "senha123");
            
            usuario.setSenha("novaSenha456");
            
            assertEquals("novaSenha456", usuario.getSenha());
        }
        
        @Test
        @DisplayName("Deve implementar equals corretamente baseado no ID")
        void testEquals_DeveCompararPorId() {
            Usuario usuario1 = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            Usuario usuario2 = new Dono(2, "Maria", "98765432100", "maria@email.com", "senha456");
            Usuario usuario3 = new Dono(1, "Pedro", "11111111111", "pedro@email.com", "senha789");
            
            // Mesmo objeto
            assertTrue(usuario1.equals(usuario1));
            
            assertFalse(usuario1.equals(usuario2));
            
            assertTrue(usuario1.equals(usuario3));
            
            assertFalse(usuario1.equals(null));
            
            assertFalse(usuario1.equals("string"));
        }
        
        @Test
        @DisplayName("Deve implementar hashCode consistente com equals")
        void testHashCode_DeveSerConsistente() {
            Usuario usuario1 = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            Usuario usuario2 = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            
            if (usuario1.equals(usuario2)) {
                assertEquals(usuario1.hashCode(), usuario2.hashCode());
            }
        }
        
        @Test
        @DisplayName("Deve implementar toString com informações básicas")
        void testToString_DeveConterInformacoesBasicas() {
            Usuario usuario = new Dono(1, "João Silva", "12345678909", "joao@email.com", "senha123");
            
            String resultado = usuario.toString();
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
            
            assertEquals("João Silva", usuario.getNome());
            assertEquals("joao@email.com", usuario.getEmail());
            assertEquals("12345678909", usuario.getCpf());
            assertEquals(1, usuario.getId());
            assertEquals(TipoUsuario.DONO, usuario.getTipo());
        }
    }
    
    @Nested
    @DisplayName("Testes da classe Dono")
    class DonoTest {
        
        @Test
        @DisplayName("Deve criar Dono com tipo correto")
        void testConstrutor_DeveCriarDonoComTipoCorreto() {
            Dono dono = new Dono(1, "João Silva", "12345678909", "joao@email.com", "senha123");
            
            assertEquals(TipoUsuario.DONO, dono.getTipo());
            assertEquals("João Silva", dono.getNome());
            assertEquals("joao@email.com", dono.getEmail());
            assertEquals("12345678909", dono.getCpf());
        }
        
        @Test
        @DisplayName("Deve criar Dono com construtor vazio e definir tipo")
        void testConstrutorVazio_DeveCriarDonoComTipo() {
            Dono dono = new Dono();
            
            assertEquals(TipoUsuario.DONO, dono.getTipo());
        }
        
        @Test
        @DisplayName("Deve herdar comportamentos da classe Usuario")
        void testHeranca_DeveHerdarComportamentos() {
            Dono dono = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            
            assertEquals(1, dono.getId());
            
            dono.setNome("João Alterado");
            assertEquals("João Alterado", dono.getNome());
        }
    }
    
    @Nested
    @DisplayName("Testes da classe Gerente")
    class GerenteTest {
        
        @Test
        @DisplayName("Deve criar Gerente com construtor básico")
        void testConstrutorBasico_DeveCriarGerente() {
            Gerente gerente = new Gerente(2, "Maria Silva", "98765432100", "maria@email.com", "senha456");
            
            assertEquals(TipoUsuario.GERENTE, gerente.getTipo());
            assertEquals("Maria Silva", gerente.getNome());
            assertEquals("maria@email.com", gerente.getEmail());
            assertEquals("98765432100", gerente.getCpf());
            assertEquals(0, gerente.getFranquiaId()); 
        }
        
        @Test
        @DisplayName("Deve criar Gerente com construtor completo")
        void testConstrutorCompleto_DeveCriarGerenteComFranquiaId() {
            Gerente gerente = new Gerente(2, "Maria Silva", "98765432100", "maria@email.com", "senha456", 5);
            
            assertEquals(TipoUsuario.GERENTE, gerente.getTipo());
            assertEquals("Maria Silva", gerente.getNome());
            assertEquals(5, gerente.getFranquiaId());
        }
        
        @Test
        @DisplayName("Deve permitir definir franquiaId")
        void testSetFranquiaId_DeveDefinirFranquiaId() {
            Gerente gerente = new Gerente(2, "Maria", "98765432100", "maria@email.com", "senha456");
            
            gerente.setFranquiaId(10);
            
            assertEquals(10, gerente.getFranquiaId());
        }
        
        @Test
        @DisplayName("Deve implementar toString com informações específicas do gerente")
        void testToString_DeveConterInformacoesGerente() {
            Gerente gerente = new Gerente(2, "Maria Silva", "98765432100", "maria@email.com", "senha456", 5);
            
            String resultado = gerente.toString();
            
            assertNotNull(resultado);
            assertFalse(resultado.trim().isEmpty());
            
            assertEquals("Maria Silva", gerente.getNome());
            assertEquals("maria@email.com", gerente.getEmail());
            assertEquals("98765432100", gerente.getCpf());
            assertEquals(2, gerente.getId());
            assertEquals(5, gerente.getFranquiaId());
            assertEquals(TipoUsuario.GERENTE, gerente.getTipo());
        }
    }
    
    @Nested
    @DisplayName("Testes de comportamentos especiais")
    class ComportamentosEspeciais {
        
        @Test
        @DisplayName("Deve manter integridade do ID após alterações")
        void testIntegridadeId_AposAlteracoes() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            int idOriginal = usuario.getId();
            
            usuario.setNome("Nome Alterado");
            usuario.setEmail("novo@email.com");
            usuario.setCpf("98765432100");
            usuario.setSenha("novaSenha");
            
            assertEquals(idOriginal, usuario.getId());
        }
        
        @Test
        @DisplayName("Deve tratar valores nulos em setters")
        void testSetters_ComValoresNulos() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            
            assertDoesNotThrow(() -> {
                usuario.setNome(null);
                usuario.setEmail(null);
                usuario.setCpf(null);
                usuario.setSenha(null);
            });
        }
        
        @Test
        @DisplayName("Deve comparar tipos diferentes de usuários corretamente")
        void testComparacaoTiposDiferentes() {
            Dono dono = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            Gerente gerente = new Gerente(2, "Maria", "98765432100", "maria@email.com", "senha456");
            
            assertFalse(dono.equals(gerente));
            
           assertEquals(TipoUsuario.DONO, dono.getTipo());
            assertEquals(TipoUsuario.GERENTE, gerente.getTipo());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de ID")
        void testSetId_DeveAlterarId() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            
            usuario.setId(999);
            
            assertEquals(999, usuario.getId());
        }
        
        @Test
        @DisplayName("Deve permitir alteração de tipo")
        void testSetTipo_DeveAlterarTipo() {
            Usuario usuario = new Dono(1, "João", "12345678909", "joao@email.com", "senha123");
            
            usuario.setTipo(TipoUsuario.GERENTE);
            
            assertEquals(TipoUsuario.GERENTE, usuario.getTipo());
        }
    }
}

