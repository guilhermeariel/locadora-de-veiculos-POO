package model;

public class PessoaFisica extends Cliente {
  private String cpf;

  public PessoaFisica(String nome, String cpf) {
    super(nome);
    this.cpf = cpf;
  }
  @Override
  public String toString() {
    return "Pessoa Física {" +
        "nome='" + getNome() + '\'' +
        ", cpf='" + cpf + '\'' +
        '}';
  }


  public String getCpf() {
    return cpf;
  }

  @Override
  public String getIdentificador() {
    return cpf;
  }

  @Override
  public boolean isPessoaFisica() {
    return true;
  }
}
