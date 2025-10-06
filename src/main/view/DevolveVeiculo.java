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
import servicos.AluguelServiceImpl;
import utils.ValidationPredicates;

public class DevolveVeiculo extends AbstractGridMenu {
    private final AluguelRepositorio aluguelRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final AluguelServiceImpl aluguelService;

    public DevolveVeiculo(AluguelRepositorio aluguelRepositorio,
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
        TextField entryCliente = ValidatedTextField.criaEntryDocumento();
        Button buttonDevolver = new Button("Devolver Veículo");
        buttonDevolver.setDisable(true);

        grid.add(labelCliente, 0, 0);
        grid.add(entryCliente, 1, 0);
        grid.add(buttonDevolver, 1, 1);

        ChangeListener<String> ativaBotao = (obs, oldText, newText) -> {
            buttonDevolver.setDisable(!ValidationPredicates.ehDocumentoValido(newText));
        };
        entryCliente.textProperty().addListener(ativaBotao);

        buttonDevolver.setOnAction(e -> {
            String documento = entryCliente.getText().trim();
            Cliente cliente = clienteRepositorio.buscarPorIdentificador(documento);
            Aluguel aluguel = aluguelRepositorio.buscarPorItem(cliente, "cliente");
            Veiculo veiculo = aluguel.getVeiculo();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Devolução de Veículo");
            try {
                aluguelService.devolverVeiculo(cliente, veiculo);
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
