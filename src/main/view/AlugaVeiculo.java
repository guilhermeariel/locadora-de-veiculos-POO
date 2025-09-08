package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Cliente;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.beans.value.ChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
        Label labelInicio = new Label("Data de Retirada (dd/mm/aaaa):");
        Label horaInicio = new Label("Hora de Retirada (hh:mm):");
        Label labelFim = new Label("Data de Devolução (dd/mm/aaaa):");
        Label horaFim = new Label("Hora de Devolução (hh:mm):");
        Label diarias = new Label("Diárias:");
        TextField entryCliente = ValidatedTextField.criaEntryDocumento();
        TextField entryInicio = ValidatedTextField.criaEntryData();
        TextField entryHoraInicio = ValidatedTextField.criaEntryHora();
        TextField entryFim = ValidatedTextField.criaEntryData();
        TextField entryHoraFim = ValidatedTextField.criaEntryHora();
        TextField entryDiarias = new TextField();
        entryDiarias.setEditable(false);
        ComboBox<String> comboVeiculo = new ComboBox<>();
        comboVeiculo.getItems().addAll("Pequeno", "Medio", "Suv");
        comboVeiculo.setValue("Tipo de Veiculo");
        Button buttonBuscar = new Button("Buscar Veículo");
        Label labelVeiculo = new Label("Veículo:");
        TextField entryVeiculo = ValidatedTextField.criaEntryPlaca();
        entryVeiculo.setEditable(false);
        Button buttonConfirmar = new Button("Confirmar Aluguel");

        grid.add(labelCliente, 0, 0);
        grid.add(entryCliente, 1, 0);
        grid.add(labelInicio, 0, 1);
        grid.add(entryInicio, 1, 1);
        grid.add(horaInicio, 0, 2);
        grid.add(entryHoraInicio, 1, 2);
        grid.add(labelFim, 0, 3);
        grid.add(entryFim, 1, 3);
        grid.add(horaFim, 0, 4);
        grid.add(entryHoraFim, 1, 4);
        grid.add(diarias, 0, 5);
        grid.add(entryDiarias, 1, 5);
        grid.add(comboVeiculo, 0, 6, 2, 1);
        grid.add(buttonBuscar, 1, 6);
        grid.add(labelVeiculo, 0, 7);
        grid.add(entryVeiculo, 1, 7);
        grid.add(buttonConfirmar, 1, 8);

        ChangeListener<String> listener = (obs, oldText, newText) -> {
            calculaDiaria(entryInicio, entryHoraInicio, entryFim, entryHoraFim, entryDiarias);
        };

        entryInicio.textProperty().addListener(listener);
        entryFim.textProperty().addListener(listener);
        entryHoraInicio.textProperty().addListener(listener);
        entryHoraFim.textProperty().addListener(listener);
    }

    public void calculaDiaria(TextField inicio, TextField horaInicio, TextField fim, TextField horaFim, TextField diarias) {
        String dataInicio = inicio.getText();
        String dataFim = fim.getText();
        String hInicio = horaInicio.getText();
        String hFim = horaFim.getText();
        if (Validations.dataValida(dataInicio) && Validations.dataValida(dataFim) &&
            Validations.horaValida(hInicio) && Validations.horaValida(hFim)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicioDate = LocalDate.parse(dataInicio, formatter);
            LocalDate fimDate = LocalDate.parse(dataFim, formatter);
            long dias = ChronoUnit.DAYS.between(inicioDate, fimDate);

            DateTimeFormatter formatterHoras = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime inicioHora = LocalTime.parse(hInicio, formatterHoras);
            LocalTime fimHora = LocalTime.parse(hFim, formatterHoras);
            if (fimHora.isAfter(inicioHora)) {
                dias += 1;
            }

            if (dias >= 1) {
                diarias.setText(String.valueOf(dias));
            } else {
                diarias.setText("");
            }
        } else {
            diarias.setText("");
        }
    }
}
