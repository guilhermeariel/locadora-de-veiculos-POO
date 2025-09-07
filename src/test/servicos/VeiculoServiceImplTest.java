package servicos;

import model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.VeiculoRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoServiceImplTest {

    private VeiculoRepositorio veiculoRepositorio;
    private VeiculoServiceImpl veiculoService;

    @BeforeEach
    void setUp() {
        veiculoRepositorio = new VeiculoRepositorio();
        veiculoService = new VeiculoServiceImpl(veiculoRepositorio);
    }

    @Test
    void when_CadastrarVeiculoValido_thenVeiculoSalvo() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "PEQUENO";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        Veiculo veiculo = veiculoService.buscarVeiculoPorId(placa);

        assertEquals("Gol", veiculo.getModelo());
    }

    @Test
    void when_CadastrarVeiculoComPlacaDuplicada_thenThrowException() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo(placa, "Audi", "medio")
        );

        assertEquals("Já existe um veículo com essa placa.", e.getMessage());
    }

    @Test
    void when_CadastrarVeiculoComPlacaInvalida_thenThrowException() {
        String placa = "123";
        String modelo = "Gol";
        String tipo = "PEQUENO";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo(placa, modelo, tipo));

        assertEquals("Placa inválida.", e.getMessage());
    }

    @Test
    void when_CadastrarVeiculoComModeloInvalido_thenThrowException() {
        String placa = "ABC1234";
        String modelo = "";
        String tipo = "PEQUENO";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo(placa, modelo, tipo));

        assertEquals("Modelo inválido.", e.getMessage());
    }

    @Test
    void when_CadastrarVeiculoComTipoVeiculoInvalido_thenThrowException() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "caminhonete";

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo(placa, modelo, tipo));

        assertEquals("Tipo de veículo inválido.", e.getMessage());
    }


    @Test
    void when_AtualizarVeiculoInexistente_thenThrowException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.atualizarVeiculo("ABC1234", "PEQUENO", false)
        );

        assertEquals("Veículo não encontrado.", e.getMessage());
    }

    @Test
    void when_AtualizarVeiculoComTipoInvalido_thenThrowException() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.atualizarVeiculo("ABC1234", "caminhonete", false)
        );

        assertEquals("Tipo de veículo inválido.", e.getMessage());
    }

    @Test
    void when_AtualizarVeiculoComTipoValido_thenVeiculoAlterado() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        veiculoService.atualizarVeiculo(placa, "Medio", null);

        Veiculo veiculo = veiculoService.buscarVeiculoPorId(placa);

        assertEquals("MEDIO", veiculo.getTipo().name());
    }

    @Test
    void when_AtualizarVeiculoComDisponibilidadeValida_thenVeiculoAlterado() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        veiculoService.atualizarVeiculo(placa, null, false);

        Veiculo veiculo = veiculoService.buscarVeiculoPorId(placa);

        assertFalse(veiculo.isDisponivel());
    }

    @Test
    void when_BuscarVeiculoInexistentePorModelo_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.buscarVeiculoPorModelo("Gol")
        );

        assertEquals("Nenhum veículo encontrado com o modelo: Gol", exception.getMessage());
    }

    @Test
    void when_BuscarVeiculosExistentesPorModelo_thenReturnVeiculo() {
        String placa1 = "ABC1234";
        String placa2 = "DEF5678";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa1, modelo, tipo);
        veiculoService.cadastrarVeiculo(placa2, modelo, tipo);

        List<Veiculo> veiculos = veiculoService.buscarVeiculoPorModelo(modelo);

        assertEquals(2, veiculos.size());

        List<String> placasEsperadas = List.of(placa1, placa2);
        for (Veiculo v : veiculos) {
            assertEquals(modelo.toLowerCase(), v.getModelo().toLowerCase());
            assertTrue(placasEsperadas.contains(v.getPlaca()));
        }
    }

    @Test
    void when_BuscarVeiculoInexistentePorId_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.buscarVeiculoPorId("123")
        );

        assertEquals("Veiculo não encontrado.", exception.getMessage());
    }

    @Test
    void when_BuscarVeiculoExistentePorId_thenReturnVeiculo() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        Veiculo veiculo = veiculoService.buscarVeiculoPorId(placa);

        assertEquals(placa, veiculo.getPlaca());
    }

    @Test
    void when_ValidarDisponibilidadeVeiculoInexistente_thenThrowException() {
        String placaInexistente = "ZZZ9999";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> veiculoService.validarDisponibilidade(placaInexistente)
        );

        assertEquals("Veículo não encontrado.", exception.getMessage());
    }

    @Test
    void when_ValidarDisponibilidadeVeiculoExistente_thenReturnDisponibilidade() {
        String placa = "ABC1234";
        String modelo = "Gol";
        String tipo = "pequeno";

        veiculoService.cadastrarVeiculo(placa, modelo, tipo);

        assertTrue(veiculoService.validarDisponibilidade(placa));
    }
}
