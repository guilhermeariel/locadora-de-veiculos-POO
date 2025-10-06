package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Veiculo;
import repository.VeiculoRepositorio;
import servicos.VeiculoServiceImpl;
import utils.PaginacaoUtil;

import java.util.List;

public class BuscaVeiculo extends AbstractGridMenu{
    private final VeiculoRepositorio repositorio;
    private final VeiculoServiceImpl veiculoServiceImpl;
    private final Atualizador atualizador;
    private final ObservableList<Veiculo> observableVeiculo;

    private Pagination pagination;
    private ListView<Veiculo> listaVeiculo;
    private List<Veiculo> listaAtual;

    private static final int ITENS_POR_PAGINA = 10;

    BuscaVeiculo(VeiculoRepositorio repositorio, Atualizador atualizador){
        this.repositorio = repositorio;
        this.veiculoServiceImpl = new VeiculoServiceImpl(repositorio);
        this.atualizador = atualizador;
        this.observableVeiculo = FXCollections.observableArrayList(repositorio != null?
                repositorio.getLista() : List.of());
    }

    @Override
    protected void gridMenu() {
        listaVeiculo = new ListView<>(observableVeiculo);
        pagination = new Pagination();

        pagination.setPageCount(1);
        pagination.currentPageIndexProperty().addListener((_, _, newIndex) ->
            atualizarPagina(listaVeiculo, newIndex.intValue() + 1)
        );

        listaVeiculo.getSelectionModel().select(0);
        BorderPane listaPane = new BorderPane(listaVeiculo, null, null, pagination, null);

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
        grid.add(listaPane, 0, 2, 2, 4);
        grid.add(buttonEditar, 0, 6);
        grid.add(buttonRemover, 1, 6);

        buttonFiltrar.setOnAction(_ -> {
            String filtro = comboFiltro.getValue();
            String valor = entryFiltro.getText().trim().toLowerCase();
            List<Veiculo> veiculosFiltrados = repositorio.filtrar(filtro, valor).getLista();
            listaAtual = veiculosFiltrados;
            observableVeiculo.setAll(veiculosFiltrados);
            if (!veiculosFiltrados.isEmpty()) {
                listaVeiculo.getSelectionModel().select(0);
            }
        });

        buttonRemover.setOnAction(_ -> {
            Veiculo veiculoSelecionado = listaVeiculo.getSelectionModel().getSelectedItem();
            if (veiculoSelecionado != null) {
                veiculoServiceImpl.removerVeiculo(veiculoSelecionado.getIdentificador());
                observableVeiculo.remove(veiculoSelecionado);
            }
        });

        buttonEditar.setOnAction(_-> {
            Veiculo veiculoSelecionado = listaVeiculo.getSelectionModel().getSelectedItem();
            if (veiculoSelecionado != null) {
                atualizador.setVeiculo(veiculoSelecionado);
                atualizador.atualizaVeiculo();
            }
        });

    }

    private void atualizarPagina(ListView<Veiculo> lista, int pagina) {
        List<Veiculo> paginaDeDados = PaginacaoUtil.paginar(
                listaAtual,
                pagina,
                ITENS_POR_PAGINA,
                Veiculo::getPlaca,
                true
        );
        observableVeiculo.setAll(paginaDeDados);
        lista.setItems(observableVeiculo);
    }

    public void update(){
        listaAtual = repositorio.getLista();
        observableVeiculo.setAll(listaAtual);
        atualizarPagina(listaVeiculo, 1);
        int totalItens = repositorio.getLista().size();
        int totalPaginas = (int) Math.ceil((double) totalItens / ITENS_POR_PAGINA);
        pagination.setPageCount(totalPaginas);
    }
}
