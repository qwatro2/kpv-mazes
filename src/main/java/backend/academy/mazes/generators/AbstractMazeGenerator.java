package backend.academy.mazes.generators;

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

    protected int coordinateToIndex(Coordinate coordinate, int width) {
        return coordinate.row() * width + coordinate.col();
    }

    protected List<Edge> getEdgesByGrid(int height, int width, boolean[][] grid) {
        int numberOfCells = height * width;
        List<Edge> result = new ArrayList<>();

        for (int fromIndex = 0; fromIndex < numberOfCells; ++fromIndex) {
            for (int toIndex = fromIndex + 1; toIndex < numberOfCells; ++toIndex) {
                if (grid[fromIndex][toIndex]) {
                    Coordinate from = new Coordinate(fromIndex / width, fromIndex % width);
                    Coordinate to = new Coordinate(toIndex / width, toIndex % width);
                    result.add(new Edge(from, to));
                    result.add(new Edge(to, from));
                }
            }
        }

        return result;
    }

    protected boolean[][] getGridByEdges(int height, int width, List<Edge> edges) {
        boolean[][] grid = new boolean[height][width];
        initializeEmptyGrid(height, width, grid);

        for (Edge edge : edges) {
            int fromIndex = coordinateToIndex(edge.from(), width);
            int toIndex = coordinateToIndex(edge.to(), width);
            grid[fromIndex][toIndex] = true;
            grid[toIndex][fromIndex] = true;
        }

        return grid;
    }
}
