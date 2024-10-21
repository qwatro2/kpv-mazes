package backend.academy.mazes.analyzers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public interface MazePathAnalyzer {
    PathStatistics analyze(Maze maze, List<Coordinate> path);
}
