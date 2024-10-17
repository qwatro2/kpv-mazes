package backend.academy.mazes.generators;

import backend.academy.mazes.entities.Maze;

public interface MazeGenerator {
    Maze generate(int height, int width);
}
