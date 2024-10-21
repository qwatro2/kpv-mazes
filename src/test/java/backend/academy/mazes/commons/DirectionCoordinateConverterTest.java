package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.types.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class DirectionCoordinateConverterTest {

    private static Stream<Arguments> provideValuesForDirectionToCoordinate() {
        return Stream.of(
            Arguments.of(Direction.NORTH, 10, 10, new Coordinate(0, 5)),
            Arguments.of(Direction.NORTHEAST, 10, 10, new Coordinate(0, 9)),
            Arguments.of(Direction.EAST, 10, 10, new Coordinate(5, 9)),
            Arguments.of(Direction.SOUTHEAST, 10, 10, new Coordinate(9, 9)),
            Arguments.of(Direction.SOUTH, 10, 10, new Coordinate(9, 5)),
            Arguments.of(Direction.SOUTHWEST, 10, 10, new Coordinate(9, 0)),
            Arguments.of(Direction.WEST, 10, 10, new Coordinate(5, 0)),
            Arguments.of(Direction.NORTHWEST, 10, 10, new Coordinate(0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValuesForDirectionToCoordinate")
    void directionToCoordinate(Direction direction, int height, int width, Coordinate expected) {
        Coordinate actual = new DirectionCoordinateConverter().directionToCoordinate(direction, height, width);
        assertTrue(actual.row() == expected.row() && actual.col() == expected.col());
    }
}
