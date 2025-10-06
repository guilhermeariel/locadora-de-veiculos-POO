package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

public class Aluguel implements Serializable {

    private static final Supplier<Integer> idGenerator = new Supplier<>() {
        private int proximoId = 1;
        @Override public Integer get() { return proximoId++; }
    };

    public static final Function<Aluguel, Long> calcularDias = aluguel -> {
        long dias = Duration.between(aluguel.getDataInicio(), aluguel.getDataFim()).toDays();
        return (Duration.between(aluguel.getDataInicio(), aluguel.getDataFim()).toHours() % 24 > 0) ? dias + 1 : dias;
    };

    private final int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private DescontoStrategy descontoStrategy = new DescontoPorTipoCliente();

    public Aluguel(Cliente cliente, Veiculo veiculo, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.id = idGenerator.get();
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public double calcularValor() {
        long dias = calcularDias.apply(this);
        double total = dias * veiculo.getTipo().getValorDiaria();
        return descontoStrategy.aplicarDesconto(cliente, dias, total);
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

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    // (Opcional) Para facilitar prints e debug
    @Override
    public String toString() {
        return "Aluguel ID: " + id + ", Cliente: " + cliente.getNome()
                + ", Veículo: " + veiculo.getModelo()
                + ", Início: " + dataInicio
                + ", Fim: " + dataFim;
    }
}
