package backend.academy.mazes.generators;

import backend.academy.mazes.entities.Cell;

public abstract class AbstractMazeGenerationInitializer {
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
}
