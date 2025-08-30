package model;

import model.cliente.Cliente;

public interface DescontoStrategy {
  double aplicarDesconto(Cliente cliente, long dias, double total);
}
