package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Cliente;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlugaVeiculo extends AbstractGridMenu{
    private AluguelRepositorio repositorio;
    private ClienteRepositorio clienteRepositorio;
    private VeiculoRepositorio veiculoRepositorio;

    public AlugaVeiculo(AluguelRepositorio repositorio,
                        ClienteRepositorio clienteRepositorio,
                        VeiculoRepositorio veiculoRepositorio) {
        this.repositorio = repositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
    }

    @Override
    protected void gridMenu() {
        Label labelCliente = new Label("CPF/CNPJ do Cliente:");
        Label labelInicio = new Label("Data de Início (dd/mm/aaaa):");
        Label labelFim = new Label("Data de Devolução (dd/mm/aaaa):");
        Label horaFim = new Label("Hora de Devolução (hh:mm):");
        TextField entryCliente = new TextField();
        TextField entryInicio = new TextField();
        TextField entryFim = new TextField();
        TextField entryHoraFim = new TextField();
        ComboBox<String> comboVeiculo = new ComboBox<>();
        comboVeiculo.getItems().addAll("Pequeno", "Medio", "Suv");
        comboVeiculo.setValue("Pequeno");
        Button buttonAlugar = new Button("Buscar Veículo");
        Label labelVeiculo = new Label("Veículo:");
        TextField entryVeiculo = new TextField();
        entryVeiculo.setEditable(false);
        Button buttonConfirmar = new Button("Confirmar Aluguel");

        grid.add(labelCliente, 0, 0);
        grid.add(entryCliente, 1, 0);
        grid.add(labelInicio, 0, 1);
        grid.add(entryInicio, 1, 1);
        grid.add(labelFim, 0, 2);
        grid.add(entryFim, 1, 2);
        grid.add(horaFim, 0, 3);
        grid.add(entryHoraFim, 1, 3);
        grid.add(comboVeiculo, 0, 4, 2, 1);
        grid.add(buttonAlugar, 1, 5);
        grid.add(labelVeiculo, 0, 6);
        grid.add(entryVeiculo, 1, 6);
        grid.add(buttonConfirmar, 1, 7);

    }


}
