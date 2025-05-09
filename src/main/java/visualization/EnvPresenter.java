/**
 * Soubor: src/main/java/visualization/EnvPresenter.java
 *
 * Popis:
 *
 * Třída prezentující (vizualizující) model prostředí v GUI (Swing).
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package visualization;

import ija.ijaProject.game.Game;
import visualization.common.ToolEnvironment;
import visualization.common.ToolField;
import visualization.view.FieldView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.Timer;

/**
 * EnvPresenter handles the visualization of the game environment and detects level completion.
 */
public class EnvPresenter {
    public static final int CELL_SIZE = 40;
    private final ToolEnvironment env;
    private List<FieldView> fields;
    private JFrame frame;
    private JPanel mainPanel;
    private Runnable levelCompletedCallback;
    private boolean levelCompletionDetected = false;
    private final Timer levelCheckTimer;

    /**
     * Creates a new EnvPresenter for the given environment.
     *
     * @param env The game environment to visualize
     */
    public EnvPresenter(ToolEnvironment env) {
        this.env = env;

        this.fields = new ArrayList<>();
        initialize();
        // Create a timer to periodically check for level completion
        levelCheckTimer = new Timer(500, e -> checkLevelCompletion());
    }

    /**
     * Gets the main game panel for embedding in JavaFX.
     *
     * @return The JPanel containing the game grid
     */
    public JPanel getGamePanel() {
        return mainPanel;
    }

    /**
     * Opens the game in a standalone window.
     */
    public void open() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.initialize();
                this.frame.setVisible(true);

                // Start checking for level completion
                levelCheckTimer.start();
            });
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(EnvPresenter.class.getName()).log(Level.SEVERE, "Error opening game window", var2);
        }
    }

    /**
     * Initializes the game UI without showing it.
     * Used when embedding the game in JavaFX.
     */
    public void init() {
        SwingUtilities.invokeLater(() -> {
            this.initialize();

            // Start checking for level completion
            levelCheckTimer.start();
        });
    }

    /**
     * Sets a callback to be called when the level is completed.
     *
     * @param callback The callback to run when level is completed
     */
    public void setLevelCompletedCallback(Runnable callback) {
        this.levelCompletedCallback = callback;
    }

    /**
     * Checks if all bulbs in the game are lit, indicating level completion.
     *
     * @return true if all bulbs are lit, false otherwise
     */
    private boolean isLevelCompleted() {
        boolean hasBulbs = false;
        boolean allBulbsLit = true;

        // Check all fields in the environment
        for (int row = 1; row <= env.rows(); row++) {
            for (int col = 1; col <= env.cols(); col++) {
                ToolField field = env.fieldAt(row, col);

                // If it's a bulb, check if it's lit
                if (field.isBulb()) {
                    hasBulbs = true;
                    if (!field.light()) {
                        allBulbsLit = false;
                        break;
                    }
                }
            }

            // If we already found an unlit bulb, no need to check further
            if (!allBulbsLit) {
                break;
            }
        }

        // Level is completed if there are bulbs and all are lit
        return hasBulbs && allBulbsLit;
    }

    /**
     * Checks if the level is completed and triggers the callback if needed.
     */
    private void checkLevelCompletion() {
        // Check if level is completed and not already detected
        if (!levelCompletionDetected && isLevelCompleted()) {
            levelCompletionDetected = true;
            levelCheckTimer.stop();

            // Invoke the callback on the Swing thread
            if (levelCompletedCallback != null) {
                SwingUtilities.invokeLater(levelCompletedCallback);
            }
        }
    }

    /**
     * Initializes the game UI components.
     */
    private void initialize() {
        this.frame = new JFrame("VoltMaze");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(350, 400);
        this.frame.setPreferredSize(new Dimension(350, 400));
        this.frame.setResizable(false);

        int rows = this.env.rows();
        int cols = this.env.cols();

        // Create main panel with dark background
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(15, 23, 42)); // Dark blue background

        // Create grid panel with spacing
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 2, 2));
        gridPanel.setBackground(new Color(15, 23, 42)); // Dark blue background
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and add field views
        for(int row = 1; row <= rows; ++row) {
            for(int col = 1; col <= cols; ++col) {
                ToolField field = this.env.fieldAt(row, col);
                FieldView fieldView = new FieldView(field);
                gridPanel.add(fieldView);
                this.fields.add(fieldView);
            }
        }

        // Add grid panel to main panel with padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Add main panel to frame
        this.frame.getContentPane().add(mainPanel);
        this.frame.pack();

        // Reset level completion state
        levelCompletionDetected = false;
    }

    /**
     * Gets the list of field views.
     *
     * @return A copy of the field views list
     */
    protected List<FieldView> fields() {
        return new ArrayList<>(this.fields);
    }

    /**
     * Resets the level completion detection state.
     * Call this when starting a new level.
     */
    public void resetLevelCompletionState() {
        levelCompletionDetected = false;

        // Restart the timer if it was stopped
        if (!levelCheckTimer.isRunning()) {
            levelCheckTimer.start();
        }
    }

    /**
     * Cleans up resources when the presenter is no longer needed.
     */
    public void cleanup() {
        // Stop the timer
        levelCheckTimer.stop();

        // Clear fields list
        if (fields != null) {
            fields.clear();
        }

        // Dispose frame if it exists
        if (frame != null) {
            frame.dispose();
        }
    }

    /** Return the environment */
    public ToolEnvironment getEnvironment() {
        return env;
    }
}