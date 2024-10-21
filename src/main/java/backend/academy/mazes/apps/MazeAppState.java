package backend.academy.mazes.apps;

import backend.academy.mazes.analyzers.PathStatistics;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import backend.academy.mazes.fillers.MazeFiller;
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
    private Random generatorRandom;
    private DirectionCoordinateConverter diConverter;
    private int height;
    private int width;
    private MazeGenerator generator;
    private Maze maze;
    private MazeRenderer renderer;
    private Coordinate start;
    private Coordinate end;
    private MazeSolver solver;
    private List<Coordinate> solution;
    private PathStatistics pathStatistics;
    private MazeFiller filler;
}
