package view;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;
import servicos.AluguelService;

public class DevolveVeiculo extends AbstractGridMenu {
    private final AluguelRepositorio repositorio;
    private final ClienteRepositorio clienteRepositorio;
    private VeiculoRepositorio veiculoRepositorio;
    private final AluguelService aluguelService;

    public DevolveVeiculo(AluguelRepositorio repositorio,
                          ClienteRepositorio clienteRepositorio,
                          VeiculoRepositorio veiculoRepositorio) {
        this.repositorio = repositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.aluguelService = new AluguelService(repositorio);
    }

    @Override
    protected void gridMenu() {
        Label labelCliente = new Label("CPF/CNPJ do Cliente:");
        TextField entryCliente = ValidatedTextField.criaEntryDocumento();
        Button buttonDevolver = new Button("Devolver Veículo");
        buttonDevolver.setDisable(true);

        grid.add(labelCliente, 0, 0);
        grid.add(entryCliente, 1, 0);
        grid.add(buttonDevolver, 1, 1);

        ChangeListener<String> ativaBotao = (obs, oldText, newText) -> {
            buttonDevolver.setDisable(!Validations.documentoValido(newText));
        };
        entryCliente.textProperty().addListener(ativaBotao);

        buttonDevolver.setOnAction(e -> {
            String documento = entryCliente.getText().trim();
            Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);
            Aluguel aluguel = repositorio.buscarPorItem(cliente, "cliente");
            Veiculo veiculo = aluguel.getVeiculo();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Devolução de Veículo");
            try {
                aluguelService.devolver(cliente, veiculo);
                alerta.setHeaderText("Veículo devolvido com sucesso!");
                alerta.setContentText(String.format("Valor total: R$ %.2f", aluguel.calcularValor()));
                alerta.showAndWait();
            }
            catch (Exception ex) {
                alerta.setAlertType(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao devolver veículo");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });
    }
}
