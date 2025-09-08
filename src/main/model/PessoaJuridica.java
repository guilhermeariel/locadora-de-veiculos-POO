package model;

public class PessoaJuridica extends Cliente {
  private final String cnpj;

  public PessoaJuridica(String nome, String cnpj) {
    super(nome);
    this.cnpj = cnpj;
  }
  @Override
  public String toString() {
    return "Pessoa Jurídica {" +
        "nome='" + getNome() + '\'' +
        ", cnpj='" + cnpj + '\'' +
        '}';
  }

  public String getCnpj() {
    return cnpj;
  }

  @Override
  public String getIdentificador() {
    return cnpj;
  }

  @Override
  public boolean isPessoaFisica() {
    return false;
  }
}
