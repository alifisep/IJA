package visualization.view;

import ija.ijaProject.common.GameNode;
import ija.ijaProject.common.Position;
import ija.ijaProject.common.Side;
import ija.ijaProject.game.Game;
import visualization.common.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InfoPresenter implements Observable.Observer {
    public static final int TILE_SIZE = 48;          // stejná velikost dlaždice jako ve hře
    private static final Color BG        = Color.decode("#0f1e2e");  // pozadí okna
    private static final Color CELL_BG   = Color.decode("#1f2f3f");  // pozadí buňky
    private static final Color LINE      = Color.decode("#2f3f4f");  // barva mřížky
    private static final Color BG_CELL     = new Color(0x1A1F34);
    private static final Color GRID_COLOR  = new Color(0x3A4154);
    private static final Font  FONT_NUMBER = new Font("Arial", Font.BOLD, 18);
    private final Game currentGame;
    private final Game solvedGame;
    private final JPanel panel;
    private final Map<Position, JLabel> labels = new HashMap<>();
    private final Map<Position,JButton> buttons = new HashMap<>();

    public InfoPresenter(Game currentGame, Game solvedGame) {
        this.currentGame = currentGame;
        this.solvedGame = solvedGame;

        int rows = currentGame.rows();
        int cols = currentGame.cols();
        this.panel = new JPanel(new GridLayout(rows, cols, 0, 0));
        panel.setBackground(BG_CELL);

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                Position pos = new Position(r, c);
                GameNode node = currentGame.getGameNode(r, c);
                JButton btn = new JButton();
                btn.setEnabled(false);

                btn.setBorder(BorderFactory.createLineBorder(GRID_COLOR));
                btn.setBackground(BG_CELL);
                btn.setFont(FONT_NUMBER);
                btn.setForeground(Color.WHITE);
                btn.setMargin(new Insets(0,0,0,0));
                btn.setOpaque(true);

                if (currentGame.isPlayebleNode(node)) {
                    node.addObserver(this);
                    buttons.put(pos, btn);
                } else {
                    btn.setText("?");
                    btn.setBackground(Color.DARK_GRAY);
                }
                panel.add(btn);
            }
        }

        updateAll();
    }

    @Override
    public void update(Observable source) {
        SwingUtilities.invokeLater(this::updateAll);
    }

    private void updateAll() {
        for (Map.Entry<Position, JButton> e : buttons.entrySet()) {
            Position pos = e.getKey();
            JButton btn    = e.getValue();


            GameNode cur = currentGame.getGameNode(pos.row(), pos.col());
            GameNode sol = solvedGame .getGameNode(pos.row(), pos.col());

            int need = rotationsNeeded(
                    cur.getConnectors(),
                    sol.getConnectors()
            );
            // btn.setText(String.valueOf(need));
            btn.setText(need >= 0 ? String.valueOf(need) : "?");
        }
    }

    private int rotationsNeeded(Set<Side> cur, Set<Side> tgt) {
        for (int k = 0; k < 4; k++) {
            final int kk = k;
            Set<Side> rot = cur.stream()
                    .map(s -> rotateCW(s, kk))
                    .collect(Collectors.toSet());
            if (rot.equals(tgt)) return kk;
        }
        return 0;
    }

    private Side rotateCW(Side s, int times) {
        Side r = s;
        for (int i = 0; i < times; i++) {
            r = switch(r) {
                case NORTH -> Side.EAST;
                case EAST  -> Side.SOUTH;
                case SOUTH -> Side.WEST;
                case WEST  -> Side.NORTH;
            };
        }
        return r;
    }

    public JPanel getPanel() {
        return panel;
    }
}