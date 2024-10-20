package backend.academy.mazes.solvers;

import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.commons.ParentsPathConverter;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.ArrayList;
import java.util.List;

public class BfsMazeSolver  implements MazeSolver {
    private final CoordinateIndexConverter ciConverter;
    private final ParentsPathConverter ppConverter;

    public BfsMazeSolver(CoordinateIndexConverter ciConverter, ParentsPathConverter ppConverter) {
        this.ciConverter = ciConverter;
        this.ppConverter = ppConverter;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int numberOfCells = maze.height() * maze.width();
        int startIndex = ciConverter.coordinateToIndex(start, maze.width());
        List<Integer> queue = new ArrayList<>();
        queue.add(startIndex);

        List<Integer> distances = new ArrayList<>(numberOfCells);
        List<Integer> parents = new ArrayList<>(numberOfCells);

        for (int i = 0; i < numberOfCells; ++i) {
            distances.add(-1);
            parents.add(null);
        }

        parents.set(startIndex, 0);

        while (!queue.isEmpty()) {
            int vertex = queue.removeFirst();
            for (int j = 0; j < numberOfCells; ++j) {
                if (maze.grid()[vertex][j]) {
                    if (distances.get(j) == -1) {
                        queue.add(j);
                        distances.set(j, distances.get(vertex) + 1);
                        parents.set(j, vertex);
                    }
                }
            }
        }

        return ppConverter.parentsToPath(start, end, maze.width(), parents);
    }
}
