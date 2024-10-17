package backend.academy.mazes.solvers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public interface MazeSolver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
