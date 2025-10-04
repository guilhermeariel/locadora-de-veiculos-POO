package servicos;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClienteRepositorio;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private String cpf;
    private String cnpj;

    // --- Suppliers para gerar clientes de teste ---
    private Supplier<Cliente> clienteFisicoSupplier;
    private Supplier<Cliente> clienteJuridicoSupplier;

    @BeforeEach
    void setup() {
        cpf = "12345678901";
        cnpj = "12345678000199";

        clienteFisicoSupplier = () -> new PessoaFisica("Paula", cpf);
        clienteJuridicoSupplier = () -> new PessoaJuridica("Empresa X", cnpj);
    }

    // --- CADASTRAR CLIENTE ---

    @Test
    void when_CadastrarClienteFisicaValida_thenClienteSalvo() {
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(null);

        clienteService.cadastrarCliente("Paula", cpf, true);

        verify(clienteRepositorio, times(1)).salvar(any(PessoaFisica.class));
    }

    @Test
    void when_CadastrarClienteJuridicaValida_thenClienteSalvo() {
        when(clienteRepositorio.buscarPorIdentificador(cnpj)).thenReturn(null);

        clienteService.cadastrarCliente("Empresa X", cnpj, false);

        verify(clienteRepositorio, times(1)).salvar(any(PessoaJuridica.class));
    }

    @Test
    void when_CadastrarClienteComDocumentoDuplicado_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(clienteFisicoSupplier.get());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.cadastrarCliente("Eduarda", cpf, true)
        );

        assertEquals("Já existe cliente cadastrado com esse documento.", exception.getMessage());
        verify(clienteRepositorio, never()).salvar(any());
    }

    // --- BUSCAR CLIENTE ---

    @Test
    void when_BuscarClienteExistente_thenReturnCliente() {
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(clienteJuridicoSupplier.get());

        Cliente resultado = clienteService.buscarClientePorId(cpf);

        assertNotNull(resultado);
        assertEquals("Empresa X", resultado.getNome());
    }

    @Test
    void when_BuscarClienteInexistente_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador("000")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.buscarClientePorId("000")
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
    }

    // --- ATUALIZAR CLIENTE ---

    @Test
    void when_AtualizarNomeClienteValido_thenNomeAtualizado() {
        Cliente cliente = clienteFisicoSupplier.get();
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(cliente);

        clienteService.atualizarCliente(cpf, "Paula Nova");

        assertEquals("Paula Nova", cliente.getNome());
        verify(clienteRepositorio, times(1)).atualizar(cliente);
    }

    @Test
    void when_AtualizarClienteInexistente_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador("000")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.atualizarCliente("000", "Novo Nome")
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clienteRepositorio, never()).atualizar(any());
    }

    @Test
    void when_AtualizarNomeClienteComNomeInvalido_thenThrowException() {
        Cliente cliente = clienteFisicoSupplier.get();
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(cliente);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.atualizarCliente(cpf, "")
        );

        assertEquals("Nome inválido.", exception.getMessage());
        verify(clienteRepositorio, never()).atualizar(any());
    }

    // --- REMOVER CLIENTE ---

    @Test
    void when_RemoverClienteExistente_thenClienteRemovido() {
        Cliente cliente = clienteFisicoSupplier.get();
        when(clienteRepositorio.buscarPorIdentificador(cpf)).thenReturn(cliente);

        clienteService.removerCliente(cpf);

        verify(clienteRepositorio, times(1)).removerItem(cliente);
    }

    @Test
    void when_RemoverClienteInexistente_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador("000")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.removerCliente("000")
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clienteRepositorio, never()).removerItem(any());
    }
}
