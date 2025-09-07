package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AluguelService {

  private final AluguelRepository aluguelRepositorio;

  public AluguelService(AluguelRepository aluguelRepositorio) {
    this.aluguelRepositorio = aluguelRepositorio;
  }

  public void alugar(Cliente cliente, Veiculo veiculo) {
    if (!veiculo.isDisponivel()) {
      System.out.println("Veículo indisponível para aluguel.");
      return;
    }

    Aluguel aluguel = new Aluguel(
        cliente,
        veiculo,
        LocalDateTime.now(),
        null
    );

    veiculo.setDisponivel(false);
    aluguelRepositorio.salvar(aluguel);
    System.out.println("Aluguel realizado com sucesso!");
  }

  public void devolver(Cliente cliente, Veiculo veiculo) {
    Aluguel aluguel = aluguelRepositorio.buscarPorVeiculo(veiculo);

    if (aluguel != null) {
      aluguel.setDataFim(LocalDateTime.now());
      veiculo.setDisponivel(true);
      aluguelRepositorio.atualizar(aluguel);

      double valorFinal = aluguel.calcularValor();
      System.out.printf("Veículo devolvido. Total a pagar: R$ %.2f\n", valorFinal);
    } else {
      System.out.println("Nenhum aluguel ativo encontrado para este veículo.");
    }
  }

  public long calcularDiarias(Aluguel aluguel) {
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
  }

}
