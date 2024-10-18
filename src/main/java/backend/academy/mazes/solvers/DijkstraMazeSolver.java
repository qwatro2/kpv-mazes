package backend.academy.mazes.solvers;

import backend.academy.mazes.commons.Converter;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.ArrayList;
import java.util.List;

public class DijkstraMazeSolver implements MazeSolver, Converter {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int numberOfCells = maze.height() * maze.width();

        List<Integer> labels = initializeLabels(numberOfCells);
        labels.set(coordinateToIndex(start, maze.width()), 0);

        List<Integer> parents = initializeParents(numberOfCells);

        List<Boolean> visited = initializeVisited(numberOfCells);

        while (visited.stream().anyMatch((Boolean b) -> !b)) {
            int index = findNotVisitedIndexWithSmallestLabel(maze.grid(), visited, labels);
            for (int col = 0; col < numberOfCells; ++col) {
                if (maze.grid()[index][col] && !visited.get(col)) {
                    int newPathLength = labels.get(index) + 1;
                    if (newPathLength < labels.get(col)) {
                        labels.set(col, newPathLength);
                        parents.set(col, index);
                    }
                }
            }
            visited.set(index, true);
        }

        List<Coordinate> result = new ArrayList<>();
        int startIndex = coordinateToIndex(start, maze.width());
        int endIndex = coordinateToIndex(end, maze.width());
        while (endIndex != startIndex) {
            result.add(indexToCoordinate(endIndex, maze.width()));
            endIndex = parents.get(endIndex);
        }
        result.add(start);

        return result.reversed();
    }

    private List<Integer> initializeLabels(int size) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(Integer.MAX_VALUE);
        }
        return result;
    }

    private List<Integer> initializeParents(int size) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(null);
        }
        return result;
    }

    private List<Boolean> initializeVisited(int size) {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(false);
        }
        return result;
    }

    private int findNotVisitedIndexWithSmallestLabel(boolean[][] grid, List<Boolean> visited, List<Integer> labels) {
        int resultIndex = -1;
        int currentMinimum = Integer.MAX_VALUE;

        for (int index = 0; index < labels.size(); ++index) {
            if (labels.get(index) < currentMinimum && !visited.get(index)) {
                currentMinimum = labels.get(index);
                resultIndex = index;
            }
        }

        return resultIndex;
    }


}
