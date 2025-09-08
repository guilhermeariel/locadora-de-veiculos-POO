package servicos;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AluguelService {

  private final AluguelRepositorio aluguelRepositorio;

  public AluguelService(AluguelRepositorio aluguelRepositorio) {
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
    if (cliente == null) {
      System.out.println("Cliente não encontrado.");
      return;
    }

    if (veiculo == null) {
      System.out.println("Veículo não encontrado.");
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

    double valorFinal = aluguel.calcularValor();
    System.out.printf("Veículo devolvido com sucesso! Valor total: R$ %.2f\n", valorFinal);
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
