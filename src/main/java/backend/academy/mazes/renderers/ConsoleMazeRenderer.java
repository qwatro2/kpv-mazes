package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;
import java.util.function.Predicate;

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
    private final String goodPassage;
    private final String badPassage;

    public static MazeRenderer getPlusMinusMazeRenderer() {
        return new ConsoleMazeRenderer("+", "--", "|",
            "  ", " ", "**", "*",
            "A", "-A", "B", "-B",
            " O", " X");
    }

    public static MazeRenderer getColorfulMazeRenderer() {
        String wall = "⬜";
        String emptyPassage = "⬛";
        String path = "\uD83D\uDFE8";
        String startPath = "\uD83C\uDD70️";
        String endPath = "\uD83C\uDD71️";
        return new ConsoleMazeRenderer(wall, wall, wall, emptyPassage, emptyPassage,
            path, path, startPath, startPath, endPath, endPath,
            "\uD83D\uDFE9", "\uD83D\uDFE5");
    }

    protected ConsoleMazeRenderer(
        String cross, String horizontalWall, String verticalWall,
        String horizontalPassage, String verticalPassage, String horizontalPath,
        String verticalPath, String verticalStartPath, String horizontalStartPath,
        String verticalEndPath, String horizontalEndPath,
        String goodPassage, String badPassage
    ) {
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
        this.goodPassage = goodPassage;
        this.badPassage = badPassage;
    }

    @Override
    public String render(Maze maze) {
        return render(maze, (_) -> false, null, null);
    }

    @Override
    public String render(Maze maze, Coordinate start, Coordinate end) {
        return render(maze, (_) -> false, start, end);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end) {
        if (path == null) {
            return "Path not found!";
        }
        return render(maze, path::contains, start, end);
    }

    @Override
    public String getLegend() {
        return goodPassage + " - good passage\n" + badPassage + " - bad passage";
    }

    private String render(Maze maze, Predicate<Coordinate> pathPredicate, Coordinate start, Coordinate end) {
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
                String passage = switch (maze.cells()[row][col].cellType()) {
                    case EMPTY -> horizontalPassage;
                    case GOOD -> goodPassage;
                    case BAD -> badPassage;
                };
                sb.append(pathPredicate.test(new Coordinate(row, col)) ? horizontalPath : passage);

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
                sb.append(checkRightCell ? (pathPredicate.test(new Coordinate(row, col))
                    && pathPredicate.test(new Coordinate(row, col + 1)) ? verticalPath : verticalPassage)
                    : verticalWall);
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
                sb.append(checkBottomCell ? (pathPredicate.test(new Coordinate(row, col))
                    && pathPredicate.test(new Coordinate(row + 1, col)) ? horizontalPath : horizontalPassage) :
                    horizontalWall);
                sb.append(cross);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
