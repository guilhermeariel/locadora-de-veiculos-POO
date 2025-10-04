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
import java.util.List;
import java.util.function.Supplier;

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

    // --- Suppliers para gerar aluguéis ---
    private Supplier<Aluguel> aluguelAtivoSupplier;
    private Supplier<Aluguel> aluguelFinalizadoSupplier;

    @BeforeEach
    void setUp() {
        cliente = new PessoaFisica("Paula", "12345678901");
        veiculo = new Veiculo("ABC1234", TipoVeiculo.HATCH, "Fiat Uno", true);

        aluguelAtivoSupplier = () -> new Aluguel(cliente, veiculo, LocalDateTime.now().minusDays(1), null);
        aluguelFinalizadoSupplier = () -> new Aluguel(cliente, veiculo, LocalDateTime.now().minusDays(1), LocalDateTime.now());
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
        Aluguel aluguel = aluguelAtivoSupplier.get();

        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(veiculo);
        when(aluguelRepositorio.getLista()).thenReturn(List.of(aluguel));

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
    void when_DevolverVeiculoNaoCadastrado_thenThrowException() {
        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                aluguelService.devolverVeiculo(cliente, veiculo)
        );

        assertEquals("Veículo inválido ou não cadastrado.", exception.getMessage());
        verify(aluguelRepositorio, never()).atualizar(any());
        verify(veiculoRepositorio, never()).atualizar(any());
    }

    @Test
    void when_DevolverVeiculoSemAluguelAtivo_thenThrowException() {
        Aluguel aluguel = aluguelFinalizadoSupplier.get();

        when(clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador())).thenReturn(cliente);
        when(veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador())).thenReturn(veiculo);
        when(aluguelRepositorio.getLista()).thenReturn(List.of(aluguel));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                aluguelService.devolverVeiculo(cliente, veiculo)
        );

        assertEquals("Nenhum aluguel ativo encontrado para este veículo.", exception.getMessage());
        verify(aluguelRepositorio, never()).atualizar(any());
        verify(veiculoRepositorio, never()).atualizar(any());
    }
}