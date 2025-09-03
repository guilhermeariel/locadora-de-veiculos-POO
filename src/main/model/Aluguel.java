package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Aluguel {
  private static int proximoId = 1;  // ID global incremental
  private final int id;              // ID único do aluguel

  private Cliente cliente;
  private Veiculo veiculo;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;

  private DescontoStrategy descontoStrategy = new DescontoPorTipoCliente();

  public Aluguel(Cliente cliente, Veiculo veiculo, LocalDateTime dataInicio, LocalDateTime dataFim) {
    this.id = proximoId++;
    this.cliente = cliente;
    this.veiculo = veiculo;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
  }

  public double calcularValor() {
    long dias = Duration.between(dataInicio, dataFim).toDays();
    if (Duration.between(dataInicio, dataFim).toHours() % 24 > 0) dias++;

    double total = dias * veiculo.getTipo().getValorDiaria();
    return descontoStrategy.aplicarDesconto(cliente, dias, total);
  }

  public int getIdentificador() {
    return id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public Veiculo getVeiculo() {
    return veiculo;
  }

  public LocalDateTime getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(LocalDateTime dataInicio) {
    this.dataInicio = dataInicio;
  }

  public LocalDateTime getDataFim() {
    return dataFim;
  }

  public void setDataFim(LocalDateTime dataFim) {
    this.dataFim = dataFim;
  }
}
