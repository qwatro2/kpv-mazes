package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.types.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class ParentsPathConverterTest {

    private static Stream<Arguments> provideValuesForParentsToPath() {
        return Stream.of(
            Arguments.of(new Coordinate(0, 0), new Coordinate(2, 2),
                3, List.of(0, 0, 1, 0, 0, 2, 0, 0, 5),
                List.of(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2),
                    new Coordinate(1, 2), new Coordinate(2, 2))),
            Arguments.of(new Coordinate(0, 0), new Coordinate(0, 2),
                3, List.of(0, 0, 1, 0, 0, 0, 0, 0, 0),
                List.of(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2))),
            Arguments.of(new Coordinate(0, 0), new Coordinate(1, 2),
                3, List.of(0, 4, 1, 0, 7, 2, 3, 6, 0),
                List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                    new Coordinate(2, 1), new Coordinate(1, 1), new Coordinate(0, 1),
                    new Coordinate(0, 2), new Coordinate(1, 2)))
            );
    }

    @ParameterizedTest
    @MethodSource("provideValuesForParentsToPath")
    void parentsToPath(Coordinate start, Coordinate end, int width, List<Integer> parents, List<Coordinate> expected) {
        CoordinateIndexConverter ciConverter = new CoordinateIndexConverter();
        ParentsPathConverter converter = new ParentsPathConverter(ciConverter);
        List<Coordinate> actual = converter.parentsToPath(start, end, width, parents);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); ++i) {
            assertTrue(expected.get(i).row() == actual.get(i).row() &&
                expected.get(i).col() == actual.get(i).col());
        }
    }
}
