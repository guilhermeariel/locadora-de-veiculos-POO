package servicos;

import model.Aluguel;
import model.Cliente;
import model.PessoaFisica;
import model.Veiculo;
import model.TipoVeiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AluguelServiceImplTest {

    private AluguelServiceImpl aluguelService;
    private ClienteRepositorio clienteRepositorio;
    private VeiculoRepositorio veiculoRepositorio;
    private AluguelRepositorio aluguelRepositorio;

    private Cliente cliente;
    private Veiculo veiculo;

    @BeforeEach
    void setup() {
        clienteRepositorio = new ClienteRepositorio();
        veiculoRepositorio = new VeiculoRepositorio();
        aluguelRepositorio = new AluguelRepositorio();

        aluguelService = new AluguelServiceImpl(aluguelRepositorio, veiculoRepositorio, clienteRepositorio);

        cliente = new PessoaFisica("Paula", "12345678900");
        veiculo = new Veiculo("ABC1234", "Gol", TipoVeiculo.PEQUENO);

        clienteRepositorio.salvar(cliente);
        veiculoRepositorio.salvar(veiculo);
    }

    @Test
    void when_AlugarVeiculoDisponivel_thenAluguelCriado() {
        aluguelService.alugar(cliente, veiculo);

        assertFalse(veiculo.isDisponivel());
        List<Aluguel> alugueis = aluguelRepositorio.listar();
        assertEquals(1, alugueis.size());
        assertEquals(cliente, alugueis.getFirst().getCliente());
        assertEquals(veiculo, alugueis.getFirst().getVeiculo());
        assertNotNull(alugueis.getFirst().getDataInicio());
        assertNull(alugueis.getFirst().getDataFim());
    }

    @Test
    void when_AlugarVeiculoIndisponivel_thenThrowException() {
        veiculo.setDisponivel(false);
        veiculoRepositorio.atualizar(veiculo);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            aluguelService.alugar(cliente, veiculo);
        });

        assertEquals("Veículo indisponível para aluguel.", e.getMessage());
    }

    @Test
    void when_AlugarClienteNaoCadastrado_thenThrowException() {
        Cliente novoCliente = new PessoaFisica("João", "99999999999");

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            aluguelService.alugar(novoCliente, veiculo);
        });

        assertEquals("Cliente inválido ou não cadastrado.", e.getMessage());
    }

    @Test
    void when_DevolverVeiculoAlugadoPorMesmoCliente_thenVeiculoDisponivel() {
        aluguelService.alugar(cliente, veiculo);
        aluguelService.devolver(cliente, veiculo);

        assertTrue(veiculo.isDisponivel());
        Aluguel aluguel = aluguelRepositorio.listar().getFirst();
        assertNotNull(aluguel.getDataFim());
        assertNotNull(aluguel.getValorTotal());
    }

    @Test
    void when_DevolverVeiculoPorClienteDiferente_thenThrowException() {
        aluguelService.alugar(cliente, veiculo);
        Cliente outroCliente = new PessoaFisica("Maria", "11122233344");
        clienteRepositorio.salvar(outroCliente);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            aluguelService.devolver(outroCliente, veiculo);
        });

        assertEquals("O cliente informado não é o responsável por este aluguel.", e.getMessage());
    }

    @Test
    void when_DevolverVeiculoNaoAlugado_thenThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            aluguelService.devolver(cliente, veiculo);
        });

        assertEquals("Nenhum aluguel ativo encontrado para o veículo: " + veiculo.getPlaca(), e.getMessage());
    }


    @Test
    void when_BuscarAluguelPorVeiculoAtivo_thenReturnAluguel() {
        aluguelService.alugar(cliente, veiculo);

        Aluguel aluguelEncontrado = aluguelService.buscarAluguelPorVeiculo(veiculo);

        assertNotNull(aluguelEncontrado);
        assertEquals(cliente, aluguelEncontrado.getCliente());
        assertEquals(veiculo, aluguelEncontrado.getVeiculo());
    }

    @Test
    void when_BuscarAluguelPorVeiculoSemAluguel_thenThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            aluguelService.buscarAluguelPorVeiculo(veiculo);
        });

        assertEquals("Nenhum aluguel ativo encontrado para o veículo: " + veiculo.getPlaca(), e.getMessage());
    }
}
