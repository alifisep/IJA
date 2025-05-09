package visualization.view;

import ija.ijaProject.game.levels.LevelManager;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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

public class LevelsView {

    private final StackPane root;
    private final Stage stage;
    private Button backButton;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private final VBox contentContainer;
    private final VBox levelsContainer;
    private int currentDifficulty = 0; // 0 = easy, 1 = medium, 2 = hard
    private EventHandler<ActionEvent> levelSelectHandler;
    private final LevelManager levelManager = LevelManager.getInstance();

    public LevelsView(Stage stage) {
        this.stage = stage;

        // Create the main layout
        root = new StackPane();

        // Create and add the animated background
        Pane backgroundPane = createAnimatedBackground();
        root.getChildren().add(backgroundPane);

        // Create the content container with responsive sizing
        contentContainer = new VBox(30);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.maxWidthProperty().bind(stage.widthProperty().multiply(0.9));
        contentContainer.setPadding(new Insets(40, 20, 40, 20));

        // Create the header with back button and title
        Pane headerBox = createHeader();

        // Create difficulty selector
        HBox difficultySelector = createDifficultySelector();

        // Create levels grid container
        levelsContainer = new VBox();
        levelsContainer.setAlignment(Pos.CENTER);
        levelsContainer.setPadding(new Insets(20, 0, 0, 0));

        // Show initial levels (Easy)
        showLevels(0);

        // Add electric circuit decoration
        Pane circuitDecoration = createCircuitDecoration();

        // Add all elements to the content container
        contentContainer.getChildren().addAll(headerBox, difficultySelector, levelsContainer);

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

    private Pane createHeader() {
        // Create a StackPane as the base container
        StackPane headerContainer = new StackPane();
        headerContainer.setPadding(new Insets(0, 0, 20, 0));

        // Create the title text
        Text levelsTitle = new Text("Select Level");
        levelsTitle.setFont(Font.font("System", FontWeight.BOLD, 36));
        levelsTitle.setFill(Color.web("#7DD3FC"));

        // Add glow effect to title
        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.web("#0EA5E9"));
        titleGlow.setRadius(10);
        titleGlow.setSpread(0.2);
        levelsTitle.setEffect(titleGlow);

        // Create back button with arrow
        backButton = new Button("←  Back");
        backButton.setStyle(
                "-fx-background-color: rgba(14, 165, 233, 0.2);" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20;"
        );

        // Add drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#0EA5E9"));
        shadow.setRadius(10);
        shadow.setSpread(0);
        backButton.setEffect(shadow);

        // Add hover effects
        addButtonHoverEffect(backButton);

        // Add the title to the center of the StackPane
        headerContainer.getChildren().add(levelsTitle);
        StackPane.setAlignment(levelsTitle, Pos.CENTER);

        // Add the back button to the left of the StackPane
        headerContainer.getChildren().add(backButton);
        StackPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return headerContainer;
    }

    private HBox createDifficultySelector() {
        HBox difficultyBox = new HBox(15);
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(10, 0, 10, 0));

        // Create a container with semi-transparent background
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(15, 30, 15, 30));
        container.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.85);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-color: rgba(14, 165, 233, 0.3);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 15px;"
        );

        // Create difficulty buttons
        easyButton = createDifficultyButton("Beginner", 0);
        mediumButton = createDifficultyButton("Intermediate", 1);
        hardButton = createDifficultyButton("Advanced", 2);

        // Set initial selection
        updateDifficultyButtonStyles();

        // Add buttons to container
        container.getChildren().addAll(easyButton, mediumButton, hardButton);

        // Add container to difficulty box
        difficultyBox.getChildren().add(container);

        return difficultyBox;
    }

    private Button createDifficultyButton(String text, int difficulty) {
        Button button = new Button(text);

        // Set button action
        button.setOnAction(e -> {
            currentDifficulty = difficulty;
            updateDifficultyButtonStyles();
            showLevels(difficulty);
        });

        return button;
    }

    private void updateDifficultyButtonStyles() {
        // Reset all buttons
        String unselectedStyle =
                "-fx-background-color: rgba(14, 165, 233, 0.2);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 15;";

        String selectedStyle =
                "-fx-background-color: rgba(14, 165, 233, 0.5);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 15;";

        easyButton.setStyle(currentDifficulty == 0 ? selectedStyle : unselectedStyle);
        mediumButton.setStyle(currentDifficulty == 1 ? selectedStyle : unselectedStyle);
        hardButton.setStyle(currentDifficulty == 2 ? selectedStyle : unselectedStyle);

        // Add glow to selected button
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#0EA5E9"));
        glow.setRadius(15);
        glow.setSpread(0.3);

        easyButton.setEffect(currentDifficulty == 0 ? glow : null);
        mediumButton.setEffect(currentDifficulty == 1 ? glow : null);
        hardButton.setEffect(currentDifficulty == 2 ? glow : null);
    }

    // Modify your showLevels method to include the reset button
    private void showLevels(int difficulty) {
        // Clear existing levels
        levelsContainer.getChildren().clear();

        // Create a container with semi-transparent background
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(25));
        container.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.85);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-color: rgba(14, 165, 233, 0.3);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 15px;"
        );

        // Create difficulty title
        String difficultyName = difficulty == 0 ? "Beginner" : difficulty == 1 ? "Intermediate" : "Advanced";
        Text difficultyTitle = new Text(difficultyName + " Levels");
        difficultyTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        difficultyTitle.setFill(Color.web("#0EA5E9"));

        // Add glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#0EA5E9"));
        glow.setRadius(5);
        glow.setSpread(0.1);
        difficultyTitle.setEffect(glow);

        // Create grid of level buttons
        GridPane levelsGrid = createLevelsGrid(difficulty);

        // Create reset progress button
        Button resetButton = createResetProgressButton();

        // Add a spacer before the reset button
        Region spacer = new Region();
        spacer.setPrefHeight(10);

        // Add title, grid, spacer, and reset button to container
        container.getChildren().addAll(difficultyTitle, levelsGrid, spacer, resetButton);

        // Add container to levels container
        levelsContainer.getChildren().add(container);
    }

    private GridPane createLevelsGrid(int difficulty) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);

        // Get the highest completed level to determine which levels are unlocked
        int highestCompleted = levelManager.getHighestCompletedLevel(difficulty);

        // Create 10 level buttons in a 5x2 grid
        for (int i = 0; i < 10; i++) {
            int levelNumber = i + 1; // Level numbers start at 1
            int row = i / 5;
            int col = i % 5;

            boolean isCompleted = LevelManager.getInstance().isLevelCompleted(levelNumber, difficulty);

            // All levels are now unlocked
            boolean isUnlocked = true; // This is the key change

            Button levelButton = createLevelButton(levelNumber, difficulty, highestCompleted);
            grid.add(levelButton, col, row);
        }

        return grid;
    }

    private Button createLevelButton(int levelNumber, int difficulty, int highestCompleted) {
        Button button = new Button();

        // Store level info in the button properties
        button.getProperties().put("levelNumber", levelNumber);
        button.getProperties().put("difficulty", difficulty);

        // Check if level is completed (for visual indication only)
        boolean isCompleted = levelManager.isLevelCompleted(levelNumber, difficulty);

        // Make all levels unlocked
        boolean isUnlocked = true; // This is the key change

        // Set button text based on status
        if (isCompleted) {
            button.setText("✓");
        } else {
            button.setText(String.valueOf(levelNumber));
        }

        // Style the button based on its status
        String baseStyle =
                "-fx-background-radius: 10;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 60px;" +
                        "-fx-min-height: 60px;" +
                        "-fx-max-width: 60px;" +
                        "-fx-max-height: 60px;";

        if (isCompleted) {
            // Completed level - green with checkmark
            button.setStyle(
                    "-fx-background-color: rgba(34, 197, 94, 0.6);" +
                            "-fx-border-color: #22C55E;" +
                            "-fx-text-fill: white;" +
                            baseStyle
            );
        } else {
            // All levels are now unlocked - blue
            button.setStyle(
                    "-fx-background-color: rgba(14, 165, 233, 0.3);" +
                            "-fx-border-color: #0EA5E9;" +
                            "-fx-text-fill: white;" +
                            baseStyle
            );
        }

        // Add glow effect
        DropShadow glow = new DropShadow();
        if (isCompleted) {
            glow.setColor(Color.web("#22C55E"));
        } else {
            glow.setColor(Color.web("#0EA5E9"));
        }
        glow.setRadius(10);
        glow.setSpread(0.1);
        button.setEffect(glow);

        // Add hover effects for all levels (since all are unlocked)
        button.setOnMouseEntered(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();

            // Change style on hover
            String hoverStyle = isCompleted ?
                    "-fx-background-color: rgba(34, 197, 94, 0.8);" +
                            "-fx-border-color: #22C55E;" :
                    "-fx-background-color: rgba(14, 165, 233, 0.5);" +
                            "-fx-border-color: #0EA5E9;";

            button.setStyle(
                    hoverStyle +
                            "-fx-text-fill: white;" +
                            baseStyle
            );

            // Enhance glow effect
            DropShadow hoverGlow = new DropShadow();
            hoverGlow.setColor(isCompleted ? Color.web("#22C55E") : Color.web("#0EA5E9"));
            hoverGlow.setRadius(15);
            hoverGlow.setSpread(0.3);
            button.setEffect(hoverGlow);
        });

        button.setOnMouseExited(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            // Restore original style
            String normalStyle = isCompleted ?
                    "-fx-background-color: rgba(34, 197, 94, 0.6);" +
                            "-fx-border-color: #22C55E;" :
                    "-fx-background-color: rgba(14, 165, 233, 0.3);" +
                            "-fx-border-color: #0EA5E9;";

            button.setStyle(
                    normalStyle +
                            "-fx-text-fill: white;" +
                            baseStyle
            );

            // Restore original glow
            DropShadow originalGlow = new DropShadow();
            originalGlow.setColor(isCompleted ? Color.web("#22C55E") : Color.web("#0EA5E9"));
            originalGlow.setRadius(10);
            originalGlow.setSpread(0.1);
            button.setEffect(originalGlow);
        });

        // Set action to start the game with the selected level (for all levels)
        button.setOnAction(e -> {
            if (levelSelectHandler != null) {
                levelSelectHandler.handle(e);
            }
        });

        return button;
    }

    private void addButtonHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            // Change style on hover
            String style = button.getStyle();
            style = style.replace("rgba(14, 165, 233, 0.2)", "rgba(14, 165, 233, 0.4)");
            button.setStyle(style);

            // Enhance glow effect
            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.web("#0EA5E9"));
            hoverShadow.setRadius(15);
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
            String style = button.getStyle();
            style = style.replace("rgba(14, 165, 233, 0.4)", "rgba(14, 165, 233, 0.2)");
            button.setStyle(style);

            // Restore original shadow
            DropShadow originalShadow = new DropShadow();
            originalShadow.setColor(Color.web("#0EA5E9"));
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            button.setEffect(originalShadow);
        });
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

    // Add a cleanup method to stop animations when switching views
    public void cleanup() {
        System.out.println("LevelsView cleanup called");
        // Stop animations here if needed
    }

    public void setOnBackAction(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }

    public void setOnLevelSelectAction(EventHandler<ActionEvent> handler) {
        this.levelSelectHandler = handler;
    }

    // Getter for the root pane
    public StackPane getRoot() {
        return root;
    }

    private Button createResetProgressButton() {
        Button resetButton = new Button("Reset Progress");
        resetButton.setStyle(
                "-fx-background-color: rgba(239, 68, 68, 0.2);" + // Red background
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: #EF4444;" + // Red border
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20;"
        );

        // Add drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#EF4444"));
        shadow.setRadius(10);
        shadow.setSpread(0);
        resetButton.setEffect(shadow);

        // Add hover effects
        resetButton.setOnMouseEntered(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), resetButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            // Change style on hover
            String style = resetButton.getStyle();
            style = style.replace("rgba(239, 68, 68, 0.2)", "rgba(239, 68, 68, 0.4)");
            resetButton.setStyle(style);

            // Enhance glow effect
            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.web("#EF4444"));
            hoverShadow.setRadius(15);
            hoverShadow.setSpread(0.2);
            resetButton.setEffect(hoverShadow);
        });

        resetButton.setOnMouseExited(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), resetButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            // Restore original style
            String style = resetButton.getStyle();
            style = style.replace("rgba(239, 68, 68, 0.4)", "rgba(239, 68, 68, 0.2)");
            resetButton.setStyle(style);

            // Restore original shadow
            DropShadow originalShadow = new DropShadow();
            originalShadow.setColor(Color.web("#EF4444"));
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            resetButton.setEffect(originalShadow);
        });

        // Set action to reset progress
        resetButton.setOnAction(e -> {
            // Show confirmation dialog
            showResetConfirmationDialog();
        });

        return resetButton;
    }

    // Add this method to show a confirmation dialog
    private void showResetConfirmationDialog() {
        // Create a modal dialog
        StackPane dialogRoot = new StackPane();
        dialogRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");

        // Create dialog content
        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);
        dialogContent.setPadding(new Insets(30));
        dialogContent.setMaxWidth(400);
        dialogContent.setMaxHeight(250);
        dialogContent.setStyle(
                "-fx-background-color: #1E293B;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 15px;"
        );

        // Create warning icon
        Text warningIcon = new Text("⚠️");
        warningIcon.setFont(Font.font("System", FontWeight.BOLD, 40));
        warningIcon.setFill(Color.web("#FBBF24"));

        // Create title
        Text title = new Text("Reset Progress");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setFill(Color.web("#FBBF24"));

        // Create message
        Text message = new Text("Are you sure you want to reset all level progress? This action cannot be undone.");
        message.setFont(Font.font("System", FontWeight.NORMAL, 16));
        message.setFill(Color.WHITE);
        message.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        message.setWrappingWidth(340);

        // Create buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(
                "-fx-background-color: rgba(100, 116, 139, 0.3);" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: #64748B;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20;"
        );

        Button confirmButton = new Button("Reset All Progress");
        confirmButton.setStyle(
                "-fx-background-color: rgba(239, 68, 68, 0.3);" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: #EF4444;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 30;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20;"
        );

        // Add hover effects to buttons
        addButtonHoverEffect(cancelButton);

        confirmButton.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), confirmButton);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            String style = confirmButton.getStyle();
            style = style.replace("rgba(239, 68, 68, 0.3)", "rgba(239, 68, 68, 0.5)");
            confirmButton.setStyle(style);

            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.web("#EF4444"));
            hoverShadow.setRadius(15);
            hoverShadow.setSpread(0.2);
            confirmButton.setEffect(hoverShadow);
        });

        confirmButton.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), confirmButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            String style = confirmButton.getStyle();
            style = style.replace("rgba(239, 68, 68, 0.5)", "rgba(239, 68, 68, 0.3)");
            confirmButton.setStyle(style);

            DropShadow originalShadow = new DropShadow();
            originalShadow.setColor(Color.web("#EF4444"));
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            confirmButton.setEffect(originalShadow);
        });

        buttonBox.getChildren().addAll(cancelButton, confirmButton);

        // Add all elements to dialog content
        dialogContent.getChildren().addAll(warningIcon, title, message, buttonBox);

        // Add dialog content to dialog root
        dialogRoot.getChildren().add(dialogContent);

        // Create a scene for the dialog
        Scene dialogScene = new Scene(dialogRoot, stage.getWidth(), stage.getHeight());
        dialogScene.setFill(Color.TRANSPARENT);

        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.initOwner(stage);
        dialogStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        dialogStage.setScene(dialogScene);

        // Set button actions
        cancelButton.setOnAction(e -> dialogStage.close());

        confirmButton.setOnAction(e -> {
            // Reset progress
            levelManager.resetProgress();

            // Close dialog
            dialogStage.close();

            // Refresh the levels view
            showLevels(currentDifficulty);

            // Show confirmation toast
            showResetConfirmationToast();
        });

        // Show the dialog
        dialogStage.show();
    }

    // Add this method to show a confirmation toast
    private void showResetConfirmationToast() {
        // Create toast container
        StackPane toastContainer = new StackPane();
        toastContainer.setStyle(
                "-fx-background-color: rgba(34, 197, 94, 0.9);" +
                        "-fx-background-radius: 30px;" +
                        "-fx-padding: 15px 25px;"
        );

        // Create toast message
        HBox toastContent = new HBox(10);
        toastContent.setAlignment(Pos.CENTER);

        Text checkIcon = new Text("✓");
        checkIcon.setFont(Font.font("System", FontWeight.BOLD, 18));
        checkIcon.setFill(Color.WHITE);

        Text message = new Text("Progress reset successfully!");
        message.setFont(Font.font("System", FontWeight.BOLD, 16));
        message.setFill(Color.WHITE);

        toastContent.getChildren().addAll(checkIcon, message);
        toastContainer.getChildren().add(toastContent);

        // Add toast to the root but position it at the bottom
        root.getChildren().add(toastContainer);
        StackPane.setAlignment(toastContainer, Pos.BOTTOM_CENTER);
        StackPane.setMargin(toastContainer, new Insets(0, 0, 50, 0));

        // Initially invisible
        toastContainer.setOpacity(0);

        // Animate toast in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Schedule toast removal
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastContainer);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> root.getChildren().remove(toastContainer));
            fadeOut.play();
        });
        pause.play();
    }
}