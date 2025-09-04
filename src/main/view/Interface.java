package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class Interface extends Application {
    private VBox root;

    String[] listaMenu = new String[]{"Cadastrar Cliente", "Cadastrar Veículo",
            "Buscar Cliente", "Buscar Veículo",
            "Alugar Veículo", "Devolver Veículo"};

    CadastroCliente cadastroCliente = new CadastroCliente();
    CadastroVeiculo cadastroVeiculo = new CadastroVeiculo();

    @Override
    public void start(javafx.stage.Stage stage) {
        root = new VBox(10);

        Scene scene = new Scene(root, 300, 200);

        ComboBox<String> comboMenu = new ComboBox<>();
        comboMenu.getItems().addAll(listaMenu);
        comboMenu.setValue("Cadastrar Cliente");
        VBox boxModeSelect = new VBox(10, comboMenu);
        root.getChildren().add(boxModeSelect);

        stage.setTitle("MeLoCaliza - Locadora de Veículos");
        stage.setScene(scene);
        stage.show();

        root.getChildren().add(cadastroCliente.getGrid());

        comboMenu.setOnAction(e -> {
            String opcao = comboMenu.getValue();
            mudaForm(opcao);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void mudaForm(String opcao) {
        root.getChildren().remove(1);
        if (opcao.equals("Cadastrar Cliente")) {
            root.getChildren().add(cadastroCliente.getGrid());

        }
        if (opcao.equals("Cadastrar Veículo")) {
            root.getChildren().add(cadastroVeiculo.getGrid());
            System.out.println("Cadastrar Veículo");
        }
        System.out.println(root.getChildren().toString());
    }

}
