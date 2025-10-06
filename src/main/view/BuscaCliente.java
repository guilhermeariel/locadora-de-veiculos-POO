package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Cliente;
import repository.ClienteRepositorio;
import servicos.ClienteServiceImpl;
import utils.PaginacaoUtil;

import java.util.List;

public class BuscaCliente extends AbstractGridMenu{
    private final ClienteRepositorio repositorio;
    private final ClienteServiceImpl clienteService;
    private final Atualizador atualizador;
    private final ObservableList<Cliente> observableCliente;

    private Pagination pagination;
    private ListView<Cliente> listaCliente;
    private List<Cliente> listaAtual;

    private static final int ITENS_POR_PAGINA = 10;

    BuscaCliente(ClienteRepositorio repositorio, Atualizador atualizador){
        this.repositorio = repositorio;
        this.clienteService = new ClienteServiceImpl(repositorio);
        this.atualizador = atualizador;
        this.observableCliente = FXCollections.observableArrayList(repositorio != null?
                repositorio.getLista() : List.of());
    }

    @Override
    protected void gridMenu() {
        listaCliente = new ListView<>(observableCliente);
        pagination = new Pagination();

        pagination.setPageCount(1);
        pagination.currentPageIndexProperty().addListener((_,
                                                           _,
                                                           newIndex) ->
                atualizarPagina(listaCliente, newIndex.intValue() + 1));

        listaCliente.getSelectionModel().select(0);
        BorderPane listaPane = new BorderPane(listaCliente, null, null, pagination, null);

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
        grid.add(listaPane, 0, 2, 2, 4);
        grid.add(buttonEditar, 0, 6);
        grid.add(buttonRemover, 1, 6);

        buttonFiltrar.setOnAction(_ -> {
            String filtro = comboFiltro.getValue();
            String valor = entryFiltro.getText().trim().toLowerCase();
            List<Cliente> clientesFiltrados = repositorio.filtrar(filtro, valor).getLista();
            listaAtual = clientesFiltrados;
            observableCliente.setAll(clientesFiltrados);
            if (!clientesFiltrados.isEmpty()) {
                listaCliente.getSelectionModel().select(0);
            }
        });

        buttonRemover.setOnAction(_ -> {
            Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                clienteService.removerCliente(clienteSelecionado.getIdentificador());
                observableCliente.remove(clienteSelecionado);
            }
        });

        buttonEditar.setOnAction(_-> {
            Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                atualizador.setCliente(clienteSelecionado);
                atualizador.atualizaCliente();
            }
        });

    }

    private void atualizarPagina(ListView<Cliente> lista, int pagina) {
        List<Cliente> paginaDeDados = PaginacaoUtil.paginar(
                    listaAtual,
                    pagina,
                    ITENS_POR_PAGINA,
                    Cliente::getNome,
                    true
                );
        observableCliente.setAll(paginaDeDados);
        lista.setItems(observableCliente);
    }

    public void update(){
        listaAtual = repositorio.getLista();
        observableCliente.setAll(listaAtual);
        atualizarPagina(listaCliente, 1);
        int totalItens = repositorio.getLista().size();
        int totalPaginas = (int) Math.ceil((double) totalItens / ITENS_POR_PAGINA);
        pagination.setPageCount(totalPaginas);
    }
}
