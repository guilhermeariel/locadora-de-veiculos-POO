package model;

public abstract class Cliente {
  protected String nome;

  public Cliente(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public abstract String getIdentificador();
  public abstract boolean isPessoaFisica();
}
