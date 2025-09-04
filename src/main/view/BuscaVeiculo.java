package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.Veiculo;
import repository.VeiculoRepositorio;

import java.util.List;

public class BuscaVeiculo extends AbstractGridMenu{
    private VeiculoRepositorio repositorio;

    @Override
    protected void gridMenu() {
        ObservableList<Veiculo> observableVeiculos = FXCollections.observableArrayList(repositorio.listar());
        ListView<Veiculo> listaVeiculos = new ListView<>(observableVeiculos);
        listaVeiculos.getSelectionModel().select(0);

    }

    public void setRepositorio(VeiculoRepositorio repositorio) {
        this.repositorio = repositorio;
    }
}
