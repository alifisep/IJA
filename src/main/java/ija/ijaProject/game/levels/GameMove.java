package ija.ijaProject.game.levels;

import ija.ijaProject.common.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameMove implements Serializable {
    public final int x, y;
    public final int rotation;
    public final long timestamp;
    private final List<GameMove> currentGameMoves = new ArrayList<>();

    public GameMove(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.timestamp = System.currentTimeMillis();
    }
    public Position getPosition() {
        return new Position(x, y);
    }
}
