/**
 * Giovana Maieli da Conceicao Livramento - 202365172A
 * Esthefany Emmanuele Silva Carvalho - 202365500B
 * Aurea Cunha Prado - 202365062AC
 */
package com.faculdade.oo.dao;

import com.faculdade.oo.model.Usuario;
import com.faculdade.oo.model.Dono;
import com.faculdade.oo.model.TipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@DisplayName("Testes da classe UsuarioDAO")
class UsuarioDAOTest {
    
    private UsuarioDAO usuarioDAO;
    private Usuario usuario1;
    private static final String ARQUIVO_TESTE = "usuarios_test.txt";
    
    @BeforeEach
    void setUp() throws Exception {
        // Limpa o arquivo de teste antes de cada teste
        limparArquivoTeste();
        usuarioDAO = new UsuarioDAO(ARQUIVO_TESTE);
        usuario1 = new Dono(1, "João Silva", "12345678901", "joao@email.com", "senha123");
    }
    
    @AfterEach
    void tearDown() {
        limparArquivoTeste();
    }
    
    private void limparArquivoTeste() {
        try {
            Path arquivoPath = Paths.get(ARQUIVO_TESTE);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
        }
    }
    
    @Nested
    @DisplayName("Testes básicos do DAO")
    class TesteBasicoDAO {
        
        @Test
        @DisplayName("Deve criar instância do UsuarioDAO")
        void testCriarInstancia_UsuarioDAO() {
            assertNotNull(usuarioDAO);
            assertInstanceOf(UsuarioDAO.class, usuarioDAO);
        }
        
        @Test
        @DisplayName("Deve listar usuários vazios inicialmente")
        void testListarTodos_DeveRetornarListaVazia() throws Exception {
            var usuarios = usuarioDAO.listarTodos();
            
            assertNotNull(usuarios);
            assertTrue(usuarios.isEmpty());
        }
        
        @Test
        @DisplayName("Deve salvar usuário com sucesso")
        void testSalvar_DeveSalvarUsuarioComSucesso() {
            assertDoesNotThrow(() -> {
                usuarioDAO.salvar(usuario1);
            });
        }
        
        @Test
        @DisplayName("Deve buscar usuário inexistente retornando null")
        void testBuscarPorId_UsuarioInexistente_DeveRetornarNull() throws Exception {
            Usuario usuario = usuarioDAO.buscarPorId(999);
            
            assertNull(usuario);
        }
        
        @Test
        @DisplayName("Deve buscar por email inexistente retornando null")
        void testBuscarPorEmail_EmailInexistente_DeveRetornarNull() throws Exception {
            Usuario usuario = usuarioDAO.buscarPorEmail("inexistente@email.com");
            
            assertNull(usuario);
        }
        
        @Test
        @DisplayName("Deve buscar por tipo retornando lista vazia")
        void testBuscarPorTipo_DeveRetornarListaVazia() throws Exception {
            var usuarios = usuarioDAO.buscarPorTipo(TipoUsuario.DONO);
            
            assertNotNull(usuarios);
            assertTrue(usuarios.isEmpty());
        }
        
        @Test
        @DisplayName("Deve obter próximo ID começando em 1")
        void testObterProximoId_DeveComesarEm1() throws Exception {
            int proximoId = usuarioDAO.obterProximoId();
            
            assertTrue(proximoId >= 1);
        }
        
        @Test
        @DisplayName("Deve verificar se ID existe retornando false para inexistente")
        void testExiste_IdInexistente_DeveRetornarFalse() throws Exception {
            boolean existe = usuarioDAO.existe(999);
            
            assertFalse(existe);
        }
        
        @Test
        @DisplayName("Deve lançar exceção ao salvar usuário nulo")
        void testSalvar_UsuarioNulo_DeveLancarExcecao() {
            assertThrows(Exception.class, () -> {
                usuarioDAO.salvar(null);
            });
        }
        
        @Test
        @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
        void testAtualizar_UsuarioInexistente_DeveLancarExcecao() {
            assertThrows(Exception.class, () -> {
                usuarioDAO.atualizar(usuario1);
            });
        }
    }
}



