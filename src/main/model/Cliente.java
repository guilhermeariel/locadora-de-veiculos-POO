package model;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
  protected String nome;

  public Cliente(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
      this.nome = nome;
  }

  public abstract String getIdentificador();
  public abstract boolean isPessoaFisica();
}
