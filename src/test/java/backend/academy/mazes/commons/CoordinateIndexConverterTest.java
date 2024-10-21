package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateIndexConverterTest {

    private static Stream<Arguments> provideValuesForCoordinateToIndex() {
        return Stream.of(
            Arguments.of(10, new Coordinate(2, 5), 25),
            Arguments.of(3, new Coordinate(0, 0), 0),
            Arguments.of(4, new Coordinate(1, 3), 7),
            Arguments.of(5, new Coordinate(0, 4), 4),
            Arguments.of(6, new Coordinate(2, 0), 12)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValuesForCoordinateToIndex")
    void coordinateToIndex(int width, Coordinate coordinate, int expectedIndex) {
        CoordinateIndexConverter converter = new CoordinateIndexConverter();
        int actualIndex = converter.coordinateToIndex(coordinate, width);
        assertEquals(expectedIndex, actualIndex);
    }

    private static Stream<Arguments> provideValuesForIndexToCoordinates() {
        return Stream.of(
            Arguments.of(10, 25, new Coordinate(2, 5)),
            Arguments.of(3, 0, new Coordinate(0, 0)),
            Arguments.of(4, 7, new Coordinate(1, 3)),
            Arguments.of(5, 4, new Coordinate(0, 4)),
            Arguments.of(6, 12, new Coordinate(2, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValuesForIndexToCoordinates")
    void indexToCoordinate(int width, int index, Coordinate expectedCoordinate) {
        CoordinateIndexConverter converter = new CoordinateIndexConverter();
        Coordinate actualCoordinate = converter.indexToCoordinate(index, width);
        assertTrue(expectedCoordinate.row() == actualCoordinate.row() &&
            expectedCoordinate.col() == actualCoordinate.col());
    }
}
