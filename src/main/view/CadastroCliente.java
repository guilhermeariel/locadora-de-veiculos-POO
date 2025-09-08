package view;

import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class CadastroCliente extends AbstractGridMenu{
    @Override
    protected void gridMenu(){
        grid.setHgap(10); // espaçamento horizontal
        grid.setVgap(10); // espaçamento vertical
        grid.setPadding(new Insets(10));

        ComboBox<String> comboClienteTipo = new ComboBox<>();
        comboClienteTipo.getItems().addAll("Pessoa Física", "Pessoa Jurídica");
        comboClienteTipo.setValue("Pessoa Física");
        Label labelNome = new Label("Nome:");
        TextField entryNome = ValidatedTextField.criaEntryTexto();
        Label labelDocumento = new Label("CPF:");
        TextField entryDoc = ValidatedTextField.criaEntryDocumento();
        Button buttonCadastrar = new Button("Cadastrar");
        buttonCadastrar.setDisable(true);

        comboClienteTipo.setOnAction(e -> {
            String selecionado = comboClienteTipo.getValue();
            if(selecionado.equals("Pessoa Física")) {
                labelDocumento.setText("CPF:");
            } else {
                labelDocumento.setText("CNPJ:");
            }
        });

        grid.add(comboClienteTipo, 0, 0, 2, 1);   // ocupa 2 colunas na primeira linha
        grid.add(labelNome, 0, 1);        // coluna 0, linha 1
        grid.add(entryNome, 1, 1);        // coluna 1, linha 1
        grid.add(labelDocumento, 0, 2);
        grid.add(entryDoc, 1, 2);
        grid.add(buttonCadastrar, 1, 3);

        ChangeListener<String> changeListener = (obs, old, neu) -> {
            boolean nomeValido = Validations.nomeValido(entryNome.getText());
            boolean docValido = Validations.documentoValido(entryDoc.getText());
            boolean docCpf = comboClienteTipo.getValue().contains("Física") && Validations.cpfValido(entryDoc.getText());
            boolean docCnpj = comboClienteTipo.getValue().contains("Jurídica") && Validations.cnpjValido(entryDoc.getText());
            docValido = docValido && (docCpf || docCnpj);
            buttonCadastrar.setDisable(!nomeValido || !docValido);
        };

        entryNome.textProperty().addListener(changeListener);
        entryDoc.textProperty().addListener(changeListener);
        comboClienteTipo.valueProperty().addListener(changeListener);
    }

    public GridPane getGrid() {
        return grid;
    }


}
