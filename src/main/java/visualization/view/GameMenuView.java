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

public class GameMenuView {

    private final StackPane root;
    private final Stage stage;
    private Button playButton;
    private Button infoButton;
    private Button settingsButton;

    public GameMenuView(Stage stage) {
        this.stage = stage;

        // Create the main layout
        root = new StackPane();

        // Create and add the animated background
        Pane backgroundPane = createAnimatedBackground();
        root.getChildren().add(backgroundPane);

        // Create the content container
        VBox contentContainer = new VBox(40);
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setMaxWidth(400);

        // Create the game title with logo
        StackPane titleContainer = createGameTitle();

        // Create menu buttons
        VBox menuButtons = createMenuButtons();

        // Add electric circuit decoration
        Pane circuitDecoration = createCircuitDecoration();

        // Add all elements to the content container
        contentContainer.getChildren().addAll(titleContainer, menuButtons);

        // Add content to the root
        root.getChildren().addAll(circuitDecoration, contentContainer);

        // Start animations
        startAnimations(circuitDecoration);
    }

    private Pane createAnimatedBackground() {
        Pane backgroundPane = new Pane();

        // Create a dark gradient background
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

        // Add electric grid lines
        Group gridLines = createGridLines();

        // Add energy particles
        Group particles = createEnergyParticles();

        backgroundPane.getChildren().addAll(background, gridLines, particles);

        // Make sure the background pane fills the entire window
        backgroundPane.prefWidthProperty().bind(stage.widthProperty());
        backgroundPane.prefHeightProperty().bind(stage.heightProperty());

        return backgroundPane;
    }

    private Group createGridLines() {
        Group gridLines = new Group();

        // Create horizontal and vertical grid lines that will scale with the window
        for (int i = 0; i < 40; i++) {
            // Horizontal lines
            Line hLine = new Line();
            hLine.startXProperty().bind(stage.widthProperty().multiply(0));
            hLine.endXProperty().bind(stage.widthProperty());
            hLine.startYProperty().set(i * 40);
            hLine.endYProperty().set(i * 40);
            hLine.setStroke(Color.web("#0EA5E9", 0.1));
            hLine.setStrokeWidth(1);

            // Vertical lines
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

    private Group createEnergyParticles() {
        Group particles = new Group();

        // Create random energy particles that will be distributed across the entire window
        for (int i = 0; i < 50; i++) {
            // Use percentages of screen size for positioning
            double xPercent = Math.random();
            double yPercent = Math.random();
            double size = 2 + Math.random() * 4;

            Circle particle = new Circle(size);

            // Bind particle position to window size
            particle.centerXProperty().bind(stage.widthProperty().multiply(xPercent));
            particle.centerYProperty().bind(stage.heightProperty().multiply(yPercent));

            particle.setFill(Color.web("#22D3EE", 0.6));

            // Add glow effect
            Glow glow = new Glow(0.8);
            particle.setEffect(glow);

            // Animate the particle
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

    private StackPane createGameTitle() {
        StackPane titleContainer = new StackPane();

        // Create the lightning bolt logo (static, no animation)
        Polygon lightning = createLightningBolt();

        // Create the game title
        Text voltText = new Text("Volt");
        voltText.setFont(Font.font("System", FontWeight.BOLD, 64));
        voltText.setFill(Color.WHITE);

        // Create the Maze text with reduced intensity
        Text mazeText = new Text("Maze");
        mazeText.setFont(Font.font("System", FontWeight.BOLD, 64));
        // Use a lighter blue color for better readability
        mazeText.setFill(Color.web("#7DD3FC"));

        // Add glow effects
        DropShadow voltGlow = new DropShadow();
        voltGlow.setColor(Color.web("#0EA5E9"));
        voltGlow.setRadius(15);
        voltGlow.setSpread(0.3);
        voltText.setEffect(voltGlow);

        // Reduced glow effect for Maze text
        DropShadow mazeGlow = new DropShadow();
        mazeGlow.setColor(Color.web("#7DD3FC"));
        mazeGlow.setRadius(10);
        mazeGlow.setSpread(0.2);
        mazeText.setEffect(mazeGlow);

        // Arrange title text
        HBox titleBox = new HBox(5, voltText, mazeText);
        titleBox.setAlignment(Pos.CENTER);

        // Create a container for the logo and title
        VBox logoAndTitle = new VBox(20, lightning, titleBox);
        logoAndTitle.setAlignment(Pos.CENTER);

        titleContainer.getChildren().add(logoAndTitle);
        return titleContainer;
    }

    private Polygon createLightningBolt() {
        // Create a detailed lightning bolt
        Polygon lightning = new Polygon(
                20.0, 0.0,   // Top point
                30.0, 15.0,
                25.0, 15.0,
                35.0, 30.0,
                30.0, 30.0,
                40.0, 50.0,  // Bottom point
                15.0, 35.0,
                25.0, 35.0,
                15.0, 20.0,
                20.0, 20.0,
                10.0, 5.0
        );

        // Apply styling
        lightning.setFill(Color.web("#22D3EE"));
        lightning.setStroke(Color.web("#0EA5E9"));
        lightning.setStrokeWidth(2);
        lightning.setScaleX(2.5);
        lightning.setScaleY(2.5);

        // Add glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#22D3EE"));
        glow.setRadius(20);
        glow.setSpread(0.5);
        lightning.setEffect(glow);

        return lightning;
    }

    private VBox createMenuButtons() {
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setMaxWidth(300);

        playButton = createStyledButton("▶  PLAY");
        infoButton = createStyledButton("ℹ  INFO");
        settingsButton = createStyledButton("⚙  SETTINGS");

        buttonContainer.getChildren().addAll(playButton, infoButton, settingsButton);
        return buttonContainer;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);

        // Apply styling directly to the button
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

        // Add drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#0EA5E9"));
        shadow.setRadius(10);
        shadow.setSpread(0);
        button.setEffect(shadow);

        button.setMaxWidth(Double.MAX_VALUE);

        // Add hover effects
        button.setOnMouseEntered(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            // Change style on hover
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

            // Enhance glow effect
            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.web("#0EA5E9"));
            hoverShadow.setRadius(20);
            hoverShadow.setSpread(0.2);
            button.setEffect(hoverShadow);
        });

        button.setOnMouseExited(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            // Restore original style
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

            // Restore original shadow
            DropShadow originalShadow = new DropShadow();
            originalShadow.setColor(Color.web("#0EA5E9"));
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            button.setEffect(originalShadow);
        });

        return button;
    }

    private Pane createCircuitDecoration() {
        Pane circuitPane = new Pane();

        // Make circuit pane fill the entire window
        circuitPane.prefWidthProperty().bind(stage.widthProperty());
        circuitPane.prefHeightProperty().bind(stage.heightProperty());

        // Create circuit paths that scale with window size
        Path topLeftCircuit = createCircuitPath(0.05, 0.05, 0.25, 0.25);
        Path bottomRightCircuit = createCircuitPath(0.7, 0.7, 0.25, 0.25);

        circuitPane.getChildren().addAll(topLeftCircuit, bottomRightCircuit);
        return circuitPane;
    }

    private Path createCircuitPath(double startXPercent, double startYPercent, double widthPercent, double heightPercent) {
        Path path = new Path();

        // Use percentages of screen size for positioning
        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(stage.widthProperty().multiply(startXPercent));
        moveTo.yProperty().bind(stage.heightProperty().multiply(startYPercent));

        // Create circuit segments that scale with window size
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

        // Style the path
        path.setStroke(Color.web("#0EA5E9", 0.6));
        path.setStrokeWidth(2);
        path.setStrokeLineCap(StrokeLineCap.ROUND);
        path.getStrokeDashArray().addAll(15.0, 5.0, 5.0, 5.0);

        return path;
    }

    private void startAnimations(Pane circuitDecoration) {
        // Animate the circuit paths
        for (int i = 0; i < circuitDecoration.getChildren().size(); i++) {
            Path path = (Path) circuitDecoration.getChildren().get(i);

            // Animate the dash offset to create a flowing effect
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

            // Animate the opacity
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

    public void setOnSettingsAction(EventHandler<ActionEvent> handler) {
        settingsButton.setOnAction(handler);
    }

    // Getter for the root pane
    public StackPane getRoot() {
        return root;
    }
}