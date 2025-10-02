package servicos;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AluguelServiceImplTest {

    @Mock
    private AluguelRepositorio aluguelRepositorio;

    @Mock
    private VeiculoRepositorio veiculoRepositorio;

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @InjectMocks
    private AluguelServiceImpl aluguelService;

    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        cliente = new PessoaFisica("Paula", "12345678901");
        veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);
    }

    // --- ALUGAR VEÍCULO ---

    @Test
    void when_AlugarVeiculoComClienteValidoEVeiculoDisponivel_thenSalvaAluguel() {
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);

        aluguelService.alugarVeiculo(cliente, veiculo);

        assertFalse(veiculo.isDisponivel());
        verify(veiculoRepositorio).atualizar(veiculo);
        verify(aluguelRepositorio).salvar(any(Aluguel.class));
    }

    @Test
    void when_AlugarVeiculoComClienteNaoCadastrado_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                aluguelService.alugarVeiculo(cliente, veiculo)
        );

        assertEquals("Cliente inválido ou não cadastrado.", exception.getMessage());
        verify(veiculoRepositorio, never()).atualizar(any());
        verify(aluguelRepositorio, never()).salvar(any());
    }

    @Test
    void when_AlugarVeiculoIndisponivel_thenThrowException() {
        veiculo.setDisponivel(false);
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                aluguelService.alugarVeiculo(cliente, veiculo)
        );

        assertEquals("Veículo indisponível para aluguel.", exception.getMessage());
        verify(veiculoRepositorio, never()).atualizar(any());
        verify(aluguelRepositorio, never()).salvar(any());
    }

    // --- DEVOLVER VEÍCULO ---

    @Test
    void when_DevolverVeiculoComAluguelAtivo_thenAtualizaAluguelEVeiculo() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now().minusDays(1), null);

        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(veiculo);
        when(aluguelRepositorio.buscarPorItem(veiculo, "veiculo")).thenReturn(aluguel);

        aluguelService.devolverVeiculo(cliente, veiculo);

        assertTrue(veiculo.isDisponivel());
        assertNotNull(aluguel.getDataFim());
        verify(aluguelRepositorio).atualizar(aluguel);
        verify(veiculoRepositorio).atualizar(veiculo);
    }

    @Test
    void when_DevolverVeiculoComClienteNaoCadastrado_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                aluguelService.devolverVeiculo(cliente, veiculo)
        );

        assertEquals("Cliente inválido ou não cadastrado.", exception.getMessage());
        verify(aluguelRepositorio, never()).atualizar(any());
        verify(veiculoRepositorio, never()).atualizar(any());
    }

    @Test
    void when_DevolverVeiculoNaoCadastrado_thenNaoFazNada() {
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(null);

        assertDoesNotThrow(() -> aluguelService.devolverVeiculo(cliente, veiculo));
        verify(aluguelRepositorio, never()).atualizar(any());
        verify(veiculoRepositorio, never()).atualizar(any());
    }

    @Test
    void when_DevolverVeiculoSemAluguelAtivo_thenNaoFazNada() {
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now().minusDays(1), LocalDateTime.now());

        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(veiculo);
        when(aluguelRepositorio.buscarPorItem(veiculo, "veiculo")).thenReturn(aluguel);

        assertDoesNotThrow(() -> aluguelService.devolverVeiculo(cliente, veiculo));

        verify(aluguelRepositorio, never()).atualizar(any());
        verify(veiculoRepositorio, never()).atualizar(any());
    }
}
