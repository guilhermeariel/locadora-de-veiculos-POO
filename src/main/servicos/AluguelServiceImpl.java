package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

public class AluguelServiceImpl implements AluguelService {

    private final AluguelRepositorio aluguelRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    // Function para calcular valor do aluguel
    private final Function<Aluguel, Double> calcularValor = Aluguel::calcularValor;

    // Consumer para imprimir valor formatado
    private final Consumer<Double> imprimirValor = valor ->
            System.out.printf("Veículo devolvido com sucesso! Valor total: R$ %.2f\n", valor);

    public AluguelServiceImpl(
            AluguelRepositorio aluguelRepositorio,
            VeiculoRepositorio veiculoRepositorio,
            ClienteRepositorio clienteRepositorio
    ) {
        this.aluguelRepositorio = aluguelRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public void alugarVeiculo(Cliente cliente, Veiculo veiculo) {
        if (cliente == null || clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador()) == null) {
            throw new IllegalArgumentException("Cliente inválido ou não cadastrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new IllegalArgumentException("Veículo indisponível para aluguel.");
        }

        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now(), null);

        // Atualizar disponibilidade do veículo
        veiculo.setDisponivel(false);
        veiculoRepositorio.atualizar(veiculo);

        aluguelRepositorio.salvar(aluguel);
    }

    @Override
    public void devolverVeiculo(Cliente cliente, Veiculo veiculo) {
        if (cliente == null || clienteRepositorio.buscarPorIdentificador(cliente.getIdentificador()) == null) {
            throw new IllegalArgumentException("Cliente inválido ou não cadastrado.");
        }

        if (veiculo == null || veiculoRepositorio.buscarPorIdentificador(veiculo.getIdentificador()) == null) {
            throw new IllegalArgumentException("Veículo inválido ou não cadastrado.");
        }

        Aluguel aluguel = buscarAluguelAtivoDeVeiculo(veiculo);

        if (aluguel == null) {
            throw new IllegalArgumentException("Nenhum aluguel ativo encontrado para este veículo.");
        }

        aluguel.setDataFim(LocalDateTime.now());
        veiculo.setDisponivel(true);

        aluguelRepositorio.atualizar(aluguel);
        veiculoRepositorio.atualizar(veiculo);

        // Calcular valor e imprimir
        double valorFinal = calcularValor.apply(aluguel);
        imprimirValor.accept(valorFinal);
    }

    private Aluguel buscarAluguelAtivoDeVeiculo(Veiculo veiculo) {
        return aluguelRepositorio.getLista().stream()
                .filter(a -> a.getVeiculo().getIdentificador().equals(veiculo.getIdentificador())
                        && a.getDataFim() == null)
                .findFirst()
                .orElse(null);
    }
}
