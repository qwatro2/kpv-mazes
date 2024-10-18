package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import java.util.ArrayList;
import java.util.List;

public interface ParentsPathConverter extends CoordinateIndexConverter {
    default List<Coordinate> parentsToPath(Coordinate start, Coordinate end, int width, List<Integer> parents) {
        List<Coordinate> result = new ArrayList<>();
        int startIndex = coordinateToIndex(start, width);
        int endIndex = coordinateToIndex(end, width);
        while (endIndex != startIndex) {
            result.add(indexToCoordinate(endIndex, width));
            endIndex = parents.get(endIndex);
        }
        result.add(start);
        return result.reversed();
    }
}
