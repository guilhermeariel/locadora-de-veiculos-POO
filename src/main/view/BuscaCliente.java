package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.Cliente;
import repository.ClienteRepositorio;
import servicos.ClienteServiceImpl;

import java.util.List;

public class BuscaCliente extends AbstractGridMenu{
    private final ClienteRepositorio repositorio;
    private final ClienteServiceImpl clienteService;
    private final Atualizador atualizador;

    BuscaCliente(ClienteRepositorio repositorio, Atualizador atualizador){
        this.repositorio = repositorio;
        this.clienteService = new ClienteServiceImpl(repositorio);
        this.atualizador = atualizador;
    }

    @Override
    protected void gridMenu() {
        ObservableList<Cliente> observableCliente = FXCollections.observableArrayList(repositorio != null?
                repositorio.listar() : List.of());
        ListView<Cliente> listaCliente = new ListView<>(observableCliente);
        listaCliente.getSelectionModel().select(0);
        Label labelFiltro = new Label("Filtrar por:");
        ComboBox<String> comboFiltro = new ComboBox<>();
        comboFiltro.getItems().addAll("Nome", "Documento");
        TextField entryFiltro = new TextField();
        Button buttonFiltrar = new Button("Filtrar");
        Button buttonEditar = new Button("Editar");
        Button buttonRemover = new Button("Remover");

        grid.add(labelFiltro, 0, 0);
        grid.add(comboFiltro, 1, 0);
        grid.add(entryFiltro, 0, 1);
        grid.add(buttonFiltrar, 1, 1);
        grid.add(listaCliente, 0, 2, 2, 4);
        grid.add(buttonEditar, 0, 6);
        grid.add(buttonRemover, 1, 6);

        buttonFiltrar.setOnAction(e -> {
            String filtro = comboFiltro.getValue();
            String valor = entryFiltro.getText().trim().toLowerCase();
            List<Cliente> clientessFiltrados = repositorio.filtrar(filtro, valor).listar();
            observableCliente.setAll(clientessFiltrados);
            if (!clientessFiltrados.isEmpty()) {
                listaCliente.getSelectionModel().select(0);
            }
        });

        buttonRemover.setOnAction(e -> {
            Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                clienteService.removerCliente(clienteSelecionado.getIdentificador());
                observableCliente.remove(clienteSelecionado);
            }
        });

        buttonEditar.setOnAction(e-> {
            Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                atualizador.setCliente(clienteSelecionado);
                atualizador.atualizaCliente();
            }
        });

    }
}
