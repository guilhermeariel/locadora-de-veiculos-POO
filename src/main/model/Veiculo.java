package model;

public class Veiculo {
  private String placa;
  private String modelo;
  private TipoVeiculo tipo;
  private boolean disponivel = true; // padrão: disponível

  public Veiculo(String placa, String modelo, TipoVeiculo tipo) {
    this.placa = placa;
    this.modelo = modelo;
    this.tipo = tipo;
    this.disponivel = true;
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

}
