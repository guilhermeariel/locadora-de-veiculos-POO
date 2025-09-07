package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.Veiculo;
import repository.VeiculoRepositorio;

import java.util.List;

public class BuscaVeiculo extends AbstractGridMenu{
    private VeiculoRepositorio repositorio;

    BuscaVeiculo(VeiculoRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    protected void gridMenu() {
        ObservableList<Veiculo> observableVeiculos = FXCollections.observableArrayList(
            repositorio != null ? repositorio.listar() : List.of()
        );
        ListView<Veiculo> listaVeiculos = new ListView<>(observableVeiculos);
        listaVeiculos.getSelectionModel().select(0);
        Label labelFiltro = new Label("Filtrar por:");
        ComboBox<String> comboFiltro = new ComboBox<>();
        comboFiltro.getItems().addAll("Placa", "Modelo", "Tipo");
        TextField entryFiltro = new TextField();
        Button buttonFiltrar = new Button("Filtrar");
        Button buttonEditar = new Button("Editar");
        Button buttonRemover = new Button("Remover");

        grid.add(labelFiltro, 0, 0);
        grid.add(comboFiltro, 1, 0);
        grid.add(entryFiltro, 0, 1);
        grid.add(buttonFiltrar, 1, 1);
        grid.add(listaVeiculos, 0, 2, 2, 4);
        grid.add(buttonEditar, 0, 6);
        grid.add(buttonRemover, 1, 6);

        buttonFiltrar.setOnAction(e -> {
            String filtro = comboFiltro.getValue();
            String valor = entryFiltro.getText().trim().toLowerCase();
            List<Veiculo> veiculosFiltrados = repositorio.filtrar(filtro, valor).listar();
            observableVeiculos.setAll(veiculosFiltrados);
            if (!veiculosFiltrados.isEmpty()) {
                listaVeiculos.getSelectionModel().select(0);
            }
        });

    }

    public void setRepositorio(VeiculoRepositorio repositorio) {
        this.repositorio = repositorio;
    }
}
