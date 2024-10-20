package backend.academy.mazes.commons;

import backend.academy.mazes.entities.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class ParentsPathConverter {
    private final CoordinateIndexConverter ciConverter;

    public ParentsPathConverter(CoordinateIndexConverter ciConverter) {
        this.ciConverter = ciConverter;
    }

    public List<Coordinate> parentsToPath(Coordinate start, Coordinate end, int width, List<Integer> parents) {
        List<Coordinate> result = new ArrayList<>();
        int startIndex = this.ciConverter.coordinateToIndex(start, width);
        int endIndex = this.ciConverter.coordinateToIndex(end, width);
        while (endIndex != startIndex) {
            result.add(this.ciConverter.indexToCoordinate(endIndex, width));
            Integer endIndexParent = parents.get(endIndex);
            if (endIndexParent == null) {
                return null;
            }
            endIndex = endIndexParent;
        }
        result.add(start);
        return result.reversed();
    }
}
