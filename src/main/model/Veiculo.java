package model;

public class Veiculo {
  private String placa;
  private String modelo;
  private TipoVeiculo tipo;
  private boolean disponivel = true; // padrão: disponível

  public Veiculo(String placa, TipoVeiculo tipo, String modelo, boolean disponivel) {
    this.placa = placa;
    this.tipo = tipo;
    this.modelo = modelo;
    this.disponivel = disponivel;
  }

  public String getPlaca() {
    return placa;
  }

  public String getModelo() {
    return modelo;
  }

  public TipoVeiculo getTipo() {
    return tipo;
  }

  public boolean isDisponivel() {
    return disponivel;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }

  public String getIdentificador() {
    return this.placa;
  }

  @Override
  public String toString() {
    return "Veículo{" +
        "placa='" + placa + '\'' +
        ", modelo='" + modelo + '\'' +
        ", tipo=" + tipo +
        ", disponível=" + disponivel +
        '}';
  }
}
