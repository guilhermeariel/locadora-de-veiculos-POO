package repository;

import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;

public class ClienteRepositorioTest {

    private ClienteRepositorio clienteRepo;
    private Supplier<Cliente> clienteFisicaSupplier;
    private Supplier<Cliente> clienteJuridicaSupplier;

    @BeforeEach
    void setup() {
        clienteRepo = new ClienteRepositorio();

        clienteFisicaSupplier = () -> new PessoaFisica("Paula", "11111111111");
        clienteJuridicaSupplier = () -> new PessoaJuridica("Top Assist", "22222222222222");
    }

    // --------------------------
    // Testes principais
    // --------------------------

    @Test
    void when_SalvarPessoaFisica_Then_ExistenteNaLista() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        assertEquals(1, clienteRepo.getLista().size());
        assertEquals(cliente, clienteRepo.buscarPorIdentificador(cliente.getIdentificador()));
    }

    @Test
    void when_SalvarPessoaJuridica_Then_ExistenteNaLista() {
        Cliente cliente = clienteJuridicaSupplier.get();
        clienteRepo.salvar(cliente);

        assertEquals(1, clienteRepo.getLista().size());
        assertEquals(cliente, clienteRepo.buscarPorIdentificador(cliente.getIdentificador()));
    }

    @Test
    void when_AtualizarClienteExistente_Then_Atualizado() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        Cliente atualizado = new PessoaFisica("Paula Eduarda", cliente.getIdentificador());
        clienteRepo.atualizar(atualizado);

        Cliente buscado = clienteRepo.buscarPorIdentificador(cliente.getIdentificador());
        assertEquals("Paula Eduarda", buscado.getNome());
    }

    @Test
    void when_AtualizarClienteNaoExistente_Then_NaoAlteraLista() {
        Cliente cliente = clienteFisicaSupplier.get();
        int tamanhoAntes = clienteRepo.getLista().size();
        clienteRepo.atualizar(cliente);
        assertEquals(tamanhoAntes, clienteRepo.getLista().size());
        assertNull(clienteRepo.buscarPorIdentificador(cliente.getIdentificador()));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaCorreto() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        Cliente buscado = clienteRepo.buscarPorIdentificador(cliente.getIdentificador());
        assertNotNull(buscado);
        assertEquals(cliente.getNome(), buscado.getNome());
    }

    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        assertNull(clienteRepo.buscarPorIdentificador("99999999999"));
    }

    @Test
    void when_getListaClientes_Then_RetornaTodos() {
        Cliente c1 = clienteFisicaSupplier.get();
        Cliente c2 = clienteJuridicaSupplier.get();
        clienteRepo.salvar(c1);
        clienteRepo.salvar(c2);

        List<Cliente> lista = clienteRepo.getLista();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(c1));
        assertTrue(lista.contains(c2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        Cliente pf = clienteFisicaSupplier.get();
        Cliente pj = clienteJuridicaSupplier.get();

        assertEquals(pf.getIdentificador(), clienteRepo.getIdentificador(pf));
        assertEquals(pj.getIdentificador(), clienteRepo.getIdentificador(pj));
    }

    @Test
    void when_RemoverClienteExistente_Then_NaoEstaMaisNaLista() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);
        clienteRepo.removerItem(cliente);

        assertFalse(clienteRepo.getLista().contains(cliente));
        assertNull(clienteRepo.buscarPorIdentificador(cliente.getIdentificador()));
    }

    @Test
    void when_RemoverClienteNaoExistente_Then_ListaPermanece() {
        Cliente cliente = clienteFisicaSupplier.get();
        int tamanhoAntes = clienteRepo.getLista().size();
        clienteRepo.removerItem(cliente);
        assertEquals(tamanhoAntes, clienteRepo.getLista().size());
    }

    @Test
    void when_LimparLista_Then_FicaVazia() {
        Cliente c1 = clienteFisicaSupplier.get();
        clienteRepo.salvar(c1);
        clienteRepo.limparLista();
        assertTrue(clienteRepo.getLista().isEmpty());
    }

    @Test
    void when_AdicionarLista_Then_TodosInseridos() {
        Cliente c1 = clienteFisicaSupplier.get();
        Cliente c2 = clienteJuridicaSupplier.get();
        clienteRepo.adicionarLista(List.of(c1, c2));
        assertTrue(clienteRepo.getLista().containsAll(List.of(c1, c2)));
    }

    @Test
    void when_FiltrarPorNome_Then_RetornaCorretos() {
        Cliente c1 = clienteFisicaSupplier.get();
        Cliente c2 = clienteJuridicaSupplier.get();
        clienteRepo.salvar(c1);
        clienteRepo.salvar(c2);

        ClienteRepositorio filtrado = clienteRepo.filtrar("nome", "Paula");

        assertEquals(1, filtrado.getLista().size());
        assertEquals(c1, filtrado.getLista().get(0));
    }

    @Test
    void when_FiltrarPorDocumento_Then_RetornaCorretos() {
        Cliente c1 = clienteFisicaSupplier.get();
        Cliente c2 = clienteJuridicaSupplier.get();
        clienteRepo.salvar(c1);
        clienteRepo.salvar(c2);

        ClienteRepositorio filtrado = clienteRepo.filtrar("documento", c1.getIdentificador());

        assertEquals(1, filtrado.getLista().size());
        assertEquals(c1, filtrado.getLista().get(0));
    }

    @Test
    void when_FiltrarComCampoInvalido_Then_RetornaTodos() {
        Cliente c1 = clienteFisicaSupplier.get();
        Cliente c2 = clienteJuridicaSupplier.get();
        clienteRepo.salvar(c1);
        clienteRepo.salvar(c2);

        ClienteRepositorio filtrado = clienteRepo.filtrar("invalido", "xxx");

        assertEquals(2, filtrado.getLista().size());
        assertTrue(filtrado.getLista().containsAll(List.of(c1, c2)));
    }

    // --------------------------
    // Testes dos Consumers
    // --------------------------

    @Test
    void when_ConsumerAoSalvar_Applied() {
        StringBuilder log = new StringBuilder();
        clienteRepo.setAoSalvar(c -> log.append("salvo:").append(c.getIdentificador()));

        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        assertTrue(log.toString().contains(cliente.getIdentificador()));
    }

    @Test
    void when_ConsumerAoAtualizar_Applied() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        StringBuilder log = new StringBuilder();
        clienteRepo.setAoAtualizar(c -> log.append("atualizado:").append(c.getIdentificador()));

        Cliente atualizado = new PessoaFisica("Paula Eduarda", cliente.getIdentificador());
        clienteRepo.atualizar(atualizado);

        assertTrue(log.toString().contains(cliente.getIdentificador()));
    }

    @Test
    void when_ConsumerAoRemover_Applied() {
        Cliente cliente = clienteFisicaSupplier.get();
        clienteRepo.salvar(cliente);

        StringBuilder log = new StringBuilder();
        clienteRepo.setAoRemover(c -> log.append("removido:").append(c.getIdentificador()));

        clienteRepo.removerItem(cliente);

        assertTrue(log.toString().contains(cliente.getIdentificador()));
    }
}
