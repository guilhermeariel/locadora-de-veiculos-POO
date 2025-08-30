package model;

import model.cliente.Cliente;

public class DescontoPorTipoCliente implements DescontoStrategy {

  @Override
  public double aplicarDesconto(Cliente cliente, long dias, double total) {
    if (cliente.isPessoaFisica() && dias > 5) {
      return total * 0.95;
    } else if (!cliente.isPessoaFisica() && dias > 3) {
      return total * 0.90;
    }
    return total;
  }
}