package view;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class ValidatedTextField {
    public static TextField criaEntryGenerico(String regex) {
        TextField textField = new TextField();

        textField.textProperty().addListener((obs, old, neu) -> {
            if (!neu.matches(regex)) {
                textField.setStyle("-fx-border-color: red;");
            } else {
                textField.setStyle("");
            }
        });

        return textField;
    }

    public static TextField criaEntryData() {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        TextField textField = criaEntryGenerico(regex);
        return textField;
    }

    public static TextField criaEntryHora() {
        String regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
        TextField textField = criaEntryGenerico(regex);
        return textField;
    }

    public static TextField criaEntryDocumento() {
        String regex = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$|^\\d{14}$)";
        TextField textField = criaEntryGenerico(regex);
        return textField;
    }

    public static TextField criaEntryPlaca() {
        String regex = "(^[A-Z]{3}-\\d{4}$)|(^[A-Z]{3}\\d[A-Z]\\d{2}$)";
        TextField textField = criaEntryGenerico(regex);
        return textField;
    }

    public static TextField criaEntryTexto() {
        String regex = "^[A-Za-zÀ-ú ]+$";
        TextField textField = criaEntryGenerico(regex);
        return textField;
    }

}
