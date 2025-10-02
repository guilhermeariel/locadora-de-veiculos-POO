package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.time.LocalDateTime;

public class AluguelServiceImpl implements AluguelService{

    private final AluguelRepositorio aluguelRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public AluguelServiceImpl(AluguelRepositorio aluguelRepositorio, VeiculoRepositorio veiculoRepositorio, ClienteRepositorio clienteRepositorio) {
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
            System.out.println("Veiculo inválido ou não cadastrado.");
            return;
        }

        Aluguel aluguel = aluguelRepositorio.buscarPorItem(veiculo, "veiculo");

        if (aluguel == null || aluguel.getDataFim() != null) {
            System.out.println("Nenhum aluguel ativo encontrado para este veículo.");
            return;
        }

        aluguel.setDataFim(LocalDateTime.now());
        veiculo.setDisponivel(true);

        aluguelRepositorio.atualizar(aluguel);
        veiculoRepositorio.atualizar(veiculo);

        double valorFinal = aluguel.calcularValor();
        System.out.printf("Veículo devolvido com sucesso! Valor total: R$ %.2f\n", valorFinal);
    }

/*    public long calcularDiarias(Aluguel aluguel) {
        if (aluguel.getDataFim() == null) {
            System.out.println("Aluguel ainda não foi finalizado.");
            return 0;
        }

        return ChronoUnit.DAYS.between(
                aluguel.getDataInicio().toLocalDate(),
                aluguel.getDataFim().toLocalDate()
        );
    }

    public void aplicarDesconto(Aluguel aluguel) {
        double valorComDesconto = aluguel.calcularValor();
        System.out.printf("Valor com desconto aplicado: R$ %.2f\n", valorComDesconto);
    }

    public void registrarAluguel(Aluguel aluguel) {
        if (!aluguel.getVeiculo().isDisponivel()) {
            System.out.println("Veículo indisponível para aluguel.");
            return;
        }

        aluguel.getVeiculo().setDisponivel(false);
        aluguelRepositorio.salvar(aluguel);
        System.out.println("Aluguel registrado com sucesso!");
    }*/
}
