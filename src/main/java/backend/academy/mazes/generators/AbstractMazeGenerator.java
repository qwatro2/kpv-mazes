package backend.academy.mazes.generators;

import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.entities.Cell;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Edge;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMazeGenerator {
    protected Cell[][] makeCells(int height, int width) {
        Cell[][] cells = new Cell[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
        return cells;
    }

    protected void initializeEmptyGrid(int height, int width, boolean[][] grid) {
        for (int i = 0; i < height * width; ++i) {
            for (int j = 0; j < height * width; ++j) {
                grid[i][j] = false;
            }
        }
    }

    protected List<Edge> getBaseEdges(int height, int width) {
        List<Edge> result = new ArrayList<>();

        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Coordinate currentCoordinate = new Coordinate(row, col);
                if (row != 0) {
                    result.add(new Edge(currentCoordinate, new Coordinate(row - 1, col)));
                }
                if (row != height - 1) {
                    result.add(new Edge(currentCoordinate, new Coordinate(row + 1, col)));
                }
                if (col != 0) {
                    result.add(new Edge(currentCoordinate, new Coordinate(row, col - 1)));
                }
                if (col != width - 1) {
                    result.add(new Edge(currentCoordinate, new Coordinate(row, col + 1)));
                }
            }
        }

        return result;
    }

    protected boolean[][] getGridByEdges(int height, int width, List<Edge> edges) {
        CoordinateIndexConverter ciConverter = new CoordinateIndexConverter();
        int numberOfCells = height * width;
        boolean[][] grid = new boolean[numberOfCells][numberOfCells];
        initializeEmptyGrid(height, width, grid);

        for (Edge edge : edges) {
            int fromIndex = ciConverter.coordinateToIndex(edge.from(), width);
            int toIndex = ciConverter.coordinateToIndex(edge.to(), width);
            grid[fromIndex][toIndex] = true;
            grid[toIndex][fromIndex] = true;
        }

        return grid;
    }
}
