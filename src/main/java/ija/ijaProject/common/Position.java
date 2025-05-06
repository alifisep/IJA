package ija.ijaProject.common;

import java.util.Objects;

public class Position {
    private final int row;
    private final int col;

    // Position of object
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Get position
    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
