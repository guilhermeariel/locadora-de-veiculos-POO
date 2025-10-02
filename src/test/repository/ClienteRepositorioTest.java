package repository;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        assertEquals(1, clienteRepo.getLista().size());
        assertEquals(cliente, clienteRepo.buscarPorIdentificador(cpf));
    }

    @Test
    void when_SalvarPessoaJuridica_Then_PessoaJuridicaExistenteNaLista() {
        String cnpj = "11111111111111";
        Cliente cliente = new PessoaJuridica("Top Assist", cnpj);
        clienteRepo.salvar(cliente);

        assertEquals(1, clienteRepo.getLista().size());
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
        int tamanhoDaListaAntes = clienteRepo.getLista().size();

        clienteRepo.atualizar(cliente);

        assertEquals(tamanhoDaListaAntes, clienteRepo.getLista().size());
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
    void when_getListaClientes_Then_RetornaListaComTodos() {
        Cliente cliente1 = new PessoaFisica("Paula Eduarda", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "11111111111111");

        clienteRepo.salvar(cliente1);
        clienteRepo.salvar(cliente2);

        assertEquals(2, clienteRepo.getLista().size());
        assertTrue(clienteRepo.getLista().contains(cliente1));
        assertTrue(clienteRepo.getLista().contains(cliente2));
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

    @Test
    void when_RemoverClienteExistente_Then_ClienteNaoEstaMaisNaLista() {
        Cliente cliente = new PessoaFisica("Paula", "11111111111");
        clienteRepo.salvar(cliente);

        clienteRepo.removerItem(cliente);

        assertFalse(clienteRepo.getLista().contains(cliente));
        assertNull(clienteRepo.buscarPorIdentificador(cliente.getIdentificador()));
    }

    @Test
    void when_RemoverClienteNaoExistente_Then_ListaPermaneceInalterada() {
        Cliente cliente = new PessoaFisica("Paula", "11111111111");
        int tamanhoAntes = clienteRepo.getLista().size();

        clienteRepo.removerItem(cliente);

        assertEquals(tamanhoAntes, clienteRepo.getLista().size());
    }

    @Test
    void when_LimparLista_Then_ListaFicaVazia() {
        Cliente cliente = new PessoaFisica("Paula", "11111111111");
        clienteRepo.salvar(cliente);

        clienteRepo.limparLista();

        assertTrue(clienteRepo.getLista().isEmpty());
    }

    @Test
    void when_AdicionarListaClientes_Then_TodosSaoInseridos() {
        Cliente cliente1 = new PessoaFisica("Paula", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "11111111111111");

        List<Cliente> novaLista = List.of(cliente1, cliente2);
        clienteRepo.adicionarLista(novaLista);

        assertTrue(clienteRepo.getLista().containsAll(novaLista));
    }

    @Test
    void when_FiltrarPorNome_Then_RetornaClientesComNomeCorrespondente() {
        Cliente cliente1 = new PessoaFisica("Paula Eduarda", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "11111111111111");
        clienteRepo.salvar(cliente1);
        clienteRepo.salvar(cliente2);

        ClienteRepositorio resultado = clienteRepo.filtrar("nome", "paula");

        assertEquals(1, resultado.getLista().size());
        assertEquals(cliente1, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorDocumento_Then_RetornaClientesComDocumentoCorrespondente() {
        Cliente cliente1 = new PessoaFisica("Paula", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "22222222222222");
        clienteRepo.salvar(cliente1);
        clienteRepo.salvar(cliente2);

        ClienteRepositorio resultado = clienteRepo.filtrar("documento", "11111111111");

        assertEquals(1, resultado.getLista().size());
        assertEquals(cliente1, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarComCampoInvalido_Then_RetornaTodos() {
        Cliente cliente1 = new PessoaFisica("Paula", "11111111111");
        Cliente cliente2 = new PessoaJuridica("Top Assist", "22222222222222");
        clienteRepo.salvar(cliente1);
        clienteRepo.salvar(cliente2);

        ClienteRepositorio resultado = clienteRepo.filtrar("invalido", "xxx");

        assertEquals(2, resultado.getLista().size());
        assertTrue(resultado.getLista().containsAll(List.of(cliente1, cliente2)));
    }
}
