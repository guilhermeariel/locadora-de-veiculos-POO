package model;

public class PessoaJuridica extends Cliente {
  private String cnpj;

  public PessoaJuridica(String nome, String cnpj) {
    super(nome);
    this.cnpj = cnpj;
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
