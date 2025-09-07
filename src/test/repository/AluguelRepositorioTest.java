package repository;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AluguelRepositorioTest {

    private AluguelRepositorio aluguelRepo;
    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setup() {
        aluguelRepo = new AluguelRepositorio();
        cliente = new PessoaFisica("Paula", "11111111111");
        veiculo = new Veiculo("ABC1234", "Fiat Uno", TipoVeiculo.PEQUENO);
    }

    @Test
    void when_SalvarAluguel_Then_AluguelExistenteNaLista() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());
        aluguelRepo.salvar(aluguel);

        assertEquals(1, aluguelRepo.listar().size());
        assertEquals(aluguel, aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_AtualizarAluguelExistente_Then_AluguelAtualizado() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());
        aluguelRepo.salvar(aluguel);

        aluguel.setDataFim(LocalDateTime.now().plusDays(2));
        aluguelRepo.atualizar(aluguel);

        Aluguel buscado = aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador());
        assertEquals(LocalDateTime.now().plusDays(2), buscado.getDataFim());
    }

    @Test
    void when_AtualizarAluguelNaoExistente_Then_NaoAlteraLista() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());
        int tamanhoDaListaAntes = aluguelRepo.listar().size();

        aluguelRepo.atualizar(aluguel);

        assertEquals(tamanhoDaListaAntes, aluguelRepo.listar().size());
        assertNull(aluguelRepo.buscarPorIdentificador(aluguel.getIdentificador()));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaAluguelCorreto() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());
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
    void when_ListarAlugueis_Then_RetornaListaComTodos() {
        Aluguel aluguel1 = new Aluguel(cliente, veiculo, LocalDateTime.now());
        Aluguel aluguel2 = new Aluguel(cliente, veiculo, LocalDateTime.now());

        aluguelRepo.salvar(aluguel1);
        aluguelRepo.salvar(aluguel2);

        assertEquals(2, aluguelRepo.listar().size());
        assertTrue(aluguelRepo.listar().contains(aluguel1));
        assertTrue(aluguelRepo.listar().contains(aluguel2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());
        int id = aluguel.getIdentificador();

        assertEquals(id, aluguelRepo.getIdentificador(aluguel));
    }
}
