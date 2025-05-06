package ija.ijaProject.game.levels;

import ija.ijaProject.common.GameNode;
import ija.ijaProject.common.Position;
import ija.ijaProject.common.Side;
import ija.ijaProject.game.Game;
import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingNode;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import visualization.EnvPresenter;
import visualization.view.InfoPresenter;

import javax.swing.*;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GameLevels class handles the creation and management of different game levels.
 * It provides methods to create and load specific levels based on level number and difficulty.
 */
public class GameLevels {

    // ==================== BEGINNER LEVELS (1-10) ====================

    private static final Object[][] LEVEL_1_BEGINNER = {
            {"P", 2, 2, Side.SOUTH},
            {"L", 3, 2, Side.EAST, Side.NORTH},
            {"L", 3, 3, Side.WEST, Side.SOUTH},
            {"L", 4, 3, Side.NORTH, Side.SOUTH},
            {"B", 5, 3, Side.NORTH}
    };



    private static final Object[][] LEVEL_2_BEGINNER = {
            {"P", 2, 2, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.SOUTH},
            {"L", 5, 2, Side.NORTH, Side.EAST},
            {"L", 5, 3, Side.WEST, Side.EAST},
            {"L", 5, 4, Side.WEST, Side.SOUTH},
            {"B", 6, 4, Side.NORTH}
    };


    private static final Object[][] LEVEL_3_BEGINNER = {
            {"P", 2, 3, Side.SOUTH},
            {"L", 3, 3, Side.NORTH, Side.SOUTH, Side.EAST},
            {"L", 4, 3, Side.NORTH, Side.SOUTH},
            {"B", 5, 3, Side.NORTH},
            {"L", 3, 4, Side.WEST, Side.EAST},
            {"L", 3, 5, Side.WEST, Side.SOUTH},
            {"L", 4, 5, Side.NORTH, Side.SOUTH},
            {"B", 5, 5, Side.NORTH}
    };


    private static final Object[][] LEVEL_4_BEGINNER = {
            {"P", 2, 4, Side.SOUTH},
            {"L", 3, 4, Side.NORTH, Side.SOUTH, Side.WEST, Side.EAST},
            {"L", 4, 4, Side.NORTH, Side.SOUTH},
            {"L", 5, 4, Side.NORTH, Side.SOUTH},
            {"B", 6, 4, Side.NORTH},
            {"L", 3, 3, Side.EAST, Side.SOUTH},
            {"B", 4, 3, Side.NORTH},
            {"L", 3, 5, Side.WEST, Side.SOUTH},
            {"L", 4, 5, Side.NORTH, Side.EAST},
            {"B", 4, 6, Side.WEST}
    };



    private static final Object[][] LEVEL_5_BEGINNER = {
            {"P", 2, 5, Side.SOUTH},
            {"L", 3, 5, Side.NORTH, Side.SOUTH, Side.WEST, Side.EAST},
            {"L", 4, 5, Side.NORTH, Side.SOUTH,Side.WEST},
            {"B", 5, 5, Side.NORTH},
            {"L", 3, 4, Side.EAST, Side.WEST},
            {"L", 3, 3, Side.EAST, Side.SOUTH},
            {"B", 4, 3, Side.NORTH},
            {"L", 3, 6, Side.WEST, Side.EAST,Side.SOUTH},
            {"L", 3, 7, Side.WEST, Side.SOUTH},
            {"B", 4, 7, Side.NORTH},
            {"L", 4, 4, Side.EAST, Side.SOUTH},
            {"B", 5, 4, Side.NORTH},
            {"L", 4, 6, Side.NORTH, Side.SOUTH},
            {"B", 5, 6, Side.NORTH}
    };


    private static final Object[][] LEVEL_6_BEGINNER = {
            {"P", 2, 4, Side.SOUTH},
            {"L", 3, 4, Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.NORTH, Side.SOUTH,Side.EAST},
            {"L", 5, 4, Side.NORTH, Side.SOUTH},
            {"L", 6, 4, Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.NORTH, Side.SOUTH,Side.EAST},
            {"B", 8, 4, Side.NORTH},
            {"L", 3, 3, Side.EAST, Side.SOUTH},
            {"B", 4, 3, Side.NORTH},
            {"L", 3, 5, Side.WEST, Side.SOUTH},
            {"B", 4, 5, Side.NORTH},
            {"L", 6, 3, Side.SOUTH, Side.EAST},
            {"B", 7, 3, Side.NORTH},
            {"L", 6, 5, Side.WEST, Side.SOUTH},
            {"B", 7, 5, Side.NORTH}
    };


    private static final Object[][] LEVEL_7_BEGINNER = {
            {"P", 2, 6, Side.SOUTH},
            {"L", 3, 6, Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.NORTH, Side.SOUTH},
            {"L", 5, 6, Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.NORTH, Side.SOUTH},
            {"L", 7, 6, Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.NORTH, Side.SOUTH,Side.EAST},
            {"B", 9, 6, Side.NORTH},
            {"L", 3, 5, Side.EAST, Side.WEST},
            {"B", 4, 5, Side.SOUTH},
            {"L", 3, 7, Side.WEST, Side.SOUTH},
            {"B", 4, 7, Side.NORTH},
            {"L", 5, 5, Side.EAST, Side.NORTH, Side.SOUTH},
            {"B", 6, 5, Side.NORTH},
            {"L", 7, 7, Side.WEST, Side.EAST},
            {"B", 8, 7, Side.WEST}
    };

    private static final Object[][] LEVEL_8_BEGINNER = {
            {"P", 2, 3, Side.SOUTH},
            {"L", 3, 3, Side.SOUTH, Side.EAST},
            {"L", 3, 4, Side.WEST, Side.EAST},
            {"L", 3, 5, Side.WEST, Side.SOUTH},
            {"L", 4, 5, Side.NORTH, Side.SOUTH},
            {"L", 5, 5, Side.NORTH, Side.EAST},
            {"B", 6, 6, Side.WEST}
    };


    private static final Object[][] LEVEL_9_BEGINNER = {
            {"P", 2, 4, Side.SOUTH},
            {"L", 3, 4, Side.NORTH, Side.EAST},
            {"L", 3, 5, Side.WEST, Side.SOUTH},
            {"L", 4, 5, Side.NORTH, Side.SOUTH},
            {"L", 5, 5, Side.NORTH, Side.EAST},
            {"L", 5, 6, Side.WEST, Side.SOUTH},
            {"B", 6, 6, Side.NORTH}
    };


    private static final Object[][] LEVEL_10_BEGINNER = {
            {"P", 2, 4, Side.SOUTH},
            {"L", 3, 4, Side.NORTH, Side.SOUTH},
            {"L", 4, 4, Side.NORTH, Side.EAST},
            {"L", 4, 5, Side.WEST, Side.EAST},
            {"L", 4, 6, Side.WEST, Side.SOUTH},
            {"L", 5, 6, Side.NORTH, Side.EAST},
            {"B", 5, 7, Side.WEST}
    };


    // ==================== INTERMEDIATE LEVELS (11-20) ====================

    // Level 11: Introduction to intermediate difficulty
    private static final Object[][] LEVEL_11_INTERMEDIATE = {
            {"L", 3, 3, Side.EAST, Side.SOUTH},
            {"L", 3, 4, Side.EAST, Side.WEST},
            {"L", 3, 5, Side.EAST, Side.WEST},
            {"L", 3, 6, Side.EAST, Side.WEST},
            {"L", 3, 7, Side.WEST, Side.SOUTH},
            {"L", 4, 3, Side.NORTH, Side.SOUTH},
            {"L", 4, 7, Side.NORTH, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.EAST},
            {"L", 5, 4, Side.EAST, Side.WEST},
            {"L", 5, 5, Side.EAST, Side.WEST},
            {"L", 5, 6, Side.EAST, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.WEST},
            {"L", 6, 4, Side.NORTH, Side.SOUTH},
            {"L", 6, 5, Side.NORTH, Side.SOUTH},
            {"L", 6, 6, Side.NORTH, Side.SOUTH},
            {"B", 2, 5, Side.SOUTH},
            {"B", 4, 2, Side.EAST},
            {"B", 4, 8, Side.WEST},
            {"B", 7, 5, Side.NORTH},
            {"P", 4, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 12: Complex grid with multiple paths
    private static final Object[][] LEVEL_12_INTERMEDIATE = {
            {"L", 2, 4, Side.EAST, Side.SOUTH},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.WEST, Side.SOUTH},
            {"L", 3, 4, Side.NORTH, Side.EAST},
            {"L", 3, 6, Side.NORTH, Side.WEST},
            {"L", 4, 3, Side.EAST, Side.SOUTH},
            {"L", 4, 4, Side.NORTH, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.SOUTH},
            {"L", 4, 6, Side.NORTH, Side.WEST},
            {"L", 4, 7, Side.WEST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.EAST},
            {"L", 5, 5, Side.NORTH, Side.EAST},
            {"L", 5, 7, Side.NORTH, Side.WEST},
            {"L", 6, 3, Side.EAST, Side.SOUTH},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.WEST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.EAST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.WEST},
            {"B", 3, 3, Side.EAST},
            {"B", 3, 7, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 8, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 13: Introducing complex interdependence
    private static final Object[][] LEVEL_13_INTERMEDIATE = {
            {"L", 2, 3, Side.EAST, Side.SOUTH},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.WEST, Side.SOUTH},
            {"L", 3, 3, Side.NORTH, Side.SOUTH},
            {"L", 3, 7, Side.NORTH, Side.SOUTH},
            {"L", 4, 3, Side.NORTH, Side.EAST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.NORTH, Side.WEST},
            {"L", 5, 4, Side.NORTH, Side.SOUTH},
            {"L", 5, 5, Side.NORTH, Side.SOUTH},
            {"L", 5, 6, Side.NORTH, Side.SOUTH},
            {"L", 6, 4, Side.NORTH, Side.EAST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.NORTH, Side.WEST},
            {"L", 7, 3, Side.EAST, Side.SOUTH},
            {"L", 7, 4, Side.NORTH, Side.WEST},
            {"L", 7, 6, Side.NORTH, Side.EAST},
            {"L", 7, 7, Side.WEST, Side.SOUTH},
            {"L", 8, 3, Side.NORTH, Side.EAST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.NORTH, Side.WEST},
            {"B", 3, 2, Side.EAST},
            {"B", 3, 8, Side.WEST},
            {"B", 9, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 3, Side.EAST, Side.SOUTH},
            {"P", 5, 7, Side.SOUTH, Side.WEST},
            {"P", 7, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 14: Complex grid with multiple power sources
    private static final Object[][] LEVEL_14_INTERMEDIATE = {
            {"L", 2, 3, Side.EAST, Side.SOUTH},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.WEST, Side.SOUTH},
            {"L", 3, 3, Side.NORTH, Side.SOUTH},
            {"L", 3, 7, Side.NORTH, Side.SOUTH},
            {"L", 4, 3, Side.NORTH, Side.EAST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.EAST},
            {"L", 6, 3, Side.EAST, Side.WEST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.EAST, Side.WEST},
            {"L", 6, 8, Side.NORTH, Side.WEST},
            {"L", 7, 4, Side.NORTH, Side.SOUTH},
            {"L", 7, 5, Side.NORTH, Side.SOUTH},
            {"L", 7, 6, Side.NORTH, Side.SOUTH},
            {"L", 8, 4, Side.NORTH, Side.EAST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 2, Side.EAST},
            {"B", 3, 8, Side.WEST},
            {"B", 9, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 7, 3, Side.EAST, Side.SOUTH},
            {"P", 7, 7, Side.SOUTH, Side.WEST}
    };

    // Level 15: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_15_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 4, Side.NORTH, Side.SOUTH},
            {"L", 7, 5, Side.NORTH, Side.SOUTH},
            {"L", 7, 6, Side.NORTH, Side.SOUTH},
            {"L", 8, 4, Side.NORTH, Side.EAST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 9, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 7, 3, Side.EAST, Side.SOUTH},
            {"P", 7, 7, Side.SOUTH, Side.WEST}
    };

    // Level 16: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_16_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 2, Side.EAST, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.EAST},
            {"L", 6, 8, Side.WEST, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 4, Side.NORTH, Side.SOUTH},
            {"L", 8, 5, Side.NORTH, Side.SOUTH},
            {"L", 8, 6, Side.NORTH, Side.SOUTH},
            {"L", 9, 4, Side.NORTH, Side.EAST},
            {"L", 9, 5, Side.EAST, Side.WEST},
            {"L", 9, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 10, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 7, 3, Side.EAST, Side.SOUTH},
            {"P", 7, 7, Side.SOUTH, Side.WEST}
    };

    // Level 17: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_17_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 5, Side.NORTH, Side.SOUTH},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 10, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 4, Side.EAST, Side.SOUTH},
            {"P", 9, 6, Side.SOUTH, Side.WEST}
    };

    // Level 18: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_18_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 4, Side.NORTH, Side.SOUTH},
            {"L", 8, 5, Side.NORTH, Side.SOUTH},
            {"L", 8, 6, Side.NORTH, Side.SOUTH},
            {"L", 9, 4, Side.NORTH, Side.EAST},
            {"L", 9, 5, Side.EAST, Side.WEST},
            {"L", 9, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 10, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 3, Side.EAST, Side.SOUTH},
            {"P", 8, 7, Side.SOUTH, Side.WEST}
    };

    // Level 19: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_19_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 4, Side.NORTH, Side.SOUTH},
            {"L", 9, 5, Side.NORTH, Side.SOUTH},
            {"L", 9, 6, Side.NORTH, Side.SOUTH},
            {"L", 10, 4, Side.NORTH, Side.EAST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 11, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 3, Side.EAST, Side.SOUTH},
            {"P", 9, 7, Side.SOUTH, Side.WEST}
    };

    // Level 20: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_20_INTERMEDIATE = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 3, Side.NORTH, Side.SOUTH},
            {"L", 8, 7, Side.NORTH, Side.SOUTH},
            {"L", 9, 3, Side.NORTH, Side.EAST},
            {"L", 9, 4, Side.EAST, Side.WEST},
            {"L", 9, 5, Side.EAST, Side.WEST},
            {"L", 9, 6, Side.EAST, Side.WEST},
            {"L", 9, 7, Side.NORTH, Side.WEST},
            {"L", 10, 4, Side.NORTH, Side.SOUTH},
            {"L", 10, 5, Side.NORTH, Side.SOUTH},
            {"L", 10, 6, Side.NORTH, Side.SOUTH},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 8, 2, Side.EAST},
            {"B", 8, 8, Side.WEST},
            {"B", 11, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // ==================== ADVANCED LEVELS (21-30) ====================

    // Level 21: Introduction to advanced difficulty
    private static final Object[][] LEVEL_21_ADVANCED = {
            {"L", 2, 3, Side.EAST, Side.SOUTH},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.WEST, Side.SOUTH},
            {"L", 3, 3, Side.NORTH, Side.SOUTH},
            {"L", 3, 7, Side.NORTH, Side.SOUTH},
            {"L", 4, 3, Side.NORTH, Side.EAST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.NORTH, Side.WEST},
            {"L", 5, 4, Side.NORTH, Side.SOUTH},
            {"L", 5, 6, Side.NORTH, Side.SOUTH},
            {"L", 6, 4, Side.NORTH, Side.EAST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.NORTH, Side.WEST},
            {"L", 7, 3, Side.EAST, Side.SOUTH},
            {"L", 7, 4, Side.NORTH, Side.WEST},
            {"L", 7, 6, Side.NORTH, Side.EAST},
            {"L", 7, 7, Side.WEST, Side.SOUTH},
            {"L", 8, 3, Side.NORTH, Side.EAST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 2, Side.EAST},
            {"B", 3, 8, Side.WEST},
            {"B", 5, 3, Side.EAST},
            {"B", 5, 7, Side.WEST},
            {"B", 9, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 7, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 22: Complex grid with multiple paths
    private static final Object[][] LEVEL_22_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 4, Side.NORTH, Side.SOUTH},
            {"L", 9, 5, Side.NORTH, Side.SOUTH},
            {"L", 9, 6, Side.NORTH, Side.SOUTH},
            {"L", 10, 4, Side.NORTH, Side.EAST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 11, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 3, Side.EAST, Side.SOUTH},
            {"P", 9, 7, Side.SOUTH, Side.WEST}
    };

    // Level 23: Complex grid with multiple power sources
    private static final Object[][] LEVEL_23_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 3, Side.NORTH, Side.SOUTH},
            {"L", 8, 7, Side.NORTH, Side.SOUTH},
            {"L", 9, 3, Side.NORTH, Side.EAST},
            {"L", 9, 4, Side.EAST, Side.WEST},
            {"L", 9, 5, Side.EAST, Side.WEST},
            {"L", 9, 6, Side.EAST, Side.WEST},
            {"L", 9, 7, Side.NORTH, Side.WEST},
            {"L", 10, 4, Side.NORTH, Side.SOUTH},
            {"L", 10, 5, Side.NORTH, Side.SOUTH},
            {"L", 10, 6, Side.NORTH, Side.SOUTH},
            {"L", 11, 4, Side.NORTH, Side.EAST},
            {"L", 11, 5, Side.EAST, Side.WEST},
            {"L", 11, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 8, 2, Side.EAST},
            {"B", 8, 8, Side.WEST},
            {"B", 12, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 24: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_24_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 3, Side.NORTH, Side.SOUTH},
            {"L", 9, 7, Side.NORTH, Side.SOUTH},
            {"L", 10, 3, Side.NORTH, Side.EAST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.NORTH, Side.WEST},
            {"L", 11, 4, Side.NORTH, Side.SOUTH},
            {"L", 11, 5, Side.NORTH, Side.SOUTH},
            {"L", 11, 6, Side.NORTH, Side.SOUTH},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 9, 2, Side.EAST},
            {"B", 9, 8, Side.WEST},
            {"B", 12, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 25: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_25_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 2, Side.EAST, Side.SOUTH},
            {"L", 8, 3, Side.NORTH, Side.WEST},
            {"L", 8, 7, Side.NORTH, Side.EAST},
            {"L", 8, 8, Side.WEST, Side.SOUTH},
            {"L", 9, 2, Side.NORTH, Side.SOUTH},
            {"L", 9, 8, Side.NORTH, Side.SOUTH},
            {"L", 10, 2, Side.NORTH, Side.EAST},
            {"L", 10, 3, Side.EAST, Side.WEST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.EAST, Side.WEST},
            {"L", 10, 8, Side.NORTH, Side.WEST},
            {"L", 11, 4, Side.NORTH, Side.SOUTH},
            {"L", 11, 5, Side.NORTH, Side.SOUTH},
            {"L", 11, 6, Side.NORTH, Side.SOUTH},
            {"L", 12, 4, Side.NORTH, Side.EAST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 9, 1, Side.EAST},
            {"B", 9, 9, Side.WEST},
            {"B", 13, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 11, 3, Side.EAST, Side.SOUTH},
            {"P", 11, 7, Side.SOUTH, Side.WEST}
    };

    // Level 26: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_26_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 3, Side.NORTH, Side.SOUTH},
            {"L", 9, 7, Side.NORTH, Side.SOUTH},
            {"L", 10, 3, Side.NORTH, Side.EAST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.NORTH, Side.WEST},
            {"L", 11, 2, Side.EAST, Side.SOUTH},
            {"L", 11, 3, Side.NORTH, Side.WEST},
            {"L", 11, 7, Side.NORTH, Side.EAST},
            {"L", 11, 8, Side.WEST, Side.SOUTH},
            {"L", 12, 2, Side.NORTH, Side.EAST},
            {"L", 12, 3, Side.EAST, Side.WEST},
            {"L", 12, 4, Side.EAST, Side.WEST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.EAST, Side.WEST},
            {"L", 12, 7, Side.EAST, Side.WEST},
            {"L", 12, 8, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 9, 2, Side.EAST},
            {"B", 9, 8, Side.WEST},
            {"B", 11, 1, Side.EAST},
            {"B", 11, 9, Side.WEST},
            {"B", 13, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 27: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_27_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 2, Side.EAST, Side.SOUTH},
            {"L", 8, 3, Side.NORTH, Side.WEST},
            {"L", 8, 7, Side.NORTH, Side.EAST},
            {"L", 8, 8, Side.WEST, Side.SOUTH},
            {"L", 9, 2, Side.NORTH, Side.SOUTH},
            {"L", 9, 8, Side.NORTH, Side.SOUTH},
            {"L", 10, 2, Side.NORTH, Side.EAST},
            {"L", 10, 3, Side.EAST, Side.WEST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.EAST, Side.WEST},
            {"L", 10, 8, Side.NORTH, Side.WEST},
            {"L", 11, 4, Side.NORTH, Side.SOUTH},
            {"L", 11, 5, Side.NORTH, Side.SOUTH},
            {"L", 11, 6, Side.NORTH, Side.SOUTH},
            {"L", 12, 4, Side.NORTH, Side.EAST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 9, 1, Side.EAST},
            {"B", 9, 9, Side.WEST},
            {"B", 13, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 11, 3, Side.EAST, Side.SOUTH},
            {"P", 11, 7, Side.SOUTH, Side.WEST}
    };

    // Level 28: Complex grid with multiple paths and bulbs
    private static final Object[][] LEVEL_28_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 3, Side.NORTH, Side.SOUTH},
            {"L", 9, 7, Side.NORTH, Side.SOUTH},
            {"L", 10, 3, Side.NORTH, Side.EAST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.NORTH, Side.WEST},
            {"L", 11, 2, Side.EAST, Side.SOUTH},
            {"L", 11, 3, Side.NORTH, Side.WEST},
            {"L", 11, 7, Side.NORTH, Side.EAST},
            {"L", 11, 8, Side.WEST, Side.SOUTH},
            {"L", 12, 2, Side.NORTH, Side.EAST},
            {"L", 12, 3, Side.EAST, Side.WEST},
            {"L", 12, 4, Side.EAST, Side.WEST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.EAST, Side.WEST},
            {"L", 12, 7, Side.EAST, Side.WEST},
            {"L", 12, 8, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 9, 2, Side.EAST},
            {"B", 9, 8, Side.WEST},
            {"B", 11, 1, Side.EAST},
            {"B", 11, 9, Side.WEST},
            {"B", 13, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST}
    };

    // Level 29: Complex grid with multiple paths and power sources
    private static final Object[][] LEVEL_29_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 2, Side.EAST, Side.SOUTH},
            {"L", 5, 3, Side.NORTH, Side.WEST},
            {"L", 5, 7, Side.NORTH, Side.EAST},
            {"L", 5, 8, Side.WEST, Side.SOUTH},
            {"L", 6, 2, Side.NORTH, Side.SOUTH},
            {"L", 6, 8, Side.NORTH, Side.SOUTH},
            {"L", 7, 2, Side.NORTH, Side.EAST},
            {"L", 7, 3, Side.EAST, Side.WEST},
            {"L", 7, 4, Side.EAST, Side.WEST},
            {"L", 7, 5, Side.EAST, Side.WEST},
            {"L", 7, 6, Side.EAST, Side.WEST},
            {"L", 7, 7, Side.EAST, Side.WEST},
            {"L", 7, 8, Side.NORTH, Side.WEST},
            {"L", 8, 2, Side.EAST, Side.SOUTH},
            {"L", 8, 3, Side.NORTH, Side.WEST},
            {"L", 8, 7, Side.NORTH, Side.EAST},
            {"L", 8, 8, Side.WEST, Side.SOUTH},
            {"L", 9, 2, Side.NORTH, Side.SOUTH},
            {"L", 9, 8, Side.NORTH, Side.SOUTH},
            {"L", 10, 2, Side.NORTH, Side.EAST},
            {"L", 10, 3, Side.EAST, Side.WEST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.EAST, Side.WEST},
            {"L", 10, 8, Side.NORTH, Side.WEST},
            {"L", 11, 4, Side.NORTH, Side.SOUTH},
            {"L",  11, 5, Side.NORTH, Side.SOUTH},
            {"L", 11, 6, Side.NORTH, Side.SOUTH},
            {"L", 12, 4, Side.NORTH, Side.EAST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 6, 1, Side.EAST},
            {"B", 6, 9, Side.WEST},
            {"B", 9, 1, Side.EAST},
            {"B", 9, 9, Side.WEST},
            {"B", 13, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 8, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 11, 3, Side.EAST, Side.SOUTH},
            {"P", 11, 7, Side.SOUTH, Side.WEST}
    };

    // Level 30: Most complex level with multiple paths, power sources, and bulbs
    private static final Object[][] LEVEL_30_ADVANCED = {
            {"L", 2, 2, Side.EAST, Side.SOUTH},
            {"L", 2, 3, Side.EAST, Side.WEST},
            {"L", 2, 4, Side.EAST, Side.WEST},
            {"L", 2, 5, Side.EAST, Side.WEST},
            {"L", 2, 6, Side.EAST, Side.WEST},
            {"L", 2, 7, Side.EAST, Side.WEST},
            {"L", 2, 8, Side.WEST, Side.SOUTH},
            {"L", 3, 2, Side.NORTH, Side.SOUTH},
            {"L", 3, 8, Side.NORTH, Side.SOUTH},
            {"L", 4, 2, Side.NORTH, Side.EAST},
            {"L", 4, 3, Side.EAST, Side.WEST},
            {"L", 4, 4, Side.EAST, Side.WEST},
            {"L", 4, 5, Side.EAST, Side.WEST},
            {"L", 4, 6, Side.EAST, Side.WEST},
            {"L", 4, 7, Side.EAST, Side.WEST},
            {"L", 4, 8, Side.NORTH, Side.WEST},
            {"L", 5, 3, Side.NORTH, Side.SOUTH},
            {"L", 5, 7, Side.NORTH, Side.SOUTH},
            {"L", 6, 3, Side.NORTH, Side.EAST},
            {"L", 6, 4, Side.EAST, Side.WEST},
            {"L", 6, 5, Side.EAST, Side.WEST},
            {"L", 6, 6, Side.EAST, Side.WEST},
            {"L", 6, 7, Side.NORTH, Side.WEST},
            {"L", 7, 2, Side.EAST, Side.SOUTH},
            {"L", 7, 3, Side.NORTH, Side.WEST},
            {"L", 7, 7, Side.NORTH, Side.EAST},
            {"L", 7, 8, Side.WEST, Side.SOUTH},
            {"L", 8, 2, Side.NORTH, Side.EAST},
            {"L", 8, 3, Side.EAST, Side.WEST},
            {"L", 8, 4, Side.EAST, Side.WEST},
            {"L", 8, 5, Side.EAST, Side.WEST},
            {"L", 8, 6, Side.EAST, Side.WEST},
            {"L", 8, 7, Side.EAST, Side.WEST},
            {"L", 8, 8, Side.NORTH, Side.WEST},
            {"L", 9, 3, Side.NORTH, Side.SOUTH},
            {"L", 9, 7, Side.NORTH, Side.SOUTH},
            {"L", 10, 3, Side.NORTH, Side.EAST},
            {"L", 10, 4, Side.EAST, Side.WEST},
            {"L", 10, 5, Side.EAST, Side.WEST},
            {"L", 10, 6, Side.EAST, Side.WEST},
            {"L", 10, 7, Side.NORTH, Side.WEST},
            {"L", 11, 2, Side.EAST, Side.SOUTH},
            {"L", 11, 3, Side.NORTH, Side.WEST},
            {"L", 11, 7, Side.NORTH, Side.EAST},
            {"L", 11, 8, Side.WEST, Side.SOUTH},
            {"L", 12, 2, Side.NORTH, Side.EAST},
            {"L", 12, 3, Side.EAST, Side.WEST},
            {"L", 12, 4, Side.EAST, Side.WEST},
            {"L", 12, 5, Side.EAST, Side.WEST},
            {"L", 12, 6, Side.EAST, Side.WEST},
            {"L", 12, 7, Side.EAST, Side.WEST},
            {"L", 12, 8, Side.NORTH, Side.WEST},
            {"L", 13, 4, Side.NORTH, Side.SOUTH},
            {"L", 13, 5, Side.NORTH, Side.SOUTH},
            {"L", 13, 6, Side.NORTH, Side.SOUTH},
            {"L", 14, 4, Side.NORTH, Side.EAST},
            {"L", 14, 5, Side.EAST, Side.WEST},
            {"L", 14, 6, Side.NORTH, Side.WEST},
            {"B", 1, 5, Side.SOUTH},
            {"B", 3, 1, Side.EAST},
            {"B", 3, 9, Side.WEST},
            {"B", 5, 2, Side.EAST},
            {"B", 5, 8, Side.WEST},
            {"B", 7, 1, Side.EAST},
            {"B", 7, 9, Side.WEST},
            {"B", 9, 2, Side.EAST},
            {"B", 9, 8, Side.WEST},
            {"B", 11, 1, Side.EAST},
            {"B", 11, 9, Side.WEST},
            {"B", 15, 5, Side.NORTH},
            {"P", 3, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 5, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 4, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 5, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 9, 6, Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST},
            {"P", 13, 3, Side.EAST, Side.SOUTH},
            {"P", 13, 7, Side.SOUTH, Side.WEST}
    };

    // Add more level definitions for levels 22-30

    /**
     * Creates a SwingNode containing the game for the specified level and difficulty.
     *
     * @param levelNumber The level number (1-10 for each difficulty)
     * @param difficulty The difficulty level (0=Beginner, 1=Intermediate, 2=Advanced)
     * @return A SwingNode containing the game
     */
    public static SwingNode createGameLevel(int levelNumber, int difficulty, Runnable levelCompletedCallback) {
        //System.out.println("Создание уровня " + levelNumber + " с сложностью " + difficulty);

        SwingNode swingNode = new SwingNode();

        // Get the appropriate level definition
        Object[][] levelDef = getLevelDefinition(levelNumber, difficulty);

        SwingUtilities.invokeLater(() -> {
            try {
                // Create game with appropriate grid size based on difficulty
                int gridSize = getGridSizeForDifficulty(difficulty);
                Game game = Game.create(gridSize, gridSize + 2);

                // Add nodes from level definition
                for (Object[] n : levelDef) {
                    String type = (String) n[0];
                    int row = (Integer) n[1];
                    int col = (Integer) n[2];
                    Position p = new Position(row, col);
                    Side[] sides = new Side[n.length - 3];
                    for (int i = 3; i < n.length; i++) {
                        sides[i - 3] = (Side) n[i];
                    }
                    switch (type) {
                        case "L" -> game.createLinkNode(p, sides);
                        case "B" -> game.createBulbNode(p, sides[0]);
                        case "P" -> game.createPowerNode(p, sides);
                    }
                }

                game.init();

                Function<Game, EnvPresenter> makePresenter = gm -> {
                    EnvPresenter pr = new EnvPresenter(gm);
                    if (levelCompletedCallback != null) {
                        pr.setLevelCompletedCallback(levelCompletedCallback);
                    }
                    pr.init();
                    return pr;
                };


                EnvPresenter solvedPr = new EnvPresenter(game);
                solvedPr.init();
                //JPanel solvedPanel = solvedPr.getGamePanel();
                Platform.runLater(() -> {
                    swingNode.setContent(solvedPr.getGamePanel());
                });
                Game solvedGame = game.deepCopy();
                SwingNode infoSwingNode = new SwingNode();
                SwingUtilities.invokeLater(() -> {
                    InfoPresenter info = new InfoPresenter(game, solvedGame);
                    infoSwingNode.setContent(info.getPanel());
                });
                PauseTransition pause = new PauseTransition(Duration.seconds(2));

                pause.setOnFinished(evt -> {

                    List<Position> positions = game.getNodes().stream()
                            .map(GameNode::getPosition)
                            .toList();
                    Random rnd = new Random();
                    int moves = 12 + difficulty * 5;
                    do {
                        for (int i = 0; i < moves; i++) {
                            game.rotateNode(positions.get(rnd.nextInt(positions.size())));
                        }
                    } while (game.anyBulbLit());



                    /*JFrame infoFrame = new JFrame("Info o otočkách");
                    infoFrame.getContentPane().add(infoPr.getPanel());
                    infoFrame.pack();
                    infoFrame.setLocationRelativeTo(null);
                    infoFrame.setVisible(true);*/

                    EnvPresenter scramblePr = new EnvPresenter(game);
                    scramblePr.setLevelCompletedCallback(levelCompletedCallback);
                    scramblePr.init();
                    Platform.runLater(() -> swingNode.setContent(scramblePr.getGamePanel()));
                    InfoPresenter infoPr = new InfoPresenter(game,solvedGame);

                    for (GameNode node : game.getNodes()) {
                        node.addObserver(infoPr);
                    }
                });
                Platform.runLater(() -> {
                    Stage infoStage = new Stage();
                    infoStage.setTitle("Info o otočení");
                    // vlož SwingNode přímo do JavaFX layoutu
                    StackPane root = new StackPane(infoSwingNode);
                    root.setStyle("-fx-background-color: #0f1e2e;"); // stejné pozadí jako hra
                    // uprav velikost okna podle panelu
                    Scene scene = new Scene(root,
                            game.cols() * InfoPresenter.TILE_SIZE,
                            game.rows() * InfoPresenter.TILE_SIZE
                    );
                    infoStage.setScene(scene);
                    infoStage.show();
                });
                pause.play();


                /* List<Position> positions = game.getNodes().stream()
                        .map(GameNode::getPosition)
                        .collect(Collectors.toList());


                int baseMoves     = 10;
                int movesPerLevel = 5;
                int scrambleMoves = baseMoves + difficulty * movesPerLevel;

                Random rnd = new Random();
                do {
                    for (int i = 0; i < scrambleMoves; i++) {
                        Position p = positions.get(rnd.nextInt(positions.size()));
                        game.rotateNode(p);
                    }
                } while (game.anyBulbLit());
                */
                /*
                EnvPresenter presenter = new EnvPresenter(game);

                // Set the level completed callback
                if (levelCompletedCallback != null) {
                    presenter.setLevelCompletedCallback(levelCompletedCallback);
                }

                presenter.init();


                // Get the internal panel from presenter
                JPanel contentPanel = presenter.getGamePanel();


                // Store the presenter in the SwingNode's user data for later access
                final EnvPresenter finalPresenter = presenter;
                final JPanel finalContentPanel = contentPanel;

                Platform.runLater(() -> {
                    try {
                        swingNode.setUserData(finalPresenter);
                        System.out.println("EnvPresenter установлен в userData: " + swingNode.getUserData());

                        swingNode.setContent(finalContentPanel);
                        System.out.println("Контент установлен для SwingNode");

                        System.out.println("Уровень " + levelNumber + " успешно инициализирован");
                    } catch (Exception e) {
                        System.err.println("Ошибка при установке контента SwingNode: " + e.getMessage());
                        e.printStackTrace();
                    }
                });*/
            } catch (Exception e) {
                System.err.println("ERROR during initializetion: " + e.getMessage());
                e.printStackTrace();
            }
        });

        return swingNode;
    }

    /**
     * Gets the level definition for the specified level and difficulty.
     *
     * @param levelNumber The level number (1-10 for each difficulty)
     * @param difficulty The difficulty level (0=Beginner, 1=Intermediate, 2=Advanced)
     * @return The level definition array
     */
    private static Object[][] getLevelDefinition(int levelNumber, int difficulty) {
        // Calculate the actual level number (1-30)
        int actualLevel = difficulty * 10 + levelNumber;

        return switch (actualLevel) {
            // Beginner levels (1-10)
            case 1 -> LEVEL_1_BEGINNER;
            case 2 -> LEVEL_2_BEGINNER;
            case 3 -> LEVEL_3_BEGINNER;
            case 4 -> LEVEL_4_BEGINNER;
            case 5 -> LEVEL_5_BEGINNER;
            case 6 -> LEVEL_6_BEGINNER;
            case 7 -> LEVEL_7_BEGINNER;
            case 8 -> LEVEL_8_BEGINNER;
            case 9 -> LEVEL_9_BEGINNER;
            case 10 -> LEVEL_10_BEGINNER;

            // Intermediate levels (11-20)
            case 11 -> LEVEL_11_INTERMEDIATE;
            case 12 -> LEVEL_12_INTERMEDIATE;
            case 13 -> LEVEL_13_INTERMEDIATE;
            case 14 -> LEVEL_14_INTERMEDIATE;
            case 15 -> LEVEL_15_INTERMEDIATE;
            case 16 -> LEVEL_16_INTERMEDIATE;
            case 17 -> LEVEL_17_INTERMEDIATE;
            case 18 -> LEVEL_18_INTERMEDIATE;
            case 19 -> LEVEL_19_INTERMEDIATE;
            case 20 -> LEVEL_20_INTERMEDIATE;

            // Advanced levels (21-30)
            case 21 -> LEVEL_21_ADVANCED;
            case 22 -> LEVEL_22_ADVANCED;
            case 23 -> LEVEL_23_ADVANCED;
            case 24 -> LEVEL_24_ADVANCED;
            case 25 -> LEVEL_25_ADVANCED;
            case 26 -> LEVEL_26_ADVANCED;
            case 27 -> LEVEL_27_ADVANCED;
            case 28 -> LEVEL_28_ADVANCED;
            case 29 -> LEVEL_29_ADVANCED;
            case 30 -> LEVEL_30_ADVANCED;

            default -> LEVEL_1_BEGINNER; // Default to level 1 if not found
        };
    }

    /**
     * Gets the grid size for the specified difficulty level.
     *
     * @param difficulty The difficulty level (0=Beginner, 1=Intermediate, 2=Advanced)
     * @return The grid size for the difficulty
     */
    private static int getGridSizeForDifficulty(int difficulty) {
        return switch (difficulty) {
            case 0 -> 10; // Beginner
            case 1 -> 12; // Intermediate
            case 2 -> 14; // Advanced
            default -> 10;
        };
    }
}