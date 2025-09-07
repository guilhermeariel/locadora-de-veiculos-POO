package servicos;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClienteRepositorio;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceImplTest {

    private ClienteRepositorio clienteRepositorio;
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        clienteRepositorio = new ClienteRepositorio(); // ou seu repositório em memória
        clienteService = new ClienteServiceImpl(clienteRepositorio);
    }

    @Test
    void when_CadastrarClienteFisicaValida_thenClienteSalvo() {
        String documento = "12345678901";
        clienteService.cadastrarCliente("Paula", documento, true);

        Cliente cliente = clienteService.buscarClientePorId(documento);

        assertInstanceOf(PessoaFisica.class, cliente);
        assertEquals("Paula", cliente.getNome());
    }

    @Test
    void when_CadastrarClienteJuridicaValida_thenClienteSalvo() {
        String documento = "12345678000199";
        clienteService.cadastrarCliente("Empresa X", documento, false);

        Cliente cliente = clienteService.buscarClientePorId(documento);

        assertInstanceOf(PessoaJuridica.class, cliente);
        assertEquals("Empresa X", cliente.getNome());
    }

    @Test
    void when_CadastrarClienteComDocumentoDuplicado_thenThrowException() {
        String documento = "12345678901";
        clienteService.cadastrarCliente("Paula", documento, true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.cadastrarCliente("Eduarda", documento, true)
        );

        assertEquals("Já existe cliente cadastrado com esse documento.", exception.getMessage());
    }

    @Test
    void when_CadastrarClienteComNomeInvalido_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.cadastrarCliente("", "12345678901", true)
        );

        assertEquals("Nome inválido", exception.getMessage());
    }

    @Test
    void when_CadastrarClienteComCpfInvalido_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.cadastrarCliente("Paula", "123", true)
        );

        assertEquals("CPF inválido.", exception.getMessage());
    }

    @Test
    void when_CadastrarClienteComCnpjInvalido_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.cadastrarCliente("Empresa X", "123", false)
        );

        assertEquals("CNPJ inválido.", exception.getMessage());
    }

    @Test
    void when_BuscarClienteExistente_thenReturnCliente() {
        String documento = "12345678901";
        clienteService.cadastrarCliente("Paula", documento, true);

        Cliente cliente = clienteService.buscarClientePorId(documento);

        assertNotNull(cliente);
        assertEquals("Paula", cliente.getNome());
    }

    @Test
    void when_BuscarClienteInexistente_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.buscarClientePorId("00000000000")
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
    }

    @Test
    void when_AtualizarNomeClienteValido_thenNomeAtualizado() {
        String documento = "12345678901";
        clienteService.cadastrarCliente("Paula", documento, true);
        clienteService.atualizarCliente(documento, "Paula Nova");

        Cliente cliente = clienteService.buscarClientePorId(documento);

        assertEquals("Paula Nova", cliente.getNome());
    }

    @Test
    void when_AtualizarClienteInexistente_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.atualizarCliente("00000000000", "Novo Nome")
        );

        assertEquals("Cliente não encontrado.", exception.getMessage());
    }

    @Test
    void when_AtualizarNomeClienteComNomeInvalido_thenThrowException() {
        String documento = "12345678901";
        clienteService.cadastrarCliente("Paula", documento, true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.atualizarCliente(documento, "")
        );

        assertEquals("Nome inválido.", exception.getMessage());
    }
}
