package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;

public class CoordinateIndexConverter {
    public int coordinateToIndex(Coordinate coordinate, int width) {
        return coordinate.row() * width + coordinate.col();
    }

    public Coordinate indexToCoordinate(int index, int width) {
        return new Coordinate(index / width, index % width);
    }
}
