package backend.academy.mazes.entities;

public record Maze(int height, int width, Cell[][] cells, boolean[][] grid) { }
