package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.time.LocalDateTime;

public class AluguelServiceImpl implements AluguelService {

    private final AluguelRepositorio aluguelRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public AluguelServiceImpl(AluguelRepositorio aluguelRepositorio, VeiculoRepositorio veiculoRepositorio, ClienteRepositorio clienteRepositorio) {
        this.aluguelRepositorio = aluguelRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void alugar(Cliente cliente, Veiculo veiculo) {
        if (cliente == null || clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador()) == null) {
            throw new IllegalArgumentException("Cliente inválido ou não cadastrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalArgumentException("Veículo indisponível para aluguel.");
        }

        veiculo.setDisponivel(false);
        veiculoRepositorio.atualizar(veiculo);

        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());

        aluguelRepositorio.salvar(aluguel);
    }

    @Override
    public void devolver(Cliente cliente, Veiculo veiculo) {
        Aluguel aluguel = buscarAluguelPorVeiculo(veiculo);

        if (!aluguel.getCliente().equals(cliente)) {
            throw new IllegalArgumentException("O cliente informado não é o responsável por este aluguel.");
        }

        aluguel.setDataFim(LocalDateTime.now());
        double valorFinal = aluguel.calcularValor();
        aluguel.setValorTotal(valorFinal);
        aluguelRepositorio.atualizar(aluguel);

        veiculo.setDisponivel(true);
        veiculoRepositorio.atualizar(veiculo);
    }

    @Override
    public Aluguel buscarAluguelPorVeiculo(Veiculo veiculo) {
        return aluguelRepositorio.listar().stream()
                .filter(a -> a.getVeiculo().equals(veiculo) && a.getDataFim() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhum aluguel ativo encontrado para o veículo: " + veiculo.getPlaca()));
    }
}
