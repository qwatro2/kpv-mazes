package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;

public class ColorfulMazeRenderer implements MazeRenderer {
    private final String wall = "⬜";
    private final String passage = "⬛";

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        // print top wall
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
                sb.append(passage);

                if (col == maze.width() - 1) {
                    sb.append(wall);
                    continue;
                }

                boolean checkRightCell = grid[row * maze.width() + col][row * maze.width() + col + 1];
                sb.append(checkRightCell ? passage : wall);
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
                sb.append(checkBottomCell ? passage : wall);
                sb.append(wall);
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