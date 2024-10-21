package backend.academy.mazes.analyzers;

import backend.academy.mazes.entities.Cell;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Maze;
import backend.academy.mazes.types.CellType;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleMazePathAnalyzerTest {
    private final SimpleMazePathAnalyzer analyzer = new SimpleMazePathAnalyzer();

    @Test
    void analyze() {
        Maze maze = mock(Maze.class);

        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                cells[i][j] = new Cell(i, j);
            }
        }
        cells[0][0].cellType(CellType.GOOD);
        cells[0][1].cellType(CellType.GOOD);
        cells[0][2].cellType(CellType.BAD);
        cells[2][2].cellType(CellType.GOOD);

        when(maze.cells()).thenReturn(cells);

        List<Coordinate> path = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2),
            new Coordinate(1, 2),
            new Coordinate(2, 2)
        );

        PathStatistics statistics = mock(SimplePathStatistics.class);
        when(statistics.constructString()).thenReturn("Number of good passages: 3\n" +
            "Number of bad passages: 1");

        assertEquals(statistics.constructString(), analyzer.analyze(maze, path).constructString());
    }
}
