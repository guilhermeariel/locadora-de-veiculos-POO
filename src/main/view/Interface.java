package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

public class Interface extends Application {
    private VBox root;
    private final ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    private final VeiculoRepositorio veiculoRepositorio = new VeiculoRepositorio();

    String[] listaMenu = new String[]{"Cadastrar Cliente", "Cadastrar Veículo",
            "Buscar Cliente", "Buscar Veículo",
            "Alugar Veículo", "Devolver Veículo"};

    CadastroCliente cadastroCliente = new CadastroCliente();
    CadastroVeiculo cadastroVeiculo = new CadastroVeiculo();
    BuscaVeiculo buscaVeiculo = new BuscaVeiculo(veiculoRepositorio);
    BuscaCliente buscaCliente = new BuscaCliente(clienteRepositorio);

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
        if (opcao.equals("Buscar Veículo")) {
            root.getChildren().add(buscaVeiculo.getGrid());
            System.out.println("Buscar Veículo");
        }
        if (opcao.equals("Buscar Cliente")) {
            root.getChildren().add(buscaCliente.getGrid());
            System.out.println("Buscar Cliente");
        }
        System.out.println(root.getChildren().toString());
    }

}
