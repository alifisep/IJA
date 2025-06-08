/**
 * Soubor: src/main/java/ija.ijaProject/game/Game.java
 *
 * Popis:
 * Prostředí hry.
 * Prostředí je tvořeno políčky GameNode
 * umístěnými v mřížce o rozměru [row, col].
 * Políčka jsou indexovaná od [1,1]
 * (levé horní políčko).
 * Implicitně je každé políčko prázdné,
 * lze změnit na některé z povolených typů
 * (vizte třídu GameNode a metody createBulbNode(),
 * createLinkNode() a createPowerNode()).
 * U krajních políček nesmí ve správném zapojení
 * směřovat vodič ven z hrací desky.
 *
 * @Author: Yaroslav Hryn (xhryny00),Oleksandr Musiichuk (xmusii00)
 *
 */


package ija.ijaProject.game;

import ija.ijaProject.common.Position;
import ija.ijaProject.common.Side;
import ija.ijaProject.common.GameNode;
import visualization.common.ToolEnvironment;
import visualization.common.ToolField;

import java.util.*;
import java.util.List;

/** Prostředí hry.
 * Prostředí je tvořeno políčky GameNode
 * umístěnými v mřížce o rozměru [row, col].
 * Políčka jsou indexovaná od [1,1]
 * (levé horní políčko).
 * Implicitně je každé políčko prázdné,
 * lze změnit na některé z povolených typů
 * (vizte třídu GameNode a metody createBulbNode(),
 * createLinkNode() a createPowerNode()).
 * U krajních políček nesmí ve správném zapojení
 * směřovat vodič ven z hrací desky.*/
public class Game implements ToolEnvironment {
    private final int rows;
    private final int cols;
    private final ToolField[][] grid;
    private boolean powerExists = false;
    private Runnable completionListener;
    private final Map<Position, GameNode> nodes;

    /**
     * Konstruktor, vytvoří prázdnou mřížku s danými rozměry.
     * Každé políčko inicializuje jako GameNode bez konektorů.
     *
     * @param rows počet řádků (>0)
     * @param cols počet sloupců (>0)
     */
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

    /**
     * Zjišťuje, zda daný uzel je součástí hry (žárovka, zdroj nebo vodič).
     *
     * @param node uzel ke kontrole
     * @return {@code true}, pokud je bulb, power nebo link
     */
    public boolean isPlayebleNode(GameNode node) {
        return node.isBulb() || node.isPower() || node.isLink();
    }

    /**
     * Tovární metoda, vytvoří instanci třídy Game se zadanými rozměry.
     *
     * @param rows počet řádků (>0)
     * @param cols počet sloupců (>0)
     * @return nová instance Game
     * @throws IllegalArgumentException pokud jsou rozměry ≤ 0
     **/
    public static Game create(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid game size.");
        }

        return new Game(rows, cols);
    }

    /**
     * Provede proud od daného powerNode do všech připojených vodičů
     * a žárovek pomocí šíření do sousedních uzlů.
     *
     * @param powerNode výchozí zdroj energie
     */
    private void lightConnectedNodes(GameNode powerNode) {
        resetPowerStates();

        Queue<GameNode> queue = new LinkedList<>();
        powerNode.setConnectedToPower(true);
        queue.add(powerNode);

        while (!queue.isEmpty()) {
            GameNode current = queue.poll();
            Position pos = current.getPosition();

            checkDirection(current, pos.row() - 1, pos.col(), Side.NORTH, Side.SOUTH, queue); // North
            checkDirection(current, pos.row() + 1, pos.col(), Side.SOUTH, Side.NORTH, queue); // South
            checkDirection(current, pos.row(), pos.col() - 1, Side.WEST, Side.EAST, queue);   // West
            checkDirection(current, pos.row(), pos.col() + 1, Side.EAST, Side.WEST, queue);   // East
        }
    }

    /**
     * Vrátí seznam všech aktivních uzlů (žárovek, zdrojů, vodičů).
     *
     * @return list instancí GameNode
     */
    public List<GameNode> getNodes() {
        return new ArrayList<>(nodes.values());
    }


    /**
     * Otočí uzel na dané pozici o 90° CW.
     *
     * @param p pozice uzlu
     */
    public void rotateNode(Position p) {
        GameNode node = node(p);
        if (node != null) {
            node.turn();
        }
    }

    /**
     * Resetuje příznak připojení ke zdroji u všech políček.
     */
    private void resetPowerStates() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ((GameNode) grid[r][c]).setConnectedToPower(false);
            }
        }
    }

    /**
     * Zkontroluje jeden směr od aktuálního uzlu k sousednímu.
     * Pokud jsou konektory kompatibilní a soused ještě nesvítí, přidá jej do fronty.
     *
     * @param current      aktuální uzel
     * @param row          řádek souseda
     * @param col          sloupec souseda
     * @param outDirection směr z aktuálního uzlu
     * @param inDirection  směr do souseda
     * @param queue        fronta uzlů k dalšímu šíření
     */
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

    /**
     * Zjistí, zda alespoň jedna žárovka stále svítí.
     *
     * @return {@code true} pokud existuje svítící žárovka
     */
    public boolean anyBulbLit() {
        return nodes.values().stream()
                .filter(GameNode::isBulb)
                .anyMatch(GameNode::light);
    }

    /**
     * Vrátí uzel na daných souřadnicích (1-based index).
     *
     * @param row řádek (1..rows)
     * @param col sloupec (1..cols)
     * @return instance GameNode nebo {@code null}, pokud mimo mřížku
     */
    public GameNode getGameNode(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return null;
        }
        return (GameNode) grid[row - 1][col - 1];
    }


    /**
     * Vytvoří políčko typu žárovka a umístí ho do prostředí hry na zadanou pozici.
     */
    public GameNode createBulbNode(Position p, Side side) {
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setBulb(side);
        nodes.put(p, node);
        return node;
    }

    /**
     * Vytvoří políčko typu vodič a umístí ho do prostředí hry na zadanou pozici.
     */
    public GameNode createLinkNode(Position p, Side... sides) {
        if (sides.length < 2) return null;
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setLink(sides);
        nodes.put(p, node);
        return node;
    }


    /**
     * Vytvoří políčko typu zdroj a umístí ho do prostředí hry na zadanou pozici.
     */
    public GameNode createPowerNode(Position p, Side... sides) {
        if (sides.length < 1 || powerExists) return null;
        GameNode node = getGameNode(p.row(), p.col());
        if (node == null) return null;
        node.setPower(sides);
        powerExists = true;
        nodes.put(p, node);
        return node;
    }


    /**
     * Zjišťuje rozměr prostředí - počet řádků.
     */
    @Override
    public int rows() {
        return rows;
    }

    /**
     * Zjišťuje rozměr prostředí - počet sloupců.
     */
    @Override
    public int cols() {
        return cols;
    }

    /**
     * Vrací pole (ToolField) na dané pozici ve 2D poli.
     *
     * @param row řádek (1..rows)
     * @param col sloupec (1..cols)
     * @return instance ToolField nebo {@code null}
     */
    @Override
    public ToolField fieldAt(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols) {
            return null;
        }
        return grid[row - 1][col - 1];
    }

    /**
     * Vrací políčko umístěné na zadané pozici.
     */
    public GameNode node(Position p) {
        if (p == null) return null;
        return getGameNode(p.row(), p.col());
    }

    /**
     * Inicializuje šíření proudu od zdroje ke všem žárovkám.
     * Zavolá {@link #lightConnectedNodes} pro každý zdroj a žárovku.
     *
     * @throws IllegalStateException pokud chybí zdroj nebo žárovka
     */
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

    /**
     * Vytvoří hlubokou kopii této hry (stejná konfigurace uzlů).
     * Kopie je ihned inicializována.
     *
     * @return nová instance Game s identickou mřížkou
     */
    public Game deepCopy() {
        Game copy = Game.create(this.rows, this.cols);

        for (GameNode original : this.getNodes()) {
            Position pos = original.getPosition();
            Side[] sides = original.getSides();

            if (original.isBulb()) {

                copy.createBulbNode(pos, sides[0]);
            } else if (original.isPower()) {

                copy.createPowerNode(pos, sides);
            } else if (original.isLink()) {

                copy.createLinkNode(pos, sides);
            }
        }


        copy.init();
        return copy;
    }
}