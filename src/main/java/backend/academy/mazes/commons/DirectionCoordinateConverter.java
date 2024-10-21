package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.types.Direction;

public class DirectionCoordinateConverter {
    public Coordinate directionToCoordinate(Direction direction, int height, int width) {
        return switch (direction) {
            case NORTH -> new Coordinate(0, width / 2);
            case NORTHEAST -> new Coordinate(0, width - 1);
            case EAST -> new Coordinate(height / 2, width - 1);
            case SOUTHEAST -> new Coordinate(height - 1, width - 1);
            case SOUTH -> new Coordinate(height - 1, width / 2);
            case SOUTHWEST -> new Coordinate(height - 1, 0);
            case WEST -> new Coordinate(height / 2, 0);
            case NORTHWEST -> new Coordinate(0, 0);
        };
    }
}
