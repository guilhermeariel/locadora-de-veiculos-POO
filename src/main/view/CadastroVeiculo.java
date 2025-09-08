package view;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import model.TipoVeiculo;
import model.Veiculo;
import repository.VeiculoRepositorio;
import servicos.VeiculoService;

public class CadastroVeiculo extends AbstractGridMenu{
    private TipoVeiculo tipo;
    private final VeiculoRepositorio repositorio;
    private final VeiculoService service;

    public CadastroVeiculo(VeiculoRepositorio repositorio){
        this.repositorio = repositorio;
        this.service = new VeiculoService(repositorio);
    }

    @Override
    public void gridMenu() {

        ComboBox<String> comboVeiculoTipo = new ComboBox<>();
        comboVeiculoTipo.getItems().addAll("Hatch", "Sedan", "Suv");
        comboVeiculoTipo.setValue("Hatch");
        Label labelPlaca = new Label("Placa:");
        TextField entryPlaca = ValidatedTextField.criaEntryPlaca();
        Label labelModelo = new Label("Modelo:");
        TextField entryModelo = new TextField();
        Button buttonCadastrar = new Button("Cadastrar");
        buttonCadastrar.setDisable(true);

        grid.add(labelPlaca, 0, 0);
        grid.add(entryPlaca, 1, 0);
        grid.add(labelModelo, 0, 1);
        grid.add(entryModelo, 1, 1);
        grid.add(comboVeiculoTipo, 0, 2, 2, 1);
        grid.add(buttonCadastrar, 1, 3);

        ChangeListener<String> changeListener = (obs, old, neu) -> {
            boolean placaValida = Validations.placaValida(entryPlaca.getText());
            boolean modeloValido = entryModelo.getText() != null && !entryModelo.getText().trim().isEmpty();
            buttonCadastrar.setDisable(!(placaValida && modeloValido));
        };

        entryPlaca.textProperty().addListener(changeListener);
        entryModelo.textProperty().addListener(changeListener);
        comboVeiculoTipo.valueProperty().addListener(changeListener);

        buttonCadastrar.setOnAction(e -> {
            String placa = entryPlaca.getText();
            String modelo = entryModelo.getText();
            String tipoSelecionado = comboVeiculoTipo.getValue();

            if (tipoSelecionado.equals("Hatch")) {
                tipo = TipoVeiculo.HATCH;
            } else if (tipoSelecionado.equals("Sedan")) {
                tipo = TipoVeiculo.SEDAN;
            } else {
                tipo = TipoVeiculo.SUV;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Adição de Veículo");
            try {
                service.cadastrarVeiculo(placa, tipo, modelo);
                Veiculo veiculo = repositorio.buscarPorIdentificador(placa);
                alert.setContentText("Veículo cadastrado com sucesso?\n" + veiculo.toString());
            }
            catch (IllegalArgumentException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
            }
            alert.showAndWait();
        });

    }
}
