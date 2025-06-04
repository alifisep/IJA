package ija.ijaProject.game.levels;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameMove implements Serializable {
    private static final long serialVersionUID = 1L;
    public final int x, y;
    public final int rotation;
    public final long timestamp;
    private final List<GameMove> currentGameMoves = new ArrayList<>();
    private String currentGameLogFile = null;

    public GameMove(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.timestamp = System.currentTimeMillis();
    }
}
