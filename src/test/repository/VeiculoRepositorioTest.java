package repository;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoRepositorioTest {

    private VeiculoRepositorio veiculoRepo;

    @BeforeEach
    void setup() {
        veiculoRepo = new VeiculoRepositorio();
    }

    @Test
    void when_SalvarVeiculo_Then_VeiculoExistenteNaLista() {
        Veiculo veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
        veiculoRepo.salvar(veiculo);

        assertEquals(1, veiculoRepo.getLista().size());
        assertEquals(veiculo, veiculoRepo.buscarPorIdentificador("ABC1234"));
    }

    @Test
    void when_AtualizarVeiculoExistente_Then_VeiculoAtualizado() {
        Veiculo veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
        veiculoRepo.salvar(veiculo);

        Veiculo veiculoAtualizado = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno 2025", true);
        veiculoRepo.atualizar(veiculoAtualizado);

        Veiculo veiculoBuscado = veiculoRepo.buscarPorIdentificador("ABC1234");
        assertEquals("Fiat Uno 2025", veiculoBuscado.getModelo());
    }

    @Test
    void when_AtualizarVeiculoNaoExistente_Then_NaoAlteraLista() {
        Veiculo veiculo = new Veiculo("XYZ9999", TipoVeiculo.SEDAN, "Gol", true);
        int tamanhoDaListaAntes = veiculoRepo.getLista().size();

        veiculoRepo.atualizar(veiculo);

        assertEquals(tamanhoDaListaAntes, veiculoRepo.getLista().size());
        assertNull(veiculoRepo.buscarPorIdentificador("XYZ9999"));
    }

    @Test
    void when_BuscarPorIdentificadorExistente_Then_RetornaVeiculoCorreto() {
        String placa = "ABC1234";
        Veiculo veiculo = new Veiculo(placa, TipoVeiculo.HATCH, "Fiat Uno", true);
        veiculoRepo.salvar(veiculo);

        Veiculo veiculoBuscado = veiculoRepo.buscarPorIdentificador(placa);
        assertNotNull(veiculoBuscado);
        assertEquals("Fiat Uno", veiculoBuscado.getModelo());
    }

    @Test
    void when_BuscarPorIdentificadorNaoExistente_Then_RetornaNull() {
        Veiculo veiculoBuscado = veiculoRepo.buscarPorIdentificador("999");
        assertNull(veiculoBuscado);
    }

    @Test
    void when_getListaVeiculos_Then_RetornaListaComTodos() {
        Veiculo veiculo1 = new Veiculo("ABC1234", TipoVeiculo.SEDAN, "Fiat Uno", true);
        Veiculo veiculo2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Chevrolet Onix", true);

        veiculoRepo.salvar(veiculo1);
        veiculoRepo.salvar(veiculo2);

        assertEquals(2, veiculoRepo.getLista().size());
        assertTrue(veiculoRepo.getLista().contains(veiculo1));
        assertTrue(veiculoRepo.getLista().contains(veiculo2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        String placa = "ABC1234";
        Veiculo veiculo = new Veiculo(placa, TipoVeiculo.HATCH, "Fiat Uno", true);

        assertEquals(placa, veiculoRepo.getIdentificador(veiculo));
    }

    @Test
    void when_RemoverVeiculoExistente_Then_VeiculoNaoEstaMaisNaLista() {
        Veiculo veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
        veiculoRepo.salvar(veiculo);

        assertEquals(1, veiculoRepo.getLista().size());

        veiculoRepo.removerItem(veiculo);

        assertTrue(veiculoRepo.getLista().isEmpty());
        assertNull(veiculoRepo.buscarPorIdentificador("ABC1234"));
    }

    @Test
    void when_ExistePlacaExistente_Then_RetornaTrue() {
        Veiculo veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
        veiculoRepo.salvar(veiculo);

        assertTrue(veiculoRepo.existePlaca("ABC1234"));
    }

    @Test
    void when_ExistePlacaInexistente_Then_RetornaFalse() {
        assertFalse(veiculoRepo.existePlaca("ZZZ9999"));
    }

    @Test
    void when_FiltrarPorPlaca_Then_RetornaApenasVeiculosComPlacaCorrespondente() {
        Veiculo v1 = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
        Veiculo v2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Chevrolet Tracker", true);
        veiculoRepo.salvar(v1);
        veiculoRepo.salvar(v2);

        VeiculoRepositorio resultado = veiculoRepo.filtrar("placa", "ABC");

        assertEquals(1, resultado.getLista().size());
        assertEquals(v1, resultado.getLista().getFirst());
    }

    @Test
    void when_FiltrarPorModelo_Then_RetornaVeiculosComModeloCorrespondente() {
        Veiculo v1 = new Veiculo("AAA1111", TipoVeiculo.HATCH, "Fiat Uno", true);
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
}
