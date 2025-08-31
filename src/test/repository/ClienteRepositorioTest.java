package repository;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositorioTest {

    private ClienteRepositorio clienteRepo;

    @BeforeEach
    void setup() {
        clienteRepo = new ClienteRepositorio();
    }

    @Test
    void when_SalvarPessoaFisica_Then_PessoaFisicaExistenteNaLista() {
        String cpf = "11111111111";
        Cliente cliente = new PessoaFisica("Paula", cpf);
        clienteRepo.salvar(cliente);

        assertEquals(1, clienteRepo.listar().size());
        assertEquals(cliente, clienteRepo.buscarPorIdentificador(cpf));
    }

    @Test
    void when_SalvarPessoaJuridica_Then_PessoaJuridicaExistenteNaLista() {
        String cnpj = "11111111111111";
        Cliente cliente = new PessoaJuridica("Top Assist", cnpj);
        clienteRepo.salvar(cliente);

        assertEquals(1, clienteRepo.listar().size());
        assertEquals(cliente, clienteRepo.buscarPorIdentificador(cnpj));
    }

    @Test
    void when_AtualizarClienteExistente_Then_ClienteAtualizado() {
        String cpf = "11111111111";
        Cliente cliente = new PessoaFisica("Paula", cpf);
        clienteRepo.salvar(cliente);

        Cliente clienteAtualizado = new PessoaFisica("Paula Eduarda", cpf);
        clienteRepo.atualizar(clienteAtualizado);

        Cliente buscado = clienteRepo.buscarPorIdentificador(cpf);
        assertEquals("Paula Eduarda", buscado.getNome());
    }

    @Test
    void when_AtualizarClienteNaoExistente_Then_NaoAlteraLista() {
        String cpf = "11111111111";
        Cliente cliente = new PessoaFisica("Paula", cpf);
        int tamanhoDaListaAntes = clienteRepo.listar().size();

        clienteRepo.atualizar(cliente);

        assertEquals(tamanhoDaListaAntes, clienteRepo.listar().size());
        assertNull(clienteRepo.buscarPorIdentificador(cpf));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaClienteCorreto() {
        String cpf = "11111111111";
        Cliente cliente = new PessoaFisica("Paula", cpf);
        clienteRepo.salvar(cliente);

        Cliente clienteBuscado = clienteRepo.buscarPorIdentificador(cpf);
        assertNotNull(clienteBuscado);
        assertEquals("Paula", clienteBuscado.getNome());
    }

    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        Cliente clienteBuscado = clienteRepo.buscarPorIdentificador("999");
        assertNull(clienteBuscado);
    }

    @Test
    void when_ListarClientes_Then_RetornaListaComTodos() {
        Cliente cliente1 = new PessoaFisica("Paula Eduarda", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "11111111111111");

        clienteRepo.salvar(cliente1);
        clienteRepo.salvar(cliente2);

        assertEquals(2, clienteRepo.listar().size());
        assertTrue(clienteRepo.listar().contains(cliente1));
        assertTrue(clienteRepo.listar().contains(cliente2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        String cpf = "11111111111";
        String cnpj = "11111111111111";
        Cliente pessoaFisica = new PessoaFisica("Paula", cpf);
        Cliente pessoaJuridica = new PessoaJuridica("Top Assist", cnpj);

        assertEquals(cpf, clienteRepo.getIdentificador(pessoaFisica));
        assertEquals(cnpj, clienteRepo.getIdentificador(pessoaJuridica));
    }
}
