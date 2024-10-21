package backend.academy.mazes.analyzers;

import backend.academy.mazes.entities.Cell;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public class SimpleMazePathAnalyzer implements MazePathAnalyzer {
    @Override
    public PathStatistics analyze(Maze maze, List<Coordinate> path) {
        SimplePathStatistics pathStatistics = new SimplePathStatistics();
        Cell[][] cells = maze.cells();

        for (Coordinate coordinate : path) {
            int row = coordinate.row();
            int col = coordinate.col();
            switch (cells[row][col].cellType()) {
                case GOOD:
                    pathStatistics.incrementGoodPassages();
                    break;
                case BAD:
                    pathStatistics.incrementBadPassages();
                    break;
                default:
                    break;
            }
        }

        return pathStatistics;
    }
}
