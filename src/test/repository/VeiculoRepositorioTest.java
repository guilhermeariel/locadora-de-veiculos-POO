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

        assertEquals(1, veiculoRepo.listar().size());
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
        int tamanhoDaListaAntes = veiculoRepo.listar().size();

        veiculoRepo.atualizar(veiculo);

        assertEquals(tamanhoDaListaAntes, veiculoRepo.listar().size());
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
    void when_ListarVeiculos_Then_RetornaListaComTodos() {
        Veiculo veiculo1 = new Veiculo("ABC1234", TipoVeiculo.SEDAN, "Fiat Uno", true);
        Veiculo veiculo2 = new Veiculo("DEF5678", TipoVeiculo.SUV, "Chevrolet Onix", true);

        veiculoRepo.salvar(veiculo1);
        veiculoRepo.salvar(veiculo2);

        assertEquals(2, veiculoRepo.listar().size());
        assertTrue(veiculoRepo.listar().contains(veiculo1));
        assertTrue(veiculoRepo.listar().contains(veiculo2));
    }

    @Test
    void when_GetIdentificador_Then_RetornaCorreto() {
        String placa = "ABC1234";
        Veiculo veiculo = new Veiculo(placa, TipoVeiculo.HATCH, "Fiat Uno", true);

        assertEquals(placa, veiculoRepo.getIdentificador(veiculo));
    }
}
