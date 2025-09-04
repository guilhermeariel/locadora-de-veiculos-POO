package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.TipoVeiculo;

public class CadastroVeiculo extends AbstractGridMenu{
    private TipoVeiculo tipo;

    @Override
    public void gridMenu() {

        ComboBox<String> comboVeiculoTipo = new ComboBox<>();
        comboVeiculoTipo.getItems().addAll("Pequeno", "Medio", "Suv");
        comboVeiculoTipo.setValue("Pequeno");
        Label labelPlaca = new Label("Placa:");
        TextField entryPlaca = new TextField();
        Label labelModelo = new Label("Modelo:");
        TextField entryModelo = new TextField();
        Button buttonCadastrar = new Button("Cadastrar");

        grid.add(labelPlaca, 0, 0);
        grid.add(entryPlaca, 1, 0);
        grid.add(labelModelo, 0, 1);
        grid.add(entryModelo, 1, 1);
        grid.add(comboVeiculoTipo, 0, 2, 2, 1);
        grid.add(buttonCadastrar, 1, 3);
    }
}
