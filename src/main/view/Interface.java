package view;
import controller.GerenciadorDados;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

public class Interface extends Application {
    private VBox root;
    private final ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    private final VeiculoRepositorio veiculoRepositorio = new VeiculoRepositorio();
    private final AluguelRepositorio aluguelRepositorio = new AluguelRepositorio();
    private final GerenciadorDados gerenciadorDados = new GerenciadorDados(clienteRepositorio,
                                                                          veiculoRepositorio,
                                                                          aluguelRepositorio);

    String[] listaMenu = new String[]{"Cadastrar Cliente", "Cadastrar Veículo",
            "Buscar Cliente", "Buscar Veículo",
            "Alugar Veículo", "Devolver Veículo"};

    CadastroCliente cadastroCliente = new CadastroCliente(clienteRepositorio);
    CadastroVeiculo cadastroVeiculo = new CadastroVeiculo();
    BuscaVeiculo buscaVeiculo = new BuscaVeiculo(veiculoRepositorio);
    BuscaCliente buscaCliente = new BuscaCliente(clienteRepositorio);
    AlugaVeiculo alugaVeiculo = new AlugaVeiculo(aluguelRepositorio,
                                                 clienteRepositorio,
                                                 veiculoRepositorio);
    DevolveVeiculo devolveVeiculo = new DevolveVeiculo(aluguelRepositorio,
                                                       clienteRepositorio,
                                                       veiculoRepositorio);

    @Override
    public void start(javafx.stage.Stage stage) {
        root = new VBox(10);

        // Configuração do Menu
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem abrirItem = new MenuItem("Abrir");
        MenuItem salvarItem = new MenuItem("Salvar");
        MenuItem sairItem = new MenuItem("Sair");
        fileMenu.getItems().addAll(abrirItem, salvarItem, sairItem);
        menuBar.getMenus().add(fileMenu);
        root.getChildren().add(menuBar);

        sairItem.setOnAction(e -> stage.close());
        abrirItem.setOnAction(e -> gerenciadorDados.carregarDados("dados"));
        salvarItem.setOnAction(e -> gerenciadorDados.salvarDados("dados"));

        // Configuração da Cena
        Scene scene = new Scene(root, 300, 400);

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
        root.getChildren().remove(2);
        if (opcao.equals("Cadastrar Cliente")) {
            root.getChildren().add(cadastroCliente.getGrid());

        }
        if (opcao.equals("Cadastrar Veículo")) {
            root.getChildren().add(cadastroVeiculo.getGrid());
            System.out.println("Cadastrar Veículo");
        }
        if (opcao.equals("Buscar Veículo")) {
            root.getChildren().add(buscaVeiculo.getGrid());
            System.out.println("Buscar Veículo");
        }
        if (opcao.equals("Buscar Cliente")) {
            root.getChildren().add(buscaCliente.getGrid());
            System.out.println("Buscar Cliente");
        }
        if (opcao.equals("Alugar Veículo")) {
            root.getChildren().add(alugaVeiculo.getGrid());
            System.out.println("Alugar Veículo");
        }
        if (opcao.equals("Devolver Veículo")) {
            root.getChildren().add(devolveVeiculo.getGrid());
            System.out.println("Devolver Veículo");
        }
    }

}
