package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Veiculo;
import repository.VeiculoRepositorio;

import java.util.List;

public class ListaVeiculos{
    private List<Veiculo> veiculos;
    private VeiculoRepositorio repositorio;
    private Veiculo veiculoSelecionado;

    public void setRepositorio(VeiculoRepositorio repositorio){
        this.repositorio = repositorio;
        this.veiculos = repositorio.listarDisponiveis();
    }

    public void startStage(){
        Stage novaJanela = new Stage();
        novaJanela.setTitle("Lista de Veículos");
        VBox layout = new VBox();

        Scene scene = new Scene(layout, 300, 200);
        Label label = new Label("Lista de Veículos");
        layout.getChildren().add(label);

        ObservableList<Veiculo> observableVeiculos = FXCollections.observableArrayList(veiculos);
        ListView<Veiculo> listaVeiculos = new ListView<>(observableVeiculos);
        listaVeiculos.getSelectionModel().select(0);

        Button selecionarButton = new Button("Selecionar");

        selecionarButton.setOnAction(e -> {
            Veiculo veiculoSelecionado = listaVeiculos.getSelectionModel().getSelectedItem();
            novaJanela.close();
            if (veiculoSelecionado != null) {
                System.out.println("Veículo selecionado: " + veiculoSelecionado);
                this.veiculoSelecionado = veiculoSelecionado;
                novaJanela.close();
            }
            else {
                System.out.println("Nenhum veículo selecionado.");
                novaJanela.close();
            }
        });

        layout.getChildren().add(listaVeiculos);
        layout.getChildren().add(selecionarButton);
        novaJanela.setScene(scene);
        novaJanela.showAndWait();
    }

    public Veiculo getVeiculoSelecionado() {
        return veiculoSelecionado;
    }

}
