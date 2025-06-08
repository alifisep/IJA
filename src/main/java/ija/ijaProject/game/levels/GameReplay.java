package ija.ijaProject.game.levels;

import ija.ijaProject.common.GameNode;
import ija.ijaProject.game.Game;

import java.util.ArrayList;
import java.util.List;

public class GameReplay {
    private final List<GameMove> moves;
    private int currentMoveIndex = -1;
    private boolean isPlayMode = false;
    private final Runnable refreshCallback;
    private final Game game;

    public GameReplay(List<GameMove> moves, Game game, Runnable refreshCallback) {
        this.moves = moves;
        this.game = game;
        this.refreshCallback = refreshCallback;
        System.out.println("[Replay] Created with " + moves.size() + " moves.");
    }

    public void stepForward() {
        System.out.println("[Replay] stepForward called. isPlayMode=" + isPlayMode + ", currentMoveIndex=" + currentMoveIndex);
        if (isPlayMode || currentMoveIndex >= moves.size() - 1) {
            System.out.println("[Replay] Skipping stepForward: either in play mode or at end.");
            return;
        }
        currentMoveIndex++;
        System.out.println("[Replay] Applying move at index: " + currentMoveIndex);
        applyMove(moves.get(currentMoveIndex));
        refreshCallback.run();
    }

    public void stepBackward() {
        System.out.println("[Replay] stepBackward called. isPlayMode=" + isPlayMode + ", currentMoveIndex=" + currentMoveIndex);
        if (isPlayMode || currentMoveIndex < 0) {
            System.out.println("[Replay] Skipping stepBackward: either in play mode or at beginning.");
            return;
        }
        System.out.println("[Replay] Reverting move at index: " + currentMoveIndex);
        revertMove(moves.get(currentMoveIndex));
        currentMoveIndex--;
        refreshCallback.run();
    }

    public void switchToPlayMode() {
        System.out.println("[Replay] Switching to play mode.");
        System.out.println("!!!! NODE: " + NodeStateManager.getInstance().isReplayMode());
        NodeStateManager.getInstance().setReplayMode(false);
        System.out.println("!!!! NODE: " + NodeStateManager.getInstance().isReplayMode());
        this.isPlayMode = true;

        List<GameMove> trimmed = new ArrayList<>(moves.subList(0, currentMoveIndex + 1));
        System.out.println("[Replay] Trimmed moves to size: " + trimmed.size());

        NodeStateManager.getInstance().startFromPartialLog(trimmed);

    }

    private void applyMove(GameMove move) {
        System.out.println("[Replay] Applying move: (" + move.x + ", " + move.y + ")");
        GameNode node = game.getGameNode(move.x, move.y);
        if (node != null) {
            for (int i = 0; i < move.rotation; i++) {
                node.turn();
            }
        } else {
            System.out.println("[Replay] Warning: Node is null in applyMove");
        }
    }

    private void revertMove(GameMove move) {
        System.out.println("[Replay] Reverting move: (" + move.x + ", " + move.y + ")");
        GameNode node = game.getGameNode(move.x, move.y);
        if (node != null) {
            for (int i = 0; i < 3; i++) node.turn();
        } else {
            System.out.println("[Replay] Warning: Node is null in revertMove");
        }
    }
}