package main.model;

public class Veiculo {
  private String placa;
  private String modelo;
  private TipoVeiculo tipo;

  public Veiculo(String placa, String modelo, TipoVeiculo tipo) {
    this.placa = placa;
    this.modelo = modelo;
    this.tipo = tipo;
  }

  public String getPlaca() { return placa; }
  public String getModelo() { return modelo; }
  public TipoVeiculo getTipo() { return tipo; }
}
