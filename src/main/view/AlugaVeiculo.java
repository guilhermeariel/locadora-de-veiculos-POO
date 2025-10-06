package view;

import javafx.scene.Node;
import javafx.scene.control.*;
import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import javafx.beans.value.ChangeListener;
import servicos.AluguelServiceImpl;
import utils.ValidationPredicates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AlugaVeiculo extends AbstractGridMenu{
    private final AluguelRepositorio aluguelRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final AluguelServiceImpl aluguelService;
    private final ListaVeiculos listaVeiculos = new ListaVeiculos();

    public AlugaVeiculo(AluguelRepositorio aluguelRepositorio,
                        ClienteRepositorio clienteRepositorio,
                        VeiculoRepositorio veiculoRepositorio) {
        this.aluguelRepositorio = aluguelRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.aluguelService = new AluguelServiceImpl(aluguelRepositorio, veiculoRepositorio, clienteRepositorio);
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
        entryInicio.setDisable(true);
        entryInicio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        TextField entryHoraInicio = ValidatedTextField.criaEntryHora();
        entryHoraInicio.setDisable(true);
        entryHoraInicio.setText(LocalTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("HH:mm")));
        TextField entryFim = ValidatedTextField.criaEntryData();
        TextField entryHoraFim = ValidatedTextField.criaEntryHora();
        TextField entryDiarias = new TextField();
        entryDiarias.setEditable(false);
        ComboBox<String> comboVeiculo = new ComboBox<>();
        comboVeiculo.getItems().addAll("Hatch", "Sedan", "Suv");
        comboVeiculo.setValue("Tipo de Veiculo");
        Button buttonBuscar = new Button("Buscar Veículo");
        Label labelVeiculo = new Label("Veículo:");
        TextField entryVeiculo = ValidatedTextField.criaEntryPlaca();
        entryVeiculo.setEditable(false);
        Button buttonConfirmar = new Button("Confirmar Aluguel");
        buttonConfirmar.setDisable(true);

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

        ChangeListener<String> ativaBotao = (obs, oldText, newText) -> {
            boolean clienteValido = ValidationPredicates.ehDocumentoValido(entryCliente.getText()) &&
                                   clienteRepositorio.buscarPorIdentificador(entryCliente.getText()) != null;
            boolean veiculoSelecionado = !entryVeiculo.getText().isEmpty();
            boolean diariasValidas = !entryDiarias.getText().isEmpty() && Integer.parseInt(entryDiarias.getText()) > 0;
            buttonConfirmar.setDisable(!(clienteValido && veiculoSelecionado && diariasValidas));
        };

        buttonBuscar.setOnAction(e -> {
            String tipoVeiculo = comboVeiculo.getValue();
            VeiculoRepositorio filtroVeiculos = veiculoRepositorio.filtrar("tipo", tipoVeiculo);
            listaVeiculos.setRepositorio(filtroVeiculos);
            listaVeiculos.startStage();
            Veiculo veiculoSelecionado = listaVeiculos.getVeiculoSelecionado();
            entryVeiculo.setText(veiculoSelecionado.getPlaca());
        });

        entryInicio.textProperty().addListener(listener);
        entryFim.textProperty().addListener(listener);
        entryHoraInicio.textProperty().addListener(listener);
        entryHoraFim.textProperty().addListener(listener);

        for(Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).textProperty().addListener(ativaBotao);
            }
        }

        buttonConfirmar.setOnAction(e -> {
            String documento = entryCliente.getText().trim();
            String placa = entryVeiculo.getText().trim();
            Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);
            Veiculo veiculo = veiculoRepositorio.buscarPorIdentificador(placa);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Aluguel de Veículo");

            try {
                if (!veiculo.isDisponivel()){
                    throw new IllegalArgumentException("Veículo não está disponível para aluguel");
                }
                aluguelService.alugarVeiculo(cliente, veiculo);
                Aluguel aluguel = aluguelRepositorio.buscarPorItem(cliente, "cliente");
                if (aluguel != null && aluguel.getVeiculo().getPlaca().equals(placa)
                    && aluguel.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        .equals(entryInicio.getText())) {
                    alert.setContentText("Aluguel confirmado");
                }
                else {
                    alert.setContentText("Erro ao confirmar aluguel");
                }
            } catch (IllegalArgumentException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
            }
            alert.showAndWait();
        });
    }

    public void calculaDiaria(TextField inicio, TextField horaInicio, TextField fim, TextField horaFim, TextField diarias) {
        String dataInicio = inicio.getText();
        String dataFim = fim.getText();
        String hInicio = horaInicio.getText();
        String hFim = horaFim.getText();
        if (ValidationPredicates.ehDataValida(dataInicio) && ValidationPredicates.ehDataValida(dataFim) &&
                ValidationPredicates.ehHoraValida(hInicio) && ValidationPredicates.ehHoraValida(hFim)) {

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
