package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Aluguel {
  private model.cliente.Cliente cliente;
  private Veiculo veiculo;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;

  private DescontoStrategy descontoStrategy = new DescontoPorTipoCliente();

  public Aluguel(Veiculo veiculo, long dias) {
    this.cliente = cliente;
    this.veiculo = veiculo;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
  }

  public double calcularValorTotal() {
    long dias = Duration.between(dataInicio, dataFim).toDays();
    if (Duration.between(dataInicio, dataFim).toHours() % 24 > 0) dias++;

    double total = dias * veiculo.getTipo().getValorDiaria();
    return descontoStrategy.aplicarDesconto(cliente, dias, total);
  }

  public model.cliente.Cliente getCliente() {
    return cliente;
  }

  public Veiculo getVeiculo() {
    return veiculo;
  }

}
