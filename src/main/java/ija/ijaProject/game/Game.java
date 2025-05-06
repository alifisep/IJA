package ija.ijaProject.game;

import ija.ijaProject.common.Position;
import ija.ijaProject.common.Side;
import ija.ijaProject.common.GameNode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import visualization.common.ToolEnvironment;
import visualization.common.ToolField;
import visualization.view.FieldView;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Game implements ToolEnvironment {
    private final int rows;
    private final int cols;
    private final ToolField[][] grid;
    private boolean powerExists = false;
    private Runnable completionListener;
    private final Map<Position, GameNode> nodes;

    // Creating game grid
    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.nodes = new HashMap<>();
        this.grid = new ToolField[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                GameNode node = new GameNode(new Position(r + 1, c + 1));
                node.setGame(this);
                grid[r][c] = node;
            }
        }
    }
    public boolean isPlayebleNode(GameNode node) {
        return node.isBulb() || node.isPower() || node.isLink();
    }
    public void setCompletionListener(Runnable listener) {
        this.completionListener = listener;
    }
    public void checkCompletion() {
        if (anyBulbLit()) return;
        if (completionListener != null) {
            completionListener.run();
        }
    }

    public static Game create(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid game size.");
        }

        return new Game(rows, cols);
    }

    // Help methods for game init
    private void lightConnectedNodes(GameNode powerNode) {
        resetPowerStates();

        Queue<GameNode> queue = new LinkedList<>();
        powerNode.setConnectedToPower(true);
        queue.add(powerNode);

        while (!queue.isEmpty()) {
            GameNode current = queue.poll();
            Position pos = current.getPosition();

            checkDirection(current, pos.row()-1, pos.col(), Side.NORTH, Side.SOUTH, queue); // North
            checkDirection(current, pos.row()+1, pos.col(), Side.SOUTH, Side.NORTH, queue); // South
            checkDirection(current, pos.row(), pos.col()-1, Side.WEST, Side.EAST, queue);   // West
            checkDirection(current, pos.row(), pos.col()+1, Side.EAST, Side.WEST, queue);   // East
        }
    }
    public List<GameNode> getNodes() {
        return new ArrayList<>(nodes.values());
    }
    public void rotateNode(Position p) {
        GameNode node = node(p);
        if (node != null) {
            node.turn();
        }
    }

    private void resetPowerStates() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ((GameNode) grid[r][c]).setConnectedToPower(false);
            }
        }
    }

    private void checkDirection(GameNode current, int row, int col,
                                Side outDirection, Side inDirection,
                                Queue<GameNode> queue) {
        if (row < 1 || row > rows || col < 1 || col > cols) return;

        if (!current.containsConnector(outDirection)) return;

        GameNode neighbor = (GameNode) fieldAt(row, col);
        if (neighbor != null && neighbor.containsConnector(inDirection) && !neighbor.light()) {
            neighbor.setConnectedToPower(true);
            queue.add(neighbor);
        }
    }
    public boolean anyBulbLit() {
        return nodes.values().stream()
                .filter(GameNode::isBulb)
                .anyMatch(GameNode::light);
    }

    public GameNode getGameNode(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return null;
        }
        return (GameNode) grid[row - 1][col - 1];
    }

    // Create Bulb
    public GameNode createBulbNode(Position p, Side side) {
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setBulb(side);
        nodes.put(p, node);
        return node;
    }

    // Create Link
    public GameNode createLinkNode(Position p, Side... sides) {
        if (sides.length < 2) return null;
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setLink(sides);
        nodes.put(p, node);
        return node;
    }

    // Create Power
    public GameNode createPowerNode(Position p, Side... sides) {
        if (sides.length < 1 || powerExists) return null;
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setPower(sides);
        powerExists = true;
        nodes.put(p, node);
        return node;
    }

    // Get position
    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public ToolField fieldAt(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return null;
        }
        return grid[row - 1][col - 1];
    }

    public GameNode node(Position p) {
        if (p == null) return null;
        return getGameNode(p.row(), p.col());
    }


    public void init() {
        if (!powerExists) {
            throw new IllegalStateException("No power source found!");
        }

        boolean bulbFound = false;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                GameNode node = (GameNode) grid[r][c];
                if (node.isBulb()) {
                    lightConnectedNodes(node);
                    bulbFound = true;
                    break;
                }
            }
        }
        if (!bulbFound) {
            throw new IllegalStateException("No bulb found!");
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                GameNode node = (GameNode) grid[r][c];
                if (node.isPower()) {
                    lightConnectedNodes(node);
                }
            }
        }
    }


    public Game deepCopy() {
        Game copy = Game.create(this.rows, this.cols);

        for (GameNode original : this.getNodes()) {
            Position pos = original.getPosition();
            Side[] sides = original.getSides();

            if (original.isBulb()) {

                copy.createBulbNode(pos, sides[0]);
            }
            else if (original.isPower()) {

                copy.createPowerNode(pos, sides);
            }
            else if (original.isLink()) {

                copy.createLinkNode(pos, sides);
            }
        }


        copy.init();
        return copy;
    }

    public GameNode getNodeAt(Position p) {
        GameNode node = nodes.get(p);
        if (node == null) {
            throw new IllegalArgumentException("Žádný uzel na pozici " + p);
        }
        return node;
    }
}