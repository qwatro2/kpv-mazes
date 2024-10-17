package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public interface MazeRenderer {
    String render(Maze maze);
    String render(Maze maze, List<Coordinate> path);
}
