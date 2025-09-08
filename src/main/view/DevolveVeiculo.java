package view;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

public class DevolveVeiculo extends AbstractGridMenu {
    private AluguelRepositorio repositorio;
    private ClienteRepositorio clienteRepositorio;
    private VeiculoRepositorio veiculoRepositorio;

    public DevolveVeiculo(AluguelRepositorio repositorio,
                          ClienteRepositorio clienteRepositorio,
                          VeiculoRepositorio veiculoRepositorio) {
        this.repositorio = repositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
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
    }
}
