package model;

public interface DescontoStrategy {
  double aplicarDesconto(Cliente cliente, long dias, double total);
}
