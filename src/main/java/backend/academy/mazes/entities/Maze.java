package backend.academy.mazes.entities;

import lombok.Getter;

@Getter
public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] cells;
    private final boolean[][] grid;

    public Maze(int height, int width, Cell[][] cells, boolean[][] grid) {
        this.height = height;
        this.width = width;
        this.cells = cells;
        this.grid = grid;
    }
}
