package model;

public enum TipoVeiculo {
  ELETRICO(150.0),
  AUTOMATICO(200.0),
  MANUAL(100.0);

  private final double valorDiaria;

  TipoVeiculo(double valorDiaria) {
    this.valorDiaria = valorDiaria;
  }

  public double getValorDiaria() {
    return valorDiaria;
  }
}
