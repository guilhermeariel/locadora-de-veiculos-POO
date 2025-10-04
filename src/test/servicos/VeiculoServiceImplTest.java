package servicos;

import model.TipoVeiculo;
import model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.VeiculoRepositorio;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceImplTest {

    @Mock
    private VeiculoRepositorio veiculoRepositorio;

    @InjectMocks
    private VeiculoServiceImpl veiculoService;

    private Supplier<Veiculo> criarVeiculo;

    @BeforeEach
    void setUp() {
        // Supplier para criar veículos de teste
        criarVeiculo = () -> new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
    }

    // --- CADASTRAR VEÍCULO ---

    @Test
    void when_CadastrarVeiculoValido_thenVeiculoSalvo() {
        Veiculo veiculo = criarVeiculo.get();
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getPlaca())).thenReturn(null);

        veiculoService.cadastrar(veiculo);

        verify(veiculoRepositorio, times(1)).salvar(veiculo);
    }

    @Test
    void when_CadastrarVeiculoDuplicado_thenNaoSalva() {
        Veiculo veiculo = criarVeiculo.get();
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getPlaca())).thenReturn(veiculo);

        veiculoService.cadastrar(veiculo);

        verify(veiculoRepositorio, never()).salvar(any());
    }

    @Test
    void when_CadastrarVeiculoViaParametrosValido_thenVeiculoSalvo() {
        when(veiculoRepositorio.buscarPorIdentificador("ABC1234")).thenReturn(null);

        veiculoService.cadastrarVeiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno");

        verify(veiculoRepositorio, times(1)).salvar(any(Veiculo.class));
    }

    @Test
    void when_CadastrarVeiculoComPlacaInvalida_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo("INV", TipoVeiculo.HATCH, "Modelo X")
        );

        assertEquals("Placa inválida", exception.getMessage());
        verify(veiculoRepositorio, never()).salvar(any());
    }

    @Test
    void when_CadastrarVeiculoDuplicadoViaParametros_thenThrowException() {
        when(veiculoRepositorio.buscarPorIdentificador("ABC1234")).thenReturn(criarVeiculo.get());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.cadastrarVeiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno")
        );

        assertEquals("Já existe um veículo cadastrado com essa placa.", exception.getMessage());
        verify(veiculoRepositorio, never()).salvar(any());
    }

    // --- BUSCAR VEÍCULO ---

    @Test
    void when_BuscarVeiculoExistente_thenReturnVeiculo() {
        Veiculo veiculo = criarVeiculo.get();
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getPlaca())).thenReturn(veiculo);

        Veiculo resultado = veiculoService.buscarPorPlaca(veiculo.getPlaca());

        assertNotNull(resultado);
        assertEquals("ABC1234", resultado.getPlaca());
    }

    @Test
    void when_BuscarVeiculoInexistente_thenThrowException() {
        when(veiculoRepositorio.buscarPorIdentificador("XYZ9999")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.buscarPorPlaca("XYZ9999")
        );

        assertEquals("Veículo não encontrado.", exception.getMessage());
    }

    // --- LISTAR VEÍCULOS ALUGADOS ---

    @Test
    void when_ListarVeiculosAlugados_thenRetornaApenasIndisponiveis() {
        Veiculo v1 = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", false);
        Veiculo v2 = new Veiculo("DEF5678", TipoVeiculo.SEDAN, "Toyota Corolla", true);

        when(veiculoRepositorio.getLista()).thenReturn(List.of(v1, v2));

        List<Veiculo> alugados = veiculoService.listarVeiculosAlugados();

        assertEquals(1, alugados.size());
        assertFalse(alugados.getFirst().isDisponivel());
        assertEquals("ABC1234", alugados.getFirst().getPlaca());
    }

    // --- REMOVER VEÍCULO ---

    @Test
    void when_RemoverVeiculoExistente_thenVeiculoRemovido() {
        Veiculo veiculo = criarVeiculo.get();
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getPlaca())).thenReturn(veiculo);

        veiculoService.removerVeiculo(veiculo.getPlaca());

        verify(veiculoRepositorio, times(1)).removerItem(veiculo);
    }

    @Test
    void when_RemoverVeiculoInexistente_thenThrowException() {
        when(veiculoRepositorio.buscarPorIdentificador("XYZ9999")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                veiculoService.removerVeiculo("XYZ9999")
        );

        assertEquals("Veículo não encontrado.", exception.getMessage());
        verify(veiculoRepositorio, never()).removerItem(any());
    }
}