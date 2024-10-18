package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;
import java.util.function.Function;

public class ColorfulMazeRenderer implements MazeRenderer {
    @Override
    public String render(Maze maze) {
        return render(maze, (Coordinate _) -> false);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        return render(maze, path::contains);
    }

    private String render(Maze maze, Function<Coordinate, Boolean> pathPredicate) {
        String wall = "⬜";
        String passage = "⬛";
        String path = "\uD83D\uDFE8";

        StringBuilder sb = new StringBuilder();

        sb.append(wall);
        for (int col = 0; col < maze.width(); ++col) {
            sb.append(wall);
            sb.append(wall);
        }
        sb.append('\n');

        boolean[][] grid = maze.grid();

        for (int row = 0; row < maze.height(); ++row) {
            sb.append(wall);
            for (int col = 0; col < maze.width(); ++col) {
                sb.append(pathPredicate.apply(new Coordinate(row, col)) ? path : passage);

                if (col == maze.width() - 1) {
                    sb.append(wall);
                    continue;
                }

                boolean checkRightCell = grid[row * maze.width() + col][row * maze.width() + col + 1];
                sb.append(checkRightCell ? (pathPredicate.apply(new Coordinate(row, col)) &&
                    pathPredicate.apply(new Coordinate(row, col + 1)) ? path : passage) : wall);
            }
            sb.append('\n');
            sb.append(wall);
            for (int col = 0; col < maze.width(); ++col) {
                if (row == maze.height() - 1) {
                    sb.append(wall);
                    sb.append(wall);
                    continue;
                }

                boolean checkBottomCell = grid[row * maze.width() + col][(row + 1) * maze.width() + col];
                sb.append(checkBottomCell ? (pathPredicate.apply(new Coordinate(row, col)) &&
                    pathPredicate.apply(new Coordinate(row + 1, col)) ? path : passage) : wall);
                sb.append(wall);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
