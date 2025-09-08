package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cliente;
import model.TipoVeiculo;
import model.Veiculo;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

public class Atualizador {
    private final VeiculoRepositorio veiculoRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private Cliente cliente;
    private Veiculo veiculo;

    public Atualizador(VeiculoRepositorio veiculoRepositorio, ClienteRepositorio clienteRepositorio){
        this.veiculoRepositorio = veiculoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void atualizaCliente(){
        Stage novaJanela = new Stage();
        novaJanela.setTitle("Atualizar Cliente");
        GridPane layout = new GridPane();

        Scene scene = new Scene(layout, 300, 200);

        Label labelTitulo = new Label("Atualiza Cliente");
        Label labelDoc = new Label("CPF/CNPJ:");
        TextField entryDoc = ValidatedTextField.criaEntryDocumento();
        entryDoc.setText(cliente.getIdentificador());
        entryDoc.setDisable(true);
        Label labelNome = new Label("Nome:");
        TextField entryNome = ValidatedTextField.criaEntryTexto();
        entryNome.setText(cliente.getNome());
        Button buttonAtualizar = new Button("Atualizar");

        layout.add(labelTitulo, 0, 0, 2, 1);
        layout.add(labelDoc, 0, 1);
        layout.add(entryDoc, 1, 1);
        layout.add(labelNome, 0, 2);
        layout.add(entryNome, 1, 2);
        layout.add(buttonAtualizar, 1, 3);

        buttonAtualizar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atualizar Cliente");
            alert.setHeaderText(null);

            if (entryNome.getText().isEmpty()) {
                alert.setContentText("Nome não pode ser vazio.");
                alert.showAndWait();
            } else {
                cliente.setNome(entryNome.getText().trim());
                clienteRepositorio.atualizar(cliente);
                alert.setContentText("Cliente atualizado com sucesso!");
                alert.showAndWait();
                novaJanela.close();
            }

        });

        novaJanela.setScene(scene);
        novaJanela.showAndWait();
    }

    public void atualizaVeiculo(){
        Stage novaJanela = new Stage();
        novaJanela.setTitle("Atualizar Veículo");
        GridPane layout = new GridPane();

        Scene scene = new Scene(layout, 300, 200);

        Label labelTitulo = new Label("Atualiza Veículo");
        Label labelPlaca = new Label("Placa:");
        TextField entryPlaca = ValidatedTextField.criaEntryPlaca();
        entryPlaca.setText(veiculo.getPlaca());
        entryPlaca.setDisable(true);
        Label labelModelo = new Label("Modelo:");
        TextField entryModelo = ValidatedTextField.criaEntryTexto();
        entryModelo.setText(veiculo.getModelo());
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Hatch", "Sedan", "SUV");
        comboTipo.setValue(veiculo.getTipo().toString());


        Button buttonAtualizar = new Button("Atualizar");

        layout.add(labelTitulo, 0, 0, 2, 1);
        layout.add(labelPlaca, 0, 1);
        layout.add(entryPlaca, 1, 1);
        layout.add(labelModelo, 0, 2);
        layout.add(entryModelo, 1, 2);
        layout.add(comboTipo, 1, 3);
        layout.add(buttonAtualizar, 1, 4);

        buttonAtualizar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atualizar Veículo");
            alert.setHeaderText(null);

            TipoVeiculo tipo = TipoVeiculo.valueOf(comboTipo.getValue().toUpperCase());

            if (entryModelo.getText().isEmpty()) {
                alert.setContentText("Modelo não pode ser vazio.");
                alert.showAndWait();
            } else {
                veiculo.setModelo(entryModelo.getText().trim());
                veiculo.setTipo(tipo);
                veiculoRepositorio.atualizar(veiculo);
                alert.setContentText("Veículo atualizado com sucesso!");
                alert.showAndWait();
                novaJanela.close();
            }
        });

        novaJanela.setScene(scene);
        novaJanela.showAndWait();
    }
}
