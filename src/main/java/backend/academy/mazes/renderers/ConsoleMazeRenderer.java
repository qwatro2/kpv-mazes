package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;

import java.util.List;
import java.util.function.Function;

public class ConsoleMazeRenderer implements MazeRenderer {
    private final String cross;
    private final String horizontalWall;
    private final String verticalWall;
    private final String horizontalPassage;
    private final String verticalPassage;
    private final String horizontalPath;
    private final String verticalPath;
    private final String verticalStartPath;
    private final String horizontalStartPath;
    private final String verticalEndPath;
    private final String horizontalEndPath;

    public static MazeRenderer getPlusMinusMazeRenderer() {
        return new ConsoleMazeRenderer("+", "--", "|",
            "  ", " ", "**", "*",
            "A", "-A", "B", "-B");
    }

    public static MazeRenderer getColorfulMazeRenderer() {
        return new ConsoleMazeRenderer("⬜", "⬜", "⬜", "⬛",
            "⬛", "\uD83D\uDFE8", "\uD83D\uDFE8",
            "\uD83C\uDD70️", "\uD83C\uDD70️",
            "\uD83C\uDD71️", "\uD83C\uDD71️");
    }

    private ConsoleMazeRenderer(String cross, String horizontalWall, String verticalWall,
        String horizontalPassage, String verticalPassage, String horizontalPath,
        String verticalPath, String verticalStartPath, String horizontalStartPath,
        String verticalEndPath, String horizontalEndPath) {

        this.cross = cross;
        this.horizontalWall = horizontalWall;
        this.verticalWall = verticalWall;
        this.horizontalPassage = horizontalPassage;
        this.verticalPassage = verticalPassage;
        this.horizontalPath = horizontalPath;
        this.verticalPath = verticalPath;
        this.verticalStartPath = verticalStartPath;
        this.horizontalStartPath = horizontalStartPath;
        this.verticalEndPath = verticalEndPath;
        this.horizontalEndPath = horizontalEndPath;
    }

    @Override
    public String render(Maze maze) {
        return render(maze, (Coordinate _) -> false, null, null);
    }

    @Override
    public String render(Maze maze, Coordinate start, Coordinate end) {
        return render(maze, (Coordinate _) -> false, start, end);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end) {
        return render(maze, path::contains, start, end);
    }

    private String render(Maze maze, Function<Coordinate, Boolean> pathPredicate,
        Coordinate start, Coordinate end) {
        StringBuilder sb = new StringBuilder();

        boolean startNotMarked = true;
        boolean endNotMarked = true;

        sb.append(cross);
        for (int col = 0; col < maze.width(); ++col) {
            if (start != null) {
                if (startNotMarked && start.col() == col && start.row() == 0) {
                    sb.append(horizontalStartPath);
                    startNotMarked = false;
                } else if (endNotMarked && end.col() == col && end.row() == 0) {
                    sb.append(horizontalEndPath);
                    endNotMarked = false;
                } else {
                    sb.append(horizontalWall);
                }
            } else {
                sb.append(horizontalWall);
            }
            sb.append(cross);
        }
        sb.append('\n');

        boolean[][] grid = maze.grid();

        for (int row = 0; row < maze.height(); ++row) {
            if (start != null) {
                if (startNotMarked && start.row() == row && start.col() == 0) {
                    sb.append(verticalStartPath);
                    startNotMarked = false;
                } else if (endNotMarked && end.row() == row && end.col() == 0) {
                    sb.append(verticalEndPath);
                    endNotMarked = false;
                } else {
                    sb.append(verticalWall);
                }
            } else {
                sb.append(verticalWall);
            }

            for (int col = 0; col < maze.width(); ++col) {
                sb.append(pathPredicate.apply(new Coordinate(row, col)) ? horizontalPath : horizontalPassage);

                if (col == maze.width() - 1) {
                    if (start != null) {
                        if (startNotMarked && start.row() == row && start.col() == maze.width() - 1) {
                            sb.append(verticalStartPath);
                            startNotMarked = false;
                        } else if (endNotMarked && end.row() == row && end.col() == maze.width() - 1) {
                            sb.append(verticalEndPath);
                            endNotMarked = false;
                        } else {
                            sb.append(verticalWall);
                        }
                    } else {
                        sb.append(verticalWall);
                    }
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
                    if (start != null) {
                        if (startNotMarked && start.col() == col && start.row() == maze.height() - 1) {
                            sb.append(horizontalStartPath);
                            startNotMarked = false;
                        } else if (endNotMarked && end.col() == col && end.row() == maze.height() - 1) {
                            sb.append(horizontalEndPath);
                            endNotMarked = false;
                        } else {
                            sb.append(horizontalWall);
                        }
                    } else {
                        sb.append(horizontalWall);
                    }
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
