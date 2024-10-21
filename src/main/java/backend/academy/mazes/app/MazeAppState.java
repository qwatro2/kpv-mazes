package backend.academy.mazes.app;

import backend.academy.mazes.analyzers.PathStatistics;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import backend.academy.mazes.generators.MazeGenerator;
import backend.academy.mazes.renderers.MazeRenderer;
import backend.academy.mazes.solvers.MazeSolver;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class MazeAppState {
    private int height;
    private int width;
    private Maze maze;
    private Coordinate start;
    private Coordinate end;
    private List<Coordinate> solution;
    private PathStatistics pathStatistics;
    private MazeGenerator generator;
    private MazeRenderer renderer;
    private MazeSolver solver;
    private Random generatorRandom;
}
