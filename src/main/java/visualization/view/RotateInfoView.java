package visualization.view;

import ija.ijaProject.game.Game;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.awt.*;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class RotateInfoView {
    private final BorderPane root;
    private Runnable onBack;

    public RotateInfoView(Game currentGame, Game solvedGame) {
        // 1) Верхняя панель с кнопкой «Назад»
        Button back = new Button("← Назад");
        back.setOnAction(e -> { if (onBack!=null) onBack.run(); });
        HBox topBar = new HBox(back);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);

        // 2) Собственно InfoPresenter
        InfoPresenter infoPresenter = new InfoPresenter(currentGame, solvedGame);
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() ->
                swingNode.setContent(infoPresenter.getPanel())
        );

        // 3) Собираем всё в BorderPane
        root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(swingNode);
    }

    public Parent getRoot() {
        return root;
    }

    public void setOnBackAction(Runnable handler) {
        this.onBack = handler;
    }
}
