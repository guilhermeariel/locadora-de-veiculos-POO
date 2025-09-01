package model;

public enum TipoVeiculo {
  MANUAL(100.0),
  AUTOMATICO(150.0),
  ELETRICO(200.0);

  private final double valorDiaria;

  TipoVeiculo(double valorDiaria) {
    this.valorDiaria = valorDiaria;
  }

  public double getValorDiaria() {
    return valorDiaria;
  }
}
