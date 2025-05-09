/**
 * Soubor: src/main/java/ija.ijaProject/game/levels/LevelManager.java
 *
 * Popis:
 * Třída LevelManager zajišťuje evidenci a perzistenci dokončených úrovní hry.
 *   Používá vzor Singleton, aby v aplikaci existovala pouze jedna instance.
 *   Poskytuje metody pro:
 *     – označení úrovně jako dokončené (markLevelCompleted),
 *     – kontrolu, zda je úroveň dokončená (isLevelCompleted),
 *     – zjištění nejvyšší dokončené úrovně pro danou obtížnost (getHighestCompletedLevel),
 *     – načtení a uložení progressu do souboru (loadProgress, saveProgress),
 *     – resetování veškerého progressu (resetProgress),
 *     – získání všech dokončených úrovní (getCompletedLevels).
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */

package ija.ijaProject.game.levels;

import java.io.*;
import java.util.*;

/**
 * LevelManager handles tracking and persistence of level completion status.
 * It uses the Singleton pattern to ensure a single instance throughout the application.
 */
public class LevelManager {
    private static final String SAVE_FILE = "level_progress.dat";
    private Set<String> completedLevels = new HashSet<>();
    private static LevelManager instance;

    /**
     * Gets the singleton instance of LevelManager.
     *
     * @return The LevelManager instance
     */
    public static LevelManager getInstance() {
        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     * Loads saved progress when created.
     */
    private LevelManager() {
        loadProgress();
    }

    /**
     * Marks a level as completed.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level (0=Beginner, 1=Intermediate, 2=Advanced)
     */
    public void markLevelCompleted(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        completedLevels.add(levelKey);
        saveProgress();

        System.out.println("Level " + levelNumber + " at difficulty " + difficulty + " marked as completed");
    }

    /**
     * Checks if a level is completed.
     *
     * @param levelNumber The level number
     * @param difficulty The difficulty level
     * @return true if the level is completed, false otherwise
     */
    public boolean isLevelCompleted(int levelNumber, int difficulty) {
        String levelKey = difficulty + "-" + levelNumber;
        return completedLevels.contains(levelKey);
    }

    /**
     * Gets the highest completed level for a difficulty.
     *
     * @param difficulty The difficulty level
     * @return The highest completed level number, or 0 if none completed
     */
    public int getHighestCompletedLevel(int difficulty) {
        int highest = 0;
        for (String levelKey : completedLevels) {
            if (levelKey.startsWith(difficulty + "-")) {
                int level = Integer.parseInt(levelKey.split("-")[1]);
                highest = Math.max(highest, level);
            }
        }
        return highest;
    }

    /**
     * Saves progress to file.
     */
    private void saveProgress() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(completedLevels);
            System.out.println("Progress saved: " + completedLevels.size() + " levels completed");
        } catch (IOException e) {
            System.err.println("Error saving progress: " + e.getMessage());
        }
    }

    /**
     * Loads progress from file.
     */
    @SuppressWarnings("unchecked")
    private void loadProgress() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("No saved progress found, starting fresh");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            completedLevels = (Set<String>) ois.readObject();
            System.out.println("Progress loaded: " + completedLevels.size() + " levels completed");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading progress: " + e.getMessage());
            // If there's an error, start with empty progress
            completedLevels = new HashSet<>();
        }
    }

    /**
     * Resets all progress.
     */
    public void resetProgress() {
        completedLevels.clear();
        saveProgress();
        System.out.println("Progress reset");
    }

    /**
     * Gets all completed levels.
     *
     * @return A set of level keys in the format "difficulty-levelNumber"
     */
    public Set<String> getCompletedLevels() {
        return new HashSet<>(completedLevels);
    }
}