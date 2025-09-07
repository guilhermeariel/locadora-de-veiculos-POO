package model;

import utils.DateUtils;

import java.time.LocalDateTime;

public class Aluguel {
    private static int proximoId = 1;  // ID global incremental
    private final int id;              // ID único do aluguel

    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Double valorTotal;

    private DescontoStrategy descontoStrategy = new DescontoPorTipoCliente();

    public Aluguel(Cliente cliente, Veiculo veiculo, LocalDateTime dataInicio) {
        this.id = proximoId++;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = null;
        this.valorTotal = null;
    }

    public int getIdentificador() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public DescontoStrategy getDescontoStrategy() {
        return descontoStrategy;
    }

    public double calcularValor() {
        if (this.getDataFim() == null) {
            throw new IllegalStateException("O aluguel ainda não foi finalizado.");
        }

        // Calcula o número de diárias usando DateUtils
        long dias = DateUtils.calcularDiarias(this.getDataInicio(), this.getDataFim());

        // Calcula o total sem desconto
        double total = dias * this.getVeiculo().getTipo().getValorDiaria();

        // Aplica desconto de acordo com tipo de cliente
        return this.getDescontoStrategy().aplicarDesconto(this.getCliente(), dias, total);
    }
}
