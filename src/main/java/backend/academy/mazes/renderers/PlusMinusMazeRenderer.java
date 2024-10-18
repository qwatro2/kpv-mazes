package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;
import java.util.function.Function;

public class PlusMinusMazeRenderer implements MazeRenderer {
    @Override
    public String render(Maze maze) {
        return render(maze, (Coordinate _) -> false);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        return render(maze, path::contains);
    }

    private String render(Maze maze, Function<Coordinate, Boolean> pathPredicate) {
        String cross = "+";
        String horizontalWall = "--";
        String verticalWall = "|";
        String horizontalPassage = "  ";
        String verticalPassage = " ";
        String horizontalPath = " *";
        String verticalPath = "*";

        StringBuilder sb = new StringBuilder();

        sb.append(cross);
        for (int col = 0; col < maze.width(); ++col) {
            sb.append(horizontalWall);
            sb.append(cross);
        }
        sb.append('\n');

        boolean[][] grid = maze.grid();

        for (int row = 0; row < maze.height(); ++row) {
            sb.append(verticalWall);
            for (int col = 0; col < maze.width(); ++col) {
                sb.append(pathPredicate.apply(new Coordinate(row, col)) ? horizontalPath : horizontalPassage);

                if (col == maze.width() - 1) {
                    sb.append(verticalWall);
                    continue;
                }

                boolean checkRightCell = grid[row * maze.width() + col][row * maze.width() + col + 1];
                sb.append(checkRightCell ? (pathPredicate.apply(new Coordinate(row, col)) &&
                        pathPredicate.apply(new Coordinate(row, col + 1)) ? verticalPath : verticalPassage) : verticalWall);
            }
            sb.append('\n');
            sb.append(cross);
            for (int col = 0; col < maze.width(); ++col) {
                if (row == maze.height() - 1) {
                    sb.append(horizontalWall);
                    sb.append(cross);
                    continue;
                }

                boolean checkBottomCell = grid[row * maze.width() + col][(row + 1) * maze.width() + col];
                sb.append(checkBottomCell ? (pathPredicate.apply(new Coordinate(row, col)) &&
                        pathPredicate.apply(new Coordinate(row + 1, col)) ? horizontalPath : horizontalPassage) : horizontalWall);
                sb.append(cross);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
