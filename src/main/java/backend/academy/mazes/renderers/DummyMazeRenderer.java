package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public class DummyMazeRenderer implements MazeRenderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        sb.append('+');
        sb.append("--+".repeat(Math.max(0, maze.width())));
        sb.append('\n');

        boolean[][] grid = maze.grid();

        for (int row = 0; row < maze.height(); ++row) {
            sb.append('|');
            for (int col = 0; col < maze.width(); ++col) {
                sb.append("  ");

                if (col == maze.width() - 1) {
                    sb.append('|');
                    continue;
                }

                boolean checkRightCell = grid[row * maze.width() + col][row * maze.width() + col + 1];
                sb.append(checkRightCell ? ' ' : '|');
            }
            sb.append('\n');
            sb.append('+');
            for (int col = 0; col < maze.width(); ++col) {
                if (row == maze.height() - 1) {
                    sb.append("--+");
                    continue;
                }

                boolean checkBottomCell = grid[row * maze.width() + col][(row + 1) * maze.width() + col];
                sb.append(checkBottomCell ? "  +" : "--+");
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        return "";
    }
}
