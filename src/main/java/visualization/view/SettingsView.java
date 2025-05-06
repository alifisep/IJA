package visualization.view;

import ija.ijaProject.settings.LanguageManager;
import ija.ijaProject.settings.SettingsManager;
import ija.ijaProject.settings.SoundManager;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
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

import java.util.Map;

public class SettingsView {
    private final StackPane root;
    private final Stage stage;
    private Button backButton;
    private final VBox contentContainer;
    private final LanguageManager languageManager = LanguageManager.getInstance();
    private final SoundManager soundManager = SoundManager.getInstance();
    private final SettingsManager settingsManager = SettingsManager.getInstance();
    private EventHandler<ActionEvent> onLanguageChangeHandler;

    public SettingsView(Stage stage) {
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

        // Create settings container
        VBox settingsContainer = createSettingsContainer();

        // Add electric circuit decoration
        Pane circuitDecoration = createCircuitDecoration();

        // Add all elements to the content container
        contentContainer.getChildren().addAll(headerBox, settingsContainer);

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
        Text settingsTitle = new Text(languageManager.getString("Settings"));
        settingsTitle.setFont(Font.font("System", FontWeight.BOLD, 36));
        settingsTitle.setFill(Color.web("#7DD3FC"));

        // Add glow effect to title
        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.web("#0EA5E9"));
        titleGlow.setRadius(10);
        titleGlow.setSpread(0.2);
        settingsTitle.setEffect(titleGlow);

        // Create back button with arrow
        backButton = new Button("←  " + languageManager.getString("back"));
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
        headerContainer.getChildren().add(settingsTitle);
        StackPane.setAlignment(settingsTitle, Pos.CENTER);

        // Add the back button to the left of the StackPane
        headerContainer.getChildren().add(backButton);
        StackPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return headerContainer;
    }

    private VBox createSettingsContainer() {
        // Create a container with semi-transparent background
        VBox container = new VBox(25);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(30));
        container.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.85);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-border-color: rgba(14, 165, 233, 0.3);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 15px;"
        );

        // Create language settings
        VBox languageSettings = createLanguageSettings();

        // Create sound settings
        VBox soundSettings = createSoundSettings();

        // Create reset settings button
        Button resetButton = createResetSettingsButton();

        // Add a spacer before the reset button
        Region spacer = new Region();
        spacer.setPrefHeight(10);

        // Add all settings to the container
        container.getChildren().addAll(languageSettings, soundSettings, spacer, resetButton);

        return container;
    }

    private VBox createLanguageSettings() {
        VBox languageBox = new VBox(15);
        languageBox.setAlignment(Pos.TOP_LEFT);

        // Create section title
        Text sectionTitle = new Text(languageManager.getString("language_settings"));
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setFill(Color.web("#0EA5E9"));

        // Add glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#0EA5E9"));
        glow.setRadius(5);
        glow.setSpread(0.1);
        sectionTitle.setEffect(glow);

        // Create language selector
        HBox languageSelector = new HBox(15);
        languageSelector.setAlignment(Pos.CENTER_LEFT);

        Text languageLabel = new Text(languageManager.getString("language") + ":");
        languageLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        languageLabel.setFill(Color.WHITE);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.setStyle(
                "-fx-background-color: rgba(14, 165, 233, 0.2);" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: #0EA5E9;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;"
        );

        // Add languages to combo box
        for (Map.Entry<String, String> entry : LanguageManager.AVAILABLE_LANGUAGES.entrySet()) {
            languageComboBox.getItems().add(entry.getValue());
            if (entry.getKey().equals(languageManager.getCurrentLanguage())) {
                languageComboBox.setValue(entry.getValue());
            }
        }

        // Set action for language change
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            String languageCode = null;

            // Find language code for selected language
            for (Map.Entry<String, String> entry : LanguageManager.AVAILABLE_LANGUAGES.entrySet()) {
                if (entry.getValue().equals(selectedLanguage)) {
                    languageCode = entry.getKey();
                    break;
                }
            }

            if (languageCode != null) {
                // Play sound
                soundManager.playSound("button_click");

                // Change language
                languageManager.loadLanguage(languageCode);

                // Notify handler
                if (onLanguageChangeHandler != null) {
                    onLanguageChangeHandler.handle(new ActionEvent());
                }
            }
        });

        languageSelector.getChildren().addAll(languageLabel, languageComboBox);

        // Add language note
        Text languageNote = new Text(languageManager.getString("language_note"));
        languageNote.setFont(Font.font("System", FontWeight.NORMAL, 14));
        languageNote.setFill(Color.web("#94A3B8"));
        languageNote.setWrappingWidth(500);

        // Add all to language box
        languageBox.getChildren().addAll(sectionTitle, languageSelector, languageNote);

        return languageBox;
    }

    private VBox createSoundSettings() {
        VBox soundBox = new VBox(15);
        soundBox.setAlignment(Pos.TOP_LEFT);

        // Create section title
        Text sectionTitle = new Text(languageManager.getString("sound_settings"));
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setFill(Color.web("#0EA5E9"));

        // Add glow effect
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#0EA5E9"));
        glow.setRadius(5);
        glow.setSpread(0.1);
        sectionTitle.setEffect(glow);

        // Create sound effects settings
        VBox soundEffectsBox = new VBox(10);

        // Sound effects toggle
        HBox soundToggleBox = new HBox(15);
        soundToggleBox.setAlignment(Pos.CENTER_LEFT);

        Text soundLabel = new Text(languageManager.getString("sound_effects") + ":");
        soundLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        soundLabel.setFill(Color.WHITE);

        CheckBox soundToggle = new CheckBox();
        soundToggle.setSelected(soundManager.isSoundEnabled());
        styleCheckBox(soundToggle);

        soundToggle.setOnAction(e -> {
            soundManager.setSoundEnabled(soundToggle.isSelected());

            // Play test sound if enabled
            if (soundToggle.isSelected()) {
                soundManager.playSound("button_click");
            }
        });

        soundToggleBox.getChildren().addAll(soundLabel, soundToggle);

        // Sound volume slider
        HBox soundVolumeBox = new HBox(15);
        soundVolumeBox.setAlignment(Pos.CENTER_LEFT);

        Text volumeLabel = new Text(languageManager.getString("volume") + ":");
        volumeLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        volumeLabel.setFill(Color.WHITE);

        Slider volumeSlider = new Slider(0, 1, soundManager.getSoundVolume());
        volumeSlider.setPrefWidth(200);
        styleSlider(volumeSlider);

        volumeSlider.disableProperty().bind(Bindings.not(soundToggle.selectedProperty()));

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundManager.setSoundVolume(newVal.doubleValue());

            // Play test sound occasionally
            if (Math.abs(newVal.doubleValue() - oldVal.doubleValue()) > 0.1) {
                soundManager.playSound("button_click");
            }
        });

        Text volumePercentage = new Text();
        volumePercentage.setFont(Font.font("System", FontWeight.NORMAL, 14));
        volumePercentage.setFill(Color.WHITE);

        volumePercentage.textProperty().bind(
                Bindings.createStringBinding(() ->
                                String.format("%d%%", Math.round(volumeSlider.getValue() * 100)),
                        volumeSlider.valueProperty())
        );

        soundVolumeBox.getChildren().addAll(volumeLabel, volumeSlider, volumePercentage);

        // Add sound effects elements to container
        soundEffectsBox.getChildren().addAll(soundToggleBox, soundVolumeBox);

        // Create music settings
        VBox musicBox = new VBox(10);
        musicBox.setPadding(new Insets(10, 0, 0, 0));

        // Music toggle
        HBox musicToggleBox = new HBox(15);
        musicToggleBox.setAlignment(Pos.CENTER_LEFT);

        Text musicLabel = new Text(languageManager.getString("background_music") + ":");
        musicLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        musicLabel.setFill(Color.WHITE);

        CheckBox musicToggle = new CheckBox();
        musicToggle.setSelected(soundManager.isMusicEnabled());
        styleCheckBox(musicToggle);

        musicToggle.setOnAction(e -> {
            soundManager.setMusicEnabled(musicToggle.isSelected());

            // Play test sound
            soundManager.playSound("button_click");
        });

        musicToggleBox.getChildren().addAll(musicLabel, musicToggle);

        // Music volume slider
        HBox musicVolumeBox = new HBox(15);
        musicVolumeBox.setAlignment(Pos.CENTER_LEFT);

        Text musicVolumeLabel = new Text(languageManager.getString("volume") + ":");
        musicVolumeLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        musicVolumeLabel.setFill(Color.WHITE);

        Slider musicVolumeSlider = new Slider(0, 1, soundManager.getMusicVolume());
        musicVolumeSlider.setPrefWidth(200);
        styleSlider(musicVolumeSlider);

        // Disable slider if music is disabled
        musicVolumeSlider.disableProperty().bind(Bindings.not(musicToggle.selectedProperty()));

        // Update volume when slider changes
        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundManager.setMusicVolume(newVal.doubleValue());
        });

        // Add volume percentage label
        Text musicVolumePercentage = new Text();
        musicVolumePercentage.setFont(Font.font("System", FontWeight.NORMAL, 14));
        musicVolumePercentage.setFill(Color.WHITE);

        // Bind percentage text to slider value
        musicVolumePercentage.textProperty().bind(
                Bindings.createStringBinding(() ->
                                String.format("%d%%", Math.round(musicVolumeSlider.getValue() * 100)),
                        musicVolumeSlider.valueProperty())
        );

        musicVolumeBox.getChildren().addAll(musicVolumeLabel, musicVolumeSlider, musicVolumePercentage);

        // Add music elements to container
        musicBox.getChildren().addAll(musicToggleBox, musicVolumeBox);

        // Add sound note
        Text soundNote = new Text(languageManager.getString("sound_note"));
        soundNote.setFont(Font.font("System", FontWeight.NORMAL, 14));
        soundNote.setFill(Color.web("#94A3B8"));
        soundNote.setWrappingWidth(500);

        // Add all to sound box
        soundBox.getChildren().addAll(sectionTitle, soundEffectsBox, musicBox, soundNote);

        return soundBox;
    }

    private Button createResetSettingsButton() {
        Button resetButton = new Button(languageManager.getString("reset_settings"));
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

        // Set action to reset settings
        resetButton.setOnAction(e -> {
            // Play sound
            soundManager.playSound("button_click");

            // Show confirmation dialog
            showResetConfirmationDialog();
        });

        return resetButton;
    }

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
        Text title = new Text(languageManager.getString("reset_settings"));
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setFill(Color.web("#FBBF24"));

        // Create message
        Text message = new Text(languageManager.getString("reset_settings_confirm"));
        message.setFont(Font.font("System", FontWeight.NORMAL, 16));
        message.setFill(Color.WHITE);
        message.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        message.setWrappingWidth(340);

        // Create buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button cancelButton = new Button(languageManager.getString("cancel"));
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

        Button confirmButton = new Button(languageManager.getString("reset_all"));
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
        cancelButton.setOnAction(e -> {
            // Play sound
            soundManager.playSound("button_click");
            dialogStage.close();
        });

        confirmButton.setOnAction(e -> {
            // Play sound
            soundManager.playSound("button_click");

            // Reset settings
            settingsManager.resetSettings();

            // Reload settings
            boolean soundWasEnabled = soundManager.isSoundEnabled();

            // Reload language
            String currentLanguage = languageManager.getCurrentLanguage();
            languageManager.loadLanguage("en"); // Reset to English

            // Notify handler if language changed
            if (!currentLanguage.equals("en") && onLanguageChangeHandler != null) {
                onLanguageChangeHandler.handle(new ActionEvent());
            }

            // Close dialog
            dialogStage.close();

            // Show confirmation toast
            showResetConfirmationToast();
        });

        // Show the dialog
        dialogStage.show();
    }

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

        Text message = new Text(languageManager.getString("settings_reset_success"));
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

    private void addButtonHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            // Change style on hover
            String style = button.getStyle();
            if (style.contains("rgba(14, 165, 233, 0.2)")) {
                style = style.replace("rgba(14, 165, 233, 0.2)", "rgba(14, 165, 233, 0.4)");
            } else if (style.contains("rgba(100, 116, 139, 0.3)")) {
                style = style.replace("rgba(100, 116, 139, 0.3)", "rgba(100, 116, 139, 0.5)");
            }
            button.setStyle(style);

            // Enhance glow effect
            DropShadow hoverShadow = new DropShadow();
            if (style.contains("#0EA5E9")) {
                hoverShadow.setColor(Color.web("#0EA5E9"));
            } else {
                hoverShadow.setColor(Color.web("#64748B"));
            }
            hoverShadow.setRadius(15);
            hoverShadow.setSpread(0.2);
            button.setEffect(hoverShadow);

            // Play sound
            soundManager.playSound("button_click");
        });

        button.setOnMouseExited(e -> {
            // Scale animation
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            // Restore original style
            String style = button.getStyle();
            if (style.contains("rgba(14, 165, 233, 0.4)")) {
                style = style.replace("rgba(14, 165, 233, 0.4)", "rgba(14, 165, 233, 0.2)");
            } else if (style.contains("rgba(100, 116, 139, 0.5)")) {
                style = style.replace("rgba(100, 116, 139, 0.5)", "rgba(100, 116, 139, 0.3)");
            }
            button.setStyle(style);

            // Restore original shadow
            DropShadow originalShadow = new DropShadow();
            if (style.contains("#0EA5E9")) {
                originalShadow.setColor(Color.web("#0EA5E9"));
            } else {
                originalShadow.setColor(Color.web("#64748B"));
            }
            originalShadow.setRadius(10);
            originalShadow.setSpread(0);
            button.setEffect(originalShadow);
        });
    }

    private void styleCheckBox(CheckBox checkBox) {
        checkBox.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;"
        );
    }

    private void styleSlider(Slider slider) {
        slider.setStyle(
                "-fx-control-inner-background: rgba(15, 23, 42, 0.7);" +
                        "-fx-track-background: rgba(15, 23, 42, 0.7);" +
                        "-fx-track-color: rgba(15, 23, 42, 0.7);" +
                        "-fx-accent: #0EA5E9;" // Color of the filled part of the slider
        );
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
        System.out.println("SettingsView cleanup called");
        // Stop animations here if needed
    }

    public void setOnBackAction(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(e -> {
            // Play sound
            soundManager.playSound("button_click");
            handler.handle(e);
        });
    }

    public void setOnLanguageChangeHandler(EventHandler<ActionEvent> handler) {
        this.onLanguageChangeHandler = handler;
    }

    // Getter for the root pane
    public StackPane getRoot() {
        return root;
    }
}