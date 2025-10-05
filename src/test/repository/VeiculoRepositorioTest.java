package repository;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoRepositorioTest {

    private VeiculoRepositorio veiculoRepo;
    private Supplier<Veiculo> veiculoSupplier;

    @BeforeEach
    void setup() {
        veiculoRepo = new VeiculoRepositorio();
        veiculoSupplier = () -> new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
    }

    @Test
    void when_SalvarVeiculo_Then_VeiculoExistenteNaLista() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        assertEquals(1, veiculoRepo.getLista().size());
        assertEquals(veiculo, veiculoRepo.buscarPorIdentificador("ABC1234"));
    }

    @Test
    void when_AtualizarVeiculoExistente_Then_VeiculoAtualizado() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        Veiculo veiculoAtualizado = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno 2025", true);
        veiculoRepo.atualizar(veiculoAtualizado);

        Veiculo buscado = veiculoRepo.buscarPorIdentificador("ABC1234");
        assertEquals("Fiat Uno 2025", buscado.getModelo());
    }

    @Test
    void when_AtualizarVeiculoNaoExistente_Then_NaoAlteraLista() {
        Veiculo veiculo = new Veiculo("XYZ9999", TipoVeiculo.SEDAN, "Gol", true);
        int tamanhoAntes = veiculoRepo.getLista().size();

        veiculoRepo.atualizar(veiculo);

        assertEquals(tamanhoAntes, veiculoRepo.getLista().size());
        assertNull(veiculoRepo.buscarPorIdentificador("XYZ9999"));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaVeiculoCorreto() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        Veiculo buscado = veiculoRepo.buscarPorIdentificador("ABC1234");
        assertNotNull(buscado);
        assertEquals("Fiat Uno", buscado.getModelo());
    }

    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        assertNull(veiculoRepo.buscarPorIdentificador("999"));
    }

    @Test
    void when_getListaVeiculos_Then_RetornaListaComTodos() {
        Veiculo v1 = veiculoSupplier.get();
        Veiculo v2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Chevrolet Onix", true);

        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        assertEquals(2, veiculoRepo.getLista().size());
        assertTrue(veiculoRepo.getLista().containsAll(List.of(v1, v2)));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        Veiculo veiculo = veiculoSupplier.get();
        assertEquals("ABC1234", veiculoRepo.getIdentificador(veiculo));
    }

    @Test
    void when_RemoverVeiculoExistente_Then_VeiculoNaoEstaMaisNaLista() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        veiculoRepo.removerItem(veiculo);

        assertFalse(veiculoRepo.getLista().contains(veiculo));
        assertNull(veiculoRepo.buscarPorIdentificador("ABC1234"));
    }

    @Test
    void when_RemoverVeiculoNaoExistente_Then_ListaPermaneceInalterada() {
        Veiculo veiculo = new Veiculo("XYZ9999", TipoVeiculo.SEDAN, "Gol", true);
        int tamanhoAntes = veiculoRepo.getLista().size();

        veiculoRepo.removerItem(veiculo);

        assertEquals(tamanhoAntes, veiculoRepo.getLista().size());
    }

    @Test
    void when_LimparLista_Then_ListaFicaVazia() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        veiculoRepo.limparLista();

        assertTrue(veiculoRepo.getLista().isEmpty());
    }

    @Test
    void when_AdicionarListaVeiculos_Then_TodosSaoInseridos() {
        Veiculo v1 = veiculoSupplier.get();
        Veiculo v2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Chevrolet Onix", true);

        veiculoRepo.adicionarLista(List.of(v1, v2));

        assertTrue(veiculoRepo.getLista().containsAll(List.of(v1, v2)));
    }

    @Test
    void when_FiltrarPorPlaca_Then_RetornaApenasVeiculosComPlacaCorrespondente() {
        Veiculo v1 = veiculoSupplier.get();
        Veiculo v2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Tracker", true);

        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        VeiculoRepositorio resultado = veiculoRepo.filtrar("placa", "ABC");

        assertEquals(1, resultado.getLista().size());
        assertEquals(v1, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorModelo_Then_RetornaVeiculosComModeloCorrespondente() {
        Veiculo v1 = veiculoSupplier.get();
        Veiculo v2 = new Veiculo("BBB2222", TipoVeiculo.SEDAN, "Fiat Siena", true);

        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        VeiculoRepositorio resultado = veiculoRepo.filtrar("modelo", "Fiat");

        assertEquals(2, resultado.getLista().size());
    }

    @Test
    void when_FiltrarPorTipo_Then_RetornaVeiculosComTipoCorrespondente() {
        Veiculo v1 = new Veiculo("AAA1111", TipoVeiculo.SUV, "Compass", true);
        Veiculo v2 = new Veiculo("BBB2222", TipoVeiculo.HATCH, "Onix", true);

        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        VeiculoRepositorio resultado = veiculoRepo.filtrar("tipo", "suv");

        assertEquals(1, resultado.getLista().size());
        assertEquals(v1, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarComFiltroInvalido_Then_RetornaTodos() {
        Veiculo v1 = new Veiculo("AAA1111", TipoVeiculo.SUV, "Compass", true);
        Veiculo v2 = new Veiculo("BBB2222", TipoVeiculo.HATCH, "Onix", true);

        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        VeiculoRepositorio resultado = veiculoRepo.filtrar("invalido", "xxx");

        assertEquals(2, resultado.getLista().size());
    }

    @Test
    void when_ListarDisponiveis_Then_RetornaApenasDisponiveis() {
        Veiculo disponivel = new Veiculo("AAA1111", TipoVeiculo.HATCH, "Onix", true);
        Veiculo indisponivel = new Veiculo("BBB2222", TipoVeiculo.SUV, "Compass", false);

        veiculoRepo.salvar(disponivel);
        veiculoRepo.salvar(indisponivel);

        var disponiveis = veiculoRepo.listarDisponiveis();

        assertEquals(1, disponiveis.size());
        assertEquals(disponivel, disponiveis.getFirst());
    }

    // --------------------------
    // Testes dos Consumers
    // --------------------------

    @Test
    void when_SalvarVeiculo_Then_ConsumerAoSalvarEhExecutado() {
        StringBuilder log = new StringBuilder();
        veiculoRepo.setAoSalvar(v -> log.append("salvo:").append(v.getPlaca()));

        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        assertTrue(log.toString().contains(veiculo.getPlaca()));
    }

    @Test
    void when_AtualizarVeiculo_Then_ConsumerAoAtualizarEhExecutado() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        StringBuilder log = new StringBuilder();
        veiculoRepo.setAoAtualizar(v -> log.append("atualizado:").append(v.getPlaca()));

        veiculo.setModelo("Fiat Novo Uno");
        veiculoRepo.atualizar(veiculo);

        assertTrue(log.toString().contains(veiculo.getPlaca()));
    }

    @Test
    void when_RemoverVeiculo_Then_ConsumerAoRemoverEhExecutado() {
        Veiculo veiculo = veiculoSupplier.get();
        veiculoRepo.salvar(veiculo);

        StringBuilder log = new StringBuilder();
        veiculoRepo.setAoRemover(v -> log.append("removido:").append(v.getPlaca()));

        veiculoRepo.removerItem(veiculo);

        assertTrue(log.toString().contains(veiculo.getPlaca()));
    }
}
