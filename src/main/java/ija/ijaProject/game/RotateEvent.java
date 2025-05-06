package ija.ijaProject.game;

import ija.ijaProject.common.Position;

public class RotateEvent implements GameEvent {
    private final Position pos;
    public RotateEvent(Position p) { this.pos = p; }
    @Override public void apply(Game g) { g.rotateNode(pos); }
}
