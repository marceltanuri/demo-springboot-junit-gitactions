package com.example.demo;

import com.example.demo.model.Contato;
import com.example.demo.repository.ContatoRepository;
import com.example.demo.service.ContatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class ContatoServiceTest {

    @InjectMocks
    private ContatoService contatoService; // Serviço a ser testado

    @Mock
    private ContatoRepository contatoRepository; // Repositório simulado

    private Contato contato; // Contato usado para testes

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        contato = new Contato();
        contato.setId(1L);
        contato.setNome("João Silva");
        contato.setEmail("joao.silva@example.com");
        contato.setTelefone("11987654321");
    }

    // Teste para salvar um novo contato
    @Test
    public void salvarContato_DeveSalvarComSucesso() {
        // Simulando o comportamento do repositório
        when(contatoRepository.save(contato)).thenReturn(contato);

        // Chama o método do serviço
        Contato resultado = contatoService.salvarContato(contato);

        // Verifica se o contato foi salvo corretamente
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao.silva@example.com", resultado.getEmail());
        verify(contatoRepository, times(1)).save(contato); // Verifica se o save foi chamado
    }

    // Teste para validar se o e-mail já existe
    @Test
    public void salvarContato_DeveLancarExcecaoSeEmailExistir() {
        // Simulando o comportamento do repositório
        when(contatoRepository.findByEmail(contato.getEmail())).thenReturn(Optional.of(contato));

        // Espera-se uma IllegalArgumentException se o e-mail já existir
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.salvarContato(contato);
        });

        assertEquals("Já existe um contato com este e-mail.", exception.getMessage());
    }

    // Teste para buscar contato por ID
    @Test
    public void buscarContatoPorId_DeveRetornarContato() {
        // Simulando o comportamento do repositório
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        // Chama o método do serviço
        Contato resultado = contatoService.buscarContatoPorId(1L);

        // Verifica se o contato retornado é o correto
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
    }

    // Teste para buscar contato por e-mail
    @Test
    public void buscarContatoPorEmail_DeveRetornarContato() {
        // Simulando o comportamento do repositório
        when(contatoRepository.findByEmail("joao.silva@example.com")).thenReturn(Optional.of(contato));

        // Chama o método do serviço
        Contato resultado = contatoService.buscarContatoPorEmail("joao.silva@example.com");

        // Verifica se o contato retornado é o correto
        assertNotNull(resultado);
        assertEquals("joao.silva@example.com", resultado.getEmail());
    }

    // Teste para excluir um contato
    @Test
    public void excluirContato_DeveExcluirComSucesso() {
        // Simula a exclusão do contato
        doNothing().when(contatoRepository).deleteById(1L);

        // Chama o método do serviço
        contatoService.excluirContato(1L);

        // Verifica se o repositório foi chamado para excluir o contato
        verify(contatoRepository, times(1)).deleteById(1L);
    }

    // Teste para validação de nome vazio
    @Test
    public void salvarContato_DeveLancarExcecaoSeNomeVazio() {
        // Modificando o contato para ter o nome vazio
        contato.setNome("");

        // Espera-se uma IllegalArgumentException se o nome for vazio
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.salvarContato(contato);
        });

        assertEquals("O nome do contato é obrigatório.", exception.getMessage());
    }

    // Teste para validação de e-mail inválido
    @Test
    public void salvarContato_DeveLancarExcecaoSeEmailInvalido() {
        // Modificando o contato para ter um e-mail inválido
        contato.setEmail("emailinvalido");

        // Espera-se uma IllegalArgumentException se o e-mail for inválido
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.salvarContato(contato);
        });

        assertEquals("O e-mail do contato é obrigatório e deve ser válido.", exception.getMessage());
    }
}
