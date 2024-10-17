package backend.academy.mazes.entities;

import lombok.Getter;

public record Cell(int row, int col) {
    public Coordinate getCoordinate() {
        return new Coordinate(this.row, this.col);
    }
}
