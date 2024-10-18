package backend.academy.mazes.solvers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BfsMazeSolver  implements MazeSolver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int numberOfCells = maze.height() * maze.width();
        int startIndex = coordinateToIndex(start, maze);
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

        List<Coordinate> result = new ArrayList<>();
        int endIndex = coordinateToIndex(end, maze);
        while (endIndex != startIndex) {
            result.add(indexToCoordinate(endIndex, maze));
            endIndex = parents.get(endIndex);
        }
        result.add(start);

        return result.reversed();
    }

    private int coordinateToIndex(Coordinate coordinate, Maze maze) {
        return coordinate.row() * maze.width() + coordinate.col();
    }

    private Coordinate indexToCoordinate(int index, Maze maze) {
        return new Coordinate(index / maze.width(), index % maze.width());
    }
}
