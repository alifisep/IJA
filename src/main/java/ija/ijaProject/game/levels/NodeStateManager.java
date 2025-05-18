/**
 * Soubor: src/main/java/ija.ijaProject/game/levels/NodeStateManager.java
 *
 * Popis:
 *
 * Manages the saving and loading of node states for game levels.
 * This allows the game to remember the state of nodes (position, rotation)
 *  when a player exits a level and returns to it later.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package ija.ijaProject.game.levels;

import ija.ijaProject.common.GameNode;
import ija.ijaProject.common.Position;
import ija.ijaProject.common.Side;
import ija.ijaProject.game.Game;

import java.io.*;
import java.util.*;

/**
 * Manages the saving and loading of node states for game levels.
 * This allows the game to remember the state of nodes (position, rotation)
 * when a player exits a level and returns to it later.
 */
public class NodeStateManager {
    private static NodeStateManager instance;
    private final Map<String, Map<String, NodeState>> nodeStates;
    private final String saveFilePath;
    private final Map<String, Boolean> levelChangesDetected;
    private final Set<String> firstSaveNeeded = new HashSet<>();


    public void markFirstSaveNeeded(int levelNumber, int difficulty) {

        String levelKey = difficulty + "-" + levelNumber;
        firstSaveNeeded.add(levelKey);
        System.out.println("Marked first save needed for level " + levelNumber +
                " at difficulty " + difficulty +
                ", keys in set: " + firstSaveNeeded.size());

        for (String key : firstSaveNeeded) {
            System.out.println("  Key in firstSaveNeeded: " + key);
        }
    }

    public boolean isFirstSaveNeeded(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        return firstSaveNeeded.contains(levelKey);
    }

    public void clearFirstSaveNeeded(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        firstSaveNeeded.remove(levelKey);
    }

    /**
     * Private constructor for singleton pattern.
     */
    private NodeStateManager() {
        nodeStates = new HashMap<>();
        levelChangesDetected = new HashMap<>();
        saveFilePath = "node_states.ser";
        loadNodeStates();
    }

    /**
     * Gets the singleton instance of NodeStateManager.
     *
     * @return The NodeStateManager instance
     */
    public static synchronized NodeStateManager getInstance() {
        if (instance == null) {
            instance = new NodeStateManager();
        }
        return instance;
    }

    /**
     * Records that changes have been made to a level.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     */
    public void markLevelChanged(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        levelChangesDetected.put(levelKey, true);
        System.out.println("MarkLevel:Changes detected for level " + levelNumber + " at difficulty " + difficulty);
    }

    /**
     * Checks if changes have been detected for a level.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     * @return true if changes have been detected, false otherwise
     */
    public boolean hasChangesDetected(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        return levelChangesDetected.getOrDefault(levelKey, false);
    }

    /**
     * Resets the changes detected flag for a level.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     */
    public void resetChangesDetected(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        levelChangesDetected.put(levelKey, false);
    }

    /**
     * Checks if a game is completed (all bulbs lit).
     *
     * @param game The game to check
     * @return true if the game is completed, false otherwise
     */
    public boolean isGameCompleted(Game game) {
        return !game.anyBulbLit();
    }

    /**
     * Saves the current state of all nodes in a game level.
     * Only saves if the level is NOT completed AND changes have been made.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     * @param game The Game instance containing the nodes
     * @return true if the state was saved, false otherwise
     */
    public boolean saveNodeStates(int levelNumber, int difficulty, Game game) {
        boolean isLevelCompleted = LevelManager.getInstance().isLevelCompleted(levelNumber, difficulty);
        boolean hasChanges = hasChangesDetected(levelNumber, difficulty);
        boolean needsFirstSave = isFirstSaveNeeded(levelNumber, difficulty);

        System.out.println("Saving state for level " + levelNumber + " at difficulty " + difficulty);
        System.out.println("Level completed: " + isLevelCompleted);
        System.out.println("Changes detected: " + hasChanges);
        System.out.println("First save needed: " + needsFirstSave);

        if (isLevelCompleted) {
            System.out.println("Level is completed, not saving state");
            return false;
        }
        if (!hasChanges && !needsFirstSave) {
            System.out.println("No changes detected and not first save, not saving state");
            return false;
        }
        String levelKey = difficulty + "-" + levelNumber;
        Map<String, NodeState> levelNodeStates = new HashMap<>();
        for (GameNode node : game.getNodes()) {
            if (game.isPlayebleNode(node)) {
                Position position = node.getPosition();
                String nodeId = position.row() + "-" + position.col();
                NodeState state = new NodeState();
                state.position = new int[]{position.row(), position.col()};
                state.rotationCount = node.getRotationCount();
                state.connectors = new ArrayList<>();
                for (Side side : node.getConnectors()) {
                    state.connectors.add(side.ordinal());
                }

                levelNodeStates.put(nodeId, state);
            }
        }
        nodeStates.put(levelKey, levelNodeStates);
        markLevelChanged(levelNumber, difficulty);
        clearFirstSaveNeeded(levelNumber, difficulty);
        saveNodeStatesToDisk();
        System.out.println("Successfully saved state for level " + levelNumber + " at difficulty " + difficulty);
        return true;
    }

    /**
     * Loads the saved state for a specific level and applies it to the game.
     * Only loads if the level is NOT completed and saved state exists.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     * @param game The Game instance to apply the states to
     * @return true if states were loaded and applied, false otherwise
     */
    public boolean loadNodeStates(int levelNumber, int difficulty, Game game) {

        boolean isLevelCompleted = LevelManager.getInstance().isLevelCompleted(levelNumber, difficulty);

        String levelKey = difficulty + "-" + levelNumber;

        if (isLevelCompleted) {
            System.out.println("Level " + levelNumber + " at difficulty " + difficulty + " is completed, not loading saved state");
            return false;
        }

        if (!nodeStates.containsKey(levelKey)) {
            System.out.println("No saved state found for level " + levelNumber + " at difficulty " + difficulty);
            return false;
        }

        Map<String, NodeState> levelNodeStates = nodeStates.get(levelKey);

        for (GameNode node : game.getNodes()) {
            if (game.isPlayebleNode(node)) {
                Position position = node.getPosition();
                String nodeId = position.row() + "-" + position.col();
                if (levelNodeStates.containsKey(nodeId)) {
                    NodeState savedState = levelNodeStates.get(nodeId);
                    node.resetRotationCount();
                    int targetRotations = savedState.rotationCount % 4;
                    for (int i = 0; i < targetRotations; i++) {
                        node.turn();
                    }
                }
            }
        }

        resetChangesDetected(levelNumber, difficulty);

        System.out.println("Loaded and applied saved state for level " + levelNumber + " at difficulty " + difficulty);
        return true;
    }

    /**
     * Checks if there are saved states for a specific level.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     * @return true if saved states exist, false otherwise
     */
    public boolean hasSavedStates(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        boolean result = nodeStates.containsKey(levelKey);
        System.out.println("Checking saved states for level " + levelNumber +
                " at difficulty " + difficulty + ": " + result);
        if (result) {
            System.out.println("Found saved state with " +
                    nodeStates.get(levelKey).size() + " nodes");
        }
        return result;
    }

    /**
     * Clears the saved states for a specific level.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     */
    public void clearNodeStates(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        if (nodeStates.containsKey(levelKey)) {
            nodeStates.remove(levelKey);
            resetChangesDetected(levelNumber, difficulty);
            saveNodeStatesToDisk();
            System.out.println("Cleared saved state for level " + levelNumber + " at difficulty " + difficulty);
        }
    }

    /**
     * Saves all node states to disk.
     */
    private void saveNodeStatesToDisk() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
                oos.writeObject(nodeStates);
                System.out.println("Saved node states to disk: " + nodeStates.size() + " levels");
            }
            try (ObjectOutputStream changesOos = new ObjectOutputStream(new FileOutputStream(saveFilePath + ".changes"))) {
                changesOos.writeObject(levelChangesDetected);
                System.out.println("Saved changes detected map to disk: " + levelChangesDetected.size() + " entries");
            }
        } catch (IOException e) {
            System.err.println("Error saving node states to disk: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads all node states from disk.
     */
    @SuppressWarnings("unchecked")
    private void loadNodeStates() {
        File file = new File(saveFilePath);
        if (!file.exists()) {
            System.out.println("No saved node states file found");
            return;
        }

        try {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
                Map<String, Map<String, NodeState>> loadedStates =
                        (Map<String, Map<String, NodeState>>) ois.readObject();
                nodeStates.clear();
                nodeStates.putAll(loadedStates);
                System.out.println("Loaded node states from disk: " + nodeStates.size() + " levels");
            }

            File changesFile = new File(saveFilePath + ".changes");
            if (changesFile.exists()) {
                try (ObjectInputStream changesOis = new ObjectInputStream(new FileInputStream(changesFile))) {
                    Map<String, Boolean> loadedChanges = (Map<String, Boolean>) changesOis.readObject();
                    levelChangesDetected.clear();
                    levelChangesDetected.putAll(loadedChanges);
                    System.out.println("Loaded changes detected map from disk: " + levelChangesDetected.size() + " entries");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading node states from disk: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inner class to store the state of a node.
     * This class must be serializable.
     */
    private static class NodeState implements Serializable {
        private static final long serialVersionUID = 1L;

        int[] position;
        int rotationCount;
        List<Integer> connectors;
    }
}
