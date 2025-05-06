package ija.ijaProject.common;

import ija.ijaProject.game.Game;
import visualization.common.AbstractObservableField;
import visualization.common.ToolField;
import java.util.HashSet;
import java.util.Set;

public class GameNode extends AbstractObservableField implements ToolField {
    private Game game;
    private final Position position;
    private boolean isBulb;
    private boolean isPower;
    private boolean isLink;
    private boolean isConnectedToPower = false;
    private final Set<Side> connectors = new HashSet<>();

    // Creating game grid
    public void setGame(Game game) {
        this.game = game;
    }

    public GameNode(Position position) {
        this.position = position;
    }

    // Set node type
    public void setBulb(Side side) {
        isBulb = true;
        connectors.add(side);
        notifyObservers();
    }
    public Set<Side> getConnectors() {
        return new HashSet<>(connectors);
    }

    public void setPower(Side... sides) {
        isPower = true;
        for (Side s : sides) {
            connectors.add(s);
        }
        notifyObservers();
    }

    public void setLink(Side... sides) {
        isLink = true;
        for (Side s : sides) {
            connectors.add(s);
        }
        notifyObservers();
    }

    @Override
    public void turn() {
        Set<Side> newConnectors = new HashSet<>();
        for (Side s : connectors) {
            newConnectors.add(rotateClockwise(s));
        }
        connectors.clear();
        connectors.addAll(newConnectors);
        notifyObservers();

        if (game != null) {
            game.init();
        }
    }

    private Side rotateClockwise(Side side) {
        return switch (side) {
            case NORTH -> Side.EAST;
            case EAST -> Side.SOUTH;
            case SOUTH -> Side.WEST;
            case WEST -> Side.NORTH;
        };
    }

    @Override
    public boolean north() {
        return connectors.contains(Side.NORTH);
    }

    @Override
    public boolean east() {
        return connectors.contains(Side.EAST);
    }

    @Override
    public boolean south() {
        return connectors.contains(Side.SOUTH);
    }

    @Override
    public boolean west() {
        return connectors.contains(Side.WEST);
    }

    @Override
    public boolean light() {
        return isConnectedToPower;
    }

    public void setConnectedToPower(boolean connected) {
        if (this.isConnectedToPower != connected) {
            this.isConnectedToPower = connected;
            notifyObservers();
        }
    }


    // State and position of node
    @Override
    public boolean isLink() {
        return isLink;
    }

    @Override
    public boolean isBulb() {
        return isBulb;
    }

    @Override
    public boolean isPower() {
        return isPower;
    }

    public Position getPosition() {
        return position;
    }

    public boolean containsConnector(Side side) {
        return connectors.contains(side);
    }
    @Override
    public String toString() {
        char type = 'E';
        if (isBulb) type = 'B';
        else if (isPower) type = 'P';
        else if (isLink) type = 'L';

        StringBuilder sb = new StringBuilder()
                .append('{')
                .append(type)
                .append('[')
                .append(position.row())
                .append('@')
                .append(position.col())
                .append("][");

        if (north()) sb.append("NORTH,");
        if (east()) sb.append("EAST,");
        if (south()) sb.append("SOUTH,");
        if (west()) sb.append("WEST,");

        if (sb.charAt(sb.length()-1) == '[') {
            sb.append(']');
        } else {
            sb.setCharAt(sb.length()-1, ']');
        }

        return sb.append('}').toString();
    }

    public void clearConnectors() {
        connectors.clear();
    }
    public void addConnector(Side side) {
        connectors.add(side);
    }
    public Side[] getSides() {
        return connectors.toArray(new Side[0]);
    }
}