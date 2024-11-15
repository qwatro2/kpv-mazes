package backend.academy.mazes.renderers;

import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import java.util.List;
import java.util.function.Predicate;
import lombok.Setter;

public class ConsoleMazeRenderer implements MazeRenderer {
    @Setter private String cross;
    @Setter private String horizontalWall;
    @Setter private String verticalWall;
    @Setter private String horizontalPassage;
    @Setter private String verticalPassage;
    @Setter private String horizontalPath;
    @Setter private String verticalPath;
    @Setter private String verticalStartPath;
    @Setter private String horizontalStartPath;
    @Setter private String verticalEndPath;
    @Setter private String horizontalEndPath;
    @Setter private String goodPassage;
    @Setter private String badPassage;
    private boolean startNotMarked;
    private boolean endNotMarked;

    public static MazeRenderer getPlusMinusMazeRenderer() {
        return new ConsoleMazeRenderer()
            .cross("+")
            .horizontalWall("--")
            .verticalWall("|")
            .horizontalPassage("  ")
            .verticalPassage(" ")
            .horizontalPath("**")
            .verticalPath("*")
            .verticalStartPath("A")
            .horizontalStartPath("-A")
            .verticalEndPath("B")
            .horizontalEndPath("-B")
            .goodPassage(" O")
            .badPassage(" X");
    }

    public static MazeRenderer getColorfulMazeRenderer() {
        String wall = "⬜";
        String emptyPassage = "⬛";
        String path = "\uD83D\uDFE8";
        String startPath = "\uD83C\uDD70️";
        String endPath = "\uD83C\uDD71️";
        return new ConsoleMazeRenderer()
            .cross(wall)
            .horizontalWall(wall)
            .verticalWall(wall)
            .horizontalPassage(emptyPassage)
            .verticalPassage(emptyPassage)
            .horizontalPath(path)
            .verticalPath(path)
            .verticalStartPath(startPath)
            .horizontalStartPath(startPath)
            .verticalEndPath(endPath)
            .horizontalEndPath(endPath)
            .goodPassage("\uD83D\uDFE9")
            .badPassage("\uD83D\uDFE5");
    }

    protected ConsoleMazeRenderer() {
    }

    @Override
    public String render(Maze maze) {
        return render(maze, (coordinate) -> false, null, null);
    }

    @Override
    public String render(Maze maze, Coordinate start, Coordinate end) {
        return render(maze, (coordinate) -> false, start, end);
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

        unmark();
        constructTopWall(sb, maze, start, end);
        for (int row = 0; row < maze.height(); ++row) {
            constructLeftWall(sb, row, start, end);
            constructRow(sb, row, maze, start, end, pathPredicate);
            constructBottomWall(sb, row, maze, start, end, pathPredicate);
        }

        return sb.toString();
    }

    private void unmark() {
        this.startNotMarked = true;
        this.endNotMarked = true;
    }

    private void constructTopWall(StringBuilder sb, Maze maze, Coordinate start, Coordinate end) {
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
    }

    private void constructLeftWall(StringBuilder sb, int row, Coordinate start, Coordinate end) {
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
    }

    private void constructRow(
        StringBuilder sb, int row, Maze maze,
        Coordinate start, Coordinate end, Predicate<Coordinate> pathPredicate
    ) {
        boolean[][] grid = maze.grid();

        for (int col = 0; col < maze.width(); ++col) {
            String passage = switch (maze.cells()[row][col].cellType()) {
                case EMPTY -> horizontalPassage;
                case GOOD -> goodPassage;
                case BAD -> badPassage;
            };
            sb.append(pathPredicate.test(new Coordinate(row, col)) ? horizontalPath : passage);

            if (col == maze.width() - 1) {
                constructRowLastCol(sb, row, maze, start, end);
                continue;
            }

            boolean checkRightCell = grid[row * maze.width() + col][row * maze.width() + col + 1];
            sb.append(checkRightCell ? (pathPredicate.test(new Coordinate(row, col))
                && pathPredicate.test(new Coordinate(row, col + 1)) ? verticalPath : verticalPassage)
                : verticalWall);
        }
        sb.append('\n');
    }

    private void constructBottomWall(
        StringBuilder sb, int row, Maze maze,
        Coordinate start, Coordinate end, Predicate<Coordinate> pathPredicate
    ) {
        boolean[][] grid = maze.grid();
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
                && pathPredicate.test(new Coordinate(row + 1, col)) ? horizontalPath : horizontalPassage)
                : horizontalWall);
            sb.append(cross);
        }
        sb.append('\n');
    }

    private void constructRowLastCol(
        StringBuilder sb, int row,
        Maze maze, Coordinate start, Coordinate end
    ) {
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
    }
}
