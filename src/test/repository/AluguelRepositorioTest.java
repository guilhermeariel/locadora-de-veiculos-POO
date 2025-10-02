package repository;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AluguelRepositorioTest {

    private AluguelRepositorio aluguelRepo;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setup() {
        aluguelRepo = new AluguelRepositorio();
        cliente = new PessoaFisica("Paula", "11111111111");
        veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);

    }

    @Test
    void when_SalvarAluguel_Then_AluguelExistenteNaLista() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        assertEquals(1, aluguelRepo.getLista().size());
        assertEquals(aluguel, aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_AtualizarAluguelExistente_Then_AluguelAtualizado() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        aluguel.setDataFim(LocalDateTime.now().plusDays(2));
        aluguelRepo.atualizar(aluguel);

        Aluguel buscado = aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador());
        assertEquals(LocalDateTime.now().plusDays(2), buscado.getDataFim());
    }

    @Test
    void when_AtualizarAluguelNaoExistente_Then_NaoAlteraLista() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        int tamanhoDaListaAntes = aluguelRepo.getLista().size();

        aluguelRepo.atualizar(aluguel);

        assertEquals(tamanhoDaListaAntes, aluguelRepo.getLista().size());
        assertNull(aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaAluguelCorreto() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);
        int id = aluguel.getIdentificador();

        Aluguel aluguelBuscado = aluguelRepo.buscarPorIdentificador(id);
        assertNotNull(aluguelBuscado);
        assertEquals("Paula", aluguelBuscado.getCliente().getNome());
    }


    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        Aluguel aluguelBuscado = aluguelRepo.buscarPorIdentificador(1);
        assertNull(aluguelBuscado);
    }

    @Test
    void when_getListaAlugueis_Then_RetornaListaComTodos() {
        Aluguel aluguel1 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Aluguel aluguel2 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        aluguelRepo.salvar(aluguel1);
        aluguelRepo.salvar(aluguel2);

        assertEquals(2, aluguelRepo.getLista().size());
        assertTrue(aluguelRepo.getLista().contains(aluguel1));
        assertTrue(aluguelRepo.getLista().contains(aluguel2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        int id = aluguel.getIdentificador();

        assertEquals(id, aluguelRepo.getIdentificador(aluguel));
    }

    @Test
    void when_RemoverItemExistente_Then_ItemNaoEstaMaisNaLista() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        aluguelRepo.removerItem(aluguel);

        assertFalse(aluguelRepo.getLista().contains(aluguel));
        assertNull(aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_RemoverItemNaoExistente_Then_ListaPermaneceInalterada() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        int tamanhoAntes = aluguelRepo.getLista().size();

        aluguelRepo.removerItem(aluguel);

        assertEquals(tamanhoAntes, aluguelRepo.getLista().size());
    }

    @Test
    void when_LimparLista_Then_ListaFicaVazia() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        aluguelRepo.limparLista();

        assertTrue(aluguelRepo.getLista().isEmpty());
    }

    @Test
    void when_AdicionarListaDeAlugueis_Then_TodosSaoInseridos() {
        Aluguel aluguel1 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Aluguel aluguel2 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        List<Aluguel> novaLista = List.of(aluguel1, aluguel2);
        aluguelRepo.adicionarLista(novaLista);

        assertTrue(aluguelRepo.getLista().containsAll(novaLista));
    }

    @Test
    void when_FiltrarPorCliente_Then_RetornaSomenteAlugueisDoCliente() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("cliente", "Paula");

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorVeiculo_Then_RetornaSomenteAlugueisDoVeiculo() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("veiculo", "ABC1234");

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorDataDeInicioDeAluguel_Then_RetornaCorrespondente() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0);
        Aluguel aluguel = new Aluguel(cliente, veiculo, inicio, inicio.plusDays(1));
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("data de aluguel", "2025-01-01");

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorDataDeDevolucaoDeAluguel_Then_RetornaCorrespondente() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime fim = inicio.plusDays(2);
        Aluguel aluguel = new Aluguel(cliente, veiculo, inicio, fim);
        aluguelRepo.salvar(aluguel);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("data de devolucao", fim.toString());

        assertEquals(1, resultado.getLista().size());
        assertEquals(aluguel, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarComCampoInvalido_Then_RetornaTodos() {
        Aluguel aluguel1 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Aluguel aluguel2 = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        aluguelRepo.salvar(aluguel1);
        aluguelRepo.salvar(aluguel2);

        Repositorio<Aluguel, Integer> resultado = aluguelRepo.filtrar("invalido", "teste");

        assertEquals(2, resultado.getLista().size());
        assertTrue(resultado.getLista().containsAll(List.of(aluguel1, aluguel2)));
    }

    @Test
    void when_BuscarPorItemClienteExistente_Then_RetornaAluguel() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        Aluguel resultado = aluguelRepo.buscarPorItem(cliente, "cliente");

        assertEquals(aluguel, resultado);
    }

    @Test
    void when_BuscarPorItemVeiculoExistente_Then_RetornaAluguel() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        aluguelRepo.salvar(aluguel);

        Aluguel resultado = aluguelRepo.buscarPorItem(veiculo, "veiculo");

        assertEquals(aluguel, resultado);
    }

    @Test
    void when_BuscarPorItemInvalido_Then_RetornaNull() {
        Aluguel resultado = aluguelRepo.buscarPorItem(cliente, "invalido");
        assertNull(resultado);
    }
}
