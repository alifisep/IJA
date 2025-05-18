/**
 * Soubor: src/main/java/visualization/view/GameMenuView.java
 *
 * Popis:
 *   Třída reprezentuje hlavní menu VoltMaze. Zobrazuje animované pozadí
 *   se "síťovými" linkami a částicemi, název hry s logem a tři tlačítka:
 *   PLAY, INFO, SETTINGS.
 *
 * @author Yaroslav Hryn (xhryny00)
 * @author Oleksandr Musiichuk (xmusii00)
 */

package visualization.view;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/** Reprezentuje hlavní menu VoltMaze. Zobrazuje animované pozadí
 *   se "síťovými" linkami a částicemi, název hry s logem a tři tlačítka:
 *   PLAY, INFO, SETTINGS. */
public class GameMenuView {

    private final StackPane root;
    private final Stage stage;
    private Button playButton;
    private Button infoButton;

    /**
     * Vytvoří nové hlavní menu.
     *
     * @param stage primární Stage aplikace – pro ňí se vážou rozměry pozadí
     */
    public GameMenuView(Stage stage) {
        this.stage = stage;
        root = new StackPane();
        Pane backgroundPane = createAnimatedBackground();
        root.getChildren().add(backgroundPane);
        VBox contentContainer = new VBox(40);
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setMaxWidth(400);

        StackPane titleContainer = createGameTitle();

        VBox menuButtons = createMenuButtons();

        Pane circuitDecoration = createCircuitDecoration();

        contentContainer.getChildren().addAll(titleContainer, menuButtons);

        root.getChildren().addAll(circuitDecoration, contentContainer);

        startAnimations(circuitDecoration);
    }

    /**
     * Sestaví tmavé přechodové pozadí s mřížkou a pohybujícími se částicemi.
     *
     * @return Pane obsahující celé pozadí menu
     */
    private Pane createAnimatedBackground() {
        Pane backgroundPane = new Pane();
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#0F172A")),
                new Stop(0.5, Color.web("#1E293B")),
                new Stop(1, Color.web("#0F172A"))
        );

        Rectangle background = new Rectangle();
        background.widthProperty().bind(backgroundPane.widthProperty());
        background.heightProperty().bind(backgroundPane.heightProperty());
        background.setFill(gradient);

        Group gridLines = createGridLines();

        Group particles = createEnergyParticles();

        backgroundPane.getChildren().addAll(background, gridLines, particles);

        backgroundPane.prefWidthProperty().bind(stage.widthProperty());
        backgroundPane.prefHeightProperty().bind(stage.heightProperty());

        return backgroundPane;
    }

    /**
     * Vytvoří linky mřížky (horizontální a vertikální),
     * které se přizpůsobují rozměrům Stage.
     *
     * @return Group obsahující tvarové prvky Line
     */
    private Group createGridLines() {
        Group gridLines = new Group();


        for (int i = 0; i < 40; i++) {
            Line hLine = new Line();
            hLine.startXProperty().bind(stage.widthProperty().multiply(0));
            hLine.endXProperty().bind(stage.widthProperty());
            hLine.startYProperty().set(i * 40);
            hLine.endYProperty().set(i * 40);
            hLine.setStroke(Color.web("#0EA5E9", 0.1));
            hLine.setStrokeWidth(1);

            Line vLine = new Line();
            vLine.startXProperty().set(i * 40);
            vLine.endXProperty().set(i * 40);
            vLine.startYProperty().bind(stage.heightProperty().multiply(0));
            vLine.endYProperty().bind(stage.heightProperty());
            vLine.setStroke(Color.web("#0EA5E9", 0.1));
            vLine.setStrokeWidth(1);

            gridLines.getChildren().addAll(hLine, vLine);
        }

        return gridLines;
    }

    /**
     * Rozmístí a animuje "energetické částice" jako kružnice s glow efektem.
     *
     * @return Group s animovanými kružnicemi
     */
    private Group createEnergyParticles() {
        Group particles = new Group();

        for (int i = 0; i < 50; i++) {
            double xPercent = Math.random();
            double yPercent = Math.random();
            double size = 2 + Math.random() * 4;

            Circle particle = new Circle(size);
            particle.centerXProperty().bind(stage.widthProperty().multiply(xPercent));
            particle.centerYProperty().bind(stage.heightProperty().multiply(yPercent));

            particle.setFill(Color.web("#22D3EE", 0.6));
            Glow glow = new Glow(0.8);
            particle.setEffect(glow);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(particle.opacityProperty(), 0.2 + Math.random() * 0.6),
                            new KeyValue(particle.radiusProperty(), size)
                    ),
                    new KeyFrame(Duration.seconds(1 + Math.random() * 2),
                            new KeyValue(particle.opacityProperty(), 0.8 + Math.random() * 0.2),
                            new KeyValue(particle.radiusProperty(), size * 1.5)
                    )
            );
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            particles.getChildren().add(particle);
        }

        return particles;
    }

    /**
     * Vytvoří logo s textem „VoltMaze“ a bleskem.
     *
     * @return StackPane obsahující grafiku loga a text
     */
    private StackPane createGameTitle() {
        StackPane titleContainer = new StackPane();
        Polygon lightning = createLightningBolt();

        Text voltText = new Text("Volt");
        voltText.setFont(Font.font("System", FontWeight.BOLD, 64));
        voltText.setFill(Color.WHITE);

        Text mazeText = new Text("Maze");
        mazeText.setFont(Font.font("System", FontWeight.BOLD, 64));
        mazeText.setFill(Color.web("#7DD3FC"));

        DropShadow voltGlow = new DropShadow();
        voltGlow.setColor(Color.web("#0EA5E9"));
        voltGlow.setRadius(15);
        voltGlow.setSpread(0.3);
        voltText.setEffect(voltGlow);

        DropShadow mazeGlow = new DropShadow();
        mazeGlow.setColor(Color.web("#7DD3FC"));
        mazeGlow.setRadius(10);
        mazeGlow.setSpread(0.2);
        mazeText.setEffect(mazeGlow);

        HBox titleBox = new HBox(5, voltText, mazeText);
        titleBox.setAlignment(Pos.CENTER);

        VBox logoAndTitle = new VBox(20, lightning, titleBox);
        logoAndTitle.setAlignment(Pos.CENTER);

        titleContainer.getChildren().add(logoAndTitle);
        return titleContainer;
    }

    /**
     * Vytvoří a naformátuje detailní tvar blesku pro logo.
     * Souřadnice bodů definují konturu blesku, která se následně
     * styluje výplní, obrysem, měřítkem a glow efektem.
     *
     * @return Polygon představující blesk s aplikovaným stylem a efektem
     */
    private Polygon createLightningBolt() {
        Polygon lightning = new Polygon(
                20.0, 0.0,
                30.0, 15.0,
                25.0, 15.0,
                35.0, 30.0,
                30.0, 30.0,
                40.0, 50.0,
                15.0, 35.0,
                25.0, 35.0,
                15.0, 20.0,
                20.0, 20.0,
                10.0, 5.0
        );

        lightning.setFill(Color.web("#22D3EE"));
        lightning.setStroke(Color.web("#0EA5E9"));
        lightning.setStrokeWidth(2);
        lightning.setScaleX(2.5);
        lightning.setScaleY(2.5);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#22D3EE"));
        glow.setRadius(20);
        glow.setSpread(0.5);
        lightning.setEffect(glow);

        return lightning;
    }

    /**
     * Položí tlačítka PLAY, INFO a SETTINGS do vertikálního kontejneru.
     *
     * @return VBox s připravenými Buttony
     */
    private VBox createMenuButtons() {
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMaxWidth(300);

        playButton = createStyledButton("▶  PLAY");
        infoButton = createStyledButton("ℹ  INFO");

        buttonContainer.getChildren().addAll(playButton, infoButton);
        return buttonContainer;
    }

    /**
     * Standardní stylování jednoho tlačítka menu.
     *
     * @param text text, který se na tlačítko vypíše
     * @return nově vytvořený a naformátovaný Button
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: rgba(14, 165, 233, 0.2);" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 15 30;" +
                        "-fx-cursor: hand;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#0EA5E9"));
        shadow.setRadius(10);
        shadow.setSpread(0);
        button.setEffect(shadow);

        button.setMaxWidth(Double.MAX_VALUE);

        button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            button.setStyle(
                    "-fx-background-color: rgba(14, 165, 233, 0.4);" +
                            "-fx-background-radius: 30;" +
                            "-fx-border-color: #0EA5E9;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 30;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 15 30;" +
                            "-fx-cursor: hand;"
            );

            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.web("#0EA5E9"));
            hoverShadow.setRadius(20);
            hoverShadow.setSpread(0.2);
            button.setEffect(hoverShadow);
        });

        button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            button.setStyle(
                    "-fx-background-color: rgba(14, 165, 233, 0.2);" +
                            "-fx-background-radius: 30;" +
                            "-fx-border-color: #0EA5E9;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 30;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 15 30;" +
                            "-fx-cursor: hand;"
            );

            DropShadow originalShadow = new DropShadow();
            originalShadow.setColor(Color.web("#0EA5E9"));
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            button.setEffect(originalShadow);
        });

        return button;
    }

    /**
     * Přidá dekorativní "cestičky" elektro-cesty do menu pozadí.
     *
     * @return Pane s několika Path prvky
     */
    private Pane createCircuitDecoration() {
        Pane circuitPane = new Pane();

        circuitPane.prefWidthProperty().bind(stage.widthProperty());
        circuitPane.prefHeightProperty().bind(stage.heightProperty());

        Path topLeftCircuit = createCircuitPath(0.05, 0.05, 0.25, 0.25);
        Path bottomRightCircuit = createCircuitPath(0.7, 0.7, 0.25, 0.25);

        circuitPane.getChildren().addAll(topLeftCircuit, bottomRightCircuit);
        return circuitPane;
    }


    /**
     * Vytvoří cestu ("circuit") skládající se z úseček (PathElements),
     * jejíž souřadnice jsou vypočítány jako procentuální pozice vůči velikosti okna.
     * Cesta začíná v bodě odpovídajícím startXPercent, startYPercent,
     * poté vede vodorovně, svisle, zase vodorovně, atd., až do koncového bodu
     * na procentuálním offsetu (startXPercent + widthPercent, startYPercent + heightPercent).
     * Výsledná cesta je na konec naformátována stylingem (barva, tloušťka čáry, čárkování).
     *
     * @param startXPercent  horizontální startovací pozice jako procento šířky okna (0.0–1.0)
     * @param startYPercent  vertikální startovací pozice jako procento výšky okna (0.0–1.0)
     * @param widthPercent   procentuální šířka segmentu cesty (0.0–1.0)
     * @param heightPercent  procentuální výška segmentu cesty (0.0–1.0)
     * @return Path obsahující nakonfigurované HLineTo a VLineTo elementy a stylování
     */
    private Path createCircuitPath(double startXPercent, double startYPercent, double widthPercent, double heightPercent) {
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(stage.widthProperty().multiply(startXPercent));
        moveTo.yProperty().bind(stage.heightProperty().multiply(startYPercent));

        HLineTo hLine1 = new HLineTo();
        hLine1.xProperty().bind(stage.widthProperty().multiply(startXPercent + widthPercent * 0.3));

        VLineTo vLine1 = new VLineTo();
        vLine1.yProperty().bind(stage.heightProperty().multiply(startYPercent + heightPercent * 0.4));

        HLineTo hLine2 = new HLineTo();
        hLine2.xProperty().bind(stage.widthProperty().multiply(startXPercent + widthPercent * 0.7));

        VLineTo vLine2 = new VLineTo();
        vLine2.yProperty().bind(stage.heightProperty().multiply(startYPercent + heightPercent * 0.7));

        HLineTo hLine3 = new HLineTo();
        hLine3.xProperty().bind(stage.widthProperty().multiply(startXPercent + widthPercent));

        path.getElements().addAll(moveTo, hLine1, vLine1, hLine2, vLine2, hLine3);

        path.setStroke(Color.web("#0EA5E9", 0.6));
        path.setStrokeWidth(2);
        path.setStrokeLineCap(StrokeLineCap.ROUND);
        path.getStrokeDashArray().addAll(15.0, 5.0, 5.0, 5.0);

        return path;
    }

    /**
     * Spustí všechny animace (pohyb čárek a blikání) v dekoraci.
     *
     * @param circuitDecoration Pane, jehož děti (Path) se mají animovat
     */
    private void startAnimations(Pane circuitDecoration) {
        for (int i = 0; i < circuitDecoration.getChildren().size(); i++) {
            Path path = (Path) circuitDecoration.getChildren().get(i);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(path.strokeDashOffsetProperty(), 0)
                    ),
                    new KeyFrame(Duration.seconds(10),
                            new KeyValue(path.strokeDashOffsetProperty(), 100)
                    )
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), path);
            fadeTransition.setFromValue(0.4);
            fadeTransition.setToValue(0.8);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setAutoReverse(true);
            fadeTransition.play();
        }
    }

    // Event handler setters
    public void setOnPlayAction(EventHandler<ActionEvent> handler) {
        playButton.setOnAction(handler);
    }

    public void setOnInfoAction(EventHandler<ActionEvent> handler) {
        infoButton.setOnAction(handler);
    }


    /**
     * Vrací root uzel scény – vkládá se do Scene v MainApp.
     *
     * @return StackPane kořenová část scény menu
     */
    public StackPane getRoot() {
        return root;
    }
}