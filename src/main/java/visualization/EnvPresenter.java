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

import java.awt.*;
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
    private ToolEnvironment environment;
    private boolean clicksEnabled = true;
    private boolean inReplayMode = false;


    /**
     * Creates a new EnvPresenter for the given environment.
     *
     * @param env The game environment to visualize
     */
    public EnvPresenter(ToolEnvironment env) {
        this.env = env;

        this.fields = new ArrayList<>();
       // initialize();
        levelCheckTimer = new Timer(200, e -> checkLevelCompletion());
    }

    public void setEnvironment(ToolEnvironment env) {
        this.environment = env;
        getGamePanel().removeAll();
        SwingUtilities.invokeLater(() -> {
            initialize();
            levelCheckTimer.start();
            /*if (inReplayMode) {
                disableUserClicks();
            }*/
        });
    }
    public void setReplayMode(boolean replay) {
        this.inReplayMode = replay;
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
            levelCheckTimer.start();
            /*if (inReplayMode) {
                disableUserClicks();
            }*/
        });
    }

    public void disableUserClicks() {
        System.out.println("[EnvPresenter] disableUserClicks() called; fieldsCount = " + fields.size());
        clicksEnabled = false;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel maybeGrid = (JPanel) comp;
                for (Component inner : maybeGrid.getComponents()) {
                    if (inner instanceof FieldView) {
                        //System.out.println("  → disabling clicks on FieldView[" + i + "] = " + fields.get(i));
                        ((FieldView) inner).disableClicks();
                        //System.out.println("disabled clicks in Env,instance: " + inner);
                    }
                }
            }
        }
    }

    public void enableUserClicks() {
        this.clicksEnabled = true;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel maybeGrid = (JPanel) comp;
                for (Component inner : maybeGrid.getComponents()) {
                    if (inner instanceof FieldView) {
                        ((FieldView) inner).enableClicks();
                        //System.out.println("enabled clicks in Env,instance: " + inner);
                    }
                }
            }
        }
    }

    public void refreshViews() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel maybeGrid = (JPanel) comp;
                for (Component inner : maybeGrid.getComponents()) {
                    if (inner instanceof FieldView) {
                        ((FieldView) inner).repaint();
                        //System.out.println("enabled clicks in Env,instance: " + inner);
                    }
                }
            }
        }
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

        for (int row = 1; row <= env.rows(); row++) {
            for (int col = 1; col <= env.cols(); col++) {
                ToolField field = env.fieldAt(row, col);
                if (field.isBulb()) {
                    hasBulbs = true;
                    if (!field.light()) {
                        allBulbsLit = false;
                        break;
                    }
                }
            }
            if (!allBulbsLit) {
                break;
            }
        }
        return hasBulbs && allBulbsLit;
    }

    /**
     * Checks if the level is completed and triggers the callback if needed.
     */
    private void checkLevelCompletion() {
        if (!levelCompletionDetected && isLevelCompleted()) {
            levelCompletionDetected = true;
            levelCheckTimer.stop();
            if (levelCompletedCallback != null) {
                SwingUtilities.invokeLater(levelCompletedCallback);
            }
        }
    }

    /**
     * Initializes the game UI components.
     */
    private void initialize() {
        System.out.println("[EnvPresenter] initialize() START; fields was: " + fields.size());
        fields.clear();
        this.frame = new JFrame("VoltMaze");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(350, 400);
        this.frame.setPreferredSize(new Dimension(350, 400));
        this.frame.setResizable(false);

        int rows = this.env.rows();
        int cols = this.env.cols();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(15, 23, 42));

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 2, 2));
        gridPanel.setBackground(new Color(15, 23, 42)); // Dark blue background
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for(int row = 1; row <= rows; ++row) {
            for(int col = 1; col <= cols; ++col) {
                ToolField field = this.env.fieldAt(row, col);
                FieldView fieldView = new FieldView(field);
                gridPanel.add(fieldView);
                this.fields.add(fieldView);
            }
        }
        System.out.println("[EnvPresenter] initialize() END; fields now: " + fields.size());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        this.frame.getContentPane().add(mainPanel);
        this.frame.pack();

        if (inReplayMode) {
            SwingUtilities.invokeLater(() -> {
                System.out.println("[EnvPresenter] (inside initialize) disableUserClicks(); fieldsCount="
                        + fields.size());
                disableUserClicks();
            });
        }

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
        if (!levelCheckTimer.isRunning()) {
            levelCheckTimer.start();
        }
    }

    /**
     * Cleans up resources when the presenter is no longer needed.
     */
    public void cleanup() {
        levelCheckTimer.stop();

        if (fields != null) {
            fields.clear();
        }
        if (frame != null) {
            frame.dispose();
        }
    }

    /** Return the environment */
    public ToolEnvironment getEnvironment() {
        return env;
    }

    public boolean inReplayMode() {
        return inReplayMode;
    }
    public boolean isInReplayMode() {
        return inReplayMode;
    }

    /** Возвращает количество созданных FieldView. */
    public int getFieldsCount() {
        return fields.size();
    }

    /** Возвращает текущее состояние clicksEnabled у первого поля (или -1, если полей нет). */
    public boolean isFirstFieldClickable() {
        if (fields.isEmpty()) return false;
        return fields.get(0).isClicksEnabled();
    }

    /** Для отладки: печатает краткую информацию о состоянии Presenter. */
    public void debugPrintState() {
        System.out.println("[EnvPresenter] this=" + this
                + ", inReplayMode=" + inReplayMode
                + ", panel=" + mainPanel
                + ", fieldsCount=" + fields.size());
    }
}