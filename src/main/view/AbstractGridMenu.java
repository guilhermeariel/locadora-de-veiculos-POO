package view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public abstract class AbstractGridMenu {
    protected GridPane grid;

    public AbstractGridMenu() {
        grid = new GridPane();
        grid.setHgap(10); // espaçamento horizontal
        grid.setVgap(10); // espaçamento vertical
        grid.setPadding(new Insets(10));
        gridMenu();
    }

    protected abstract void gridMenu();

    public GridPane getGrid() {
        return grid;
    }
}
