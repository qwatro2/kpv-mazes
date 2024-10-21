package backend.academy.mazes.entities;

import lombok.Getter;
import lombok.Setter;

@Getter public class Cell {
    private final int row;
    private final int col;
    @Setter private CellType cellType;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellType = CellType.EMPTY;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(this.row, this.col);
    }
}
