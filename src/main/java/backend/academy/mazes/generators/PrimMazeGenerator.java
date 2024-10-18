package backend.academy.mazes.generators;

import backend.academy.mazes.commons.Randomizable;
import backend.academy.mazes.entities.Cell;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.entities.Edge;
import backend.academy.mazes.entities.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimMazeGenerator extends AbstractMazeGenerator
    implements MazeGenerator, Randomizable<PrimMazeGenerator> {
    private Random random;

    @Override
    public Maze generate(int height, int width) {
        Cell[][] cells = makeCells(height, width);
        int numberOfCells = height * width;
        boolean[][] grid = new boolean[numberOfCells][numberOfCells];
        initializeEmptyGrid(height, width, grid);

        boolean[][] visited = new boolean[height][width];
        List<Cell> visitedCells = new ArrayList<>();
        Coordinate coordinateOfRandomCell = getRandomCoordinates(height, width);
        List<Edge> edgesToCheck = new ArrayList<>();

        int randomRow = coordinateOfRandomCell.row();
        int randomCol = coordinateOfRandomCell.col();

        visited[randomRow][randomCol] = true;
        visitedCells.add(cells[randomRow][randomCol]);

        addEdgesToNotVisitedCells(height, width, visited, edgesToCheck, coordinateOfRandomCell);

        while (!edgesToCheck.isEmpty()) {
            int randomEdgeIndex = random.nextInt(edgesToCheck.size());
            Edge randomEdge = edgesToCheck.get(randomEdgeIndex);
            Coordinate toCellCoordinate = randomEdge.to();

            visited[toCellCoordinate.row()][toCellCoordinate.col()] = true;
            visitedCells.add(cells[toCellCoordinate.row()][toCellCoordinate.col()]);
            int toCellGridIndex = toCellCoordinate.row() * width + toCellCoordinate.col();

            Coordinate fromCellCoordinate = randomEdge.from();
            int fromCellGridIndex = fromCellCoordinate.row() * width + fromCellCoordinate.col();
            grid[fromCellGridIndex][toCellGridIndex] = true;
            grid[toCellGridIndex][fromCellGridIndex] = true;

            edgesToCheck.clear();
            for (Cell currentVisitedCell : visitedCells) {
                addEdgesToNotVisitedCells(height, width, visited, edgesToCheck, currentVisitedCell.getCoordinate());
            }
        }

        return new Maze(height, width, cells, grid);
    }

    private Coordinate getRandomCoordinates(int height, int width) {
        int row = random.nextInt(height);
        int col = random.nextInt(width);
        return new Coordinate(row, col);
    }

    private void addEdgesToNotVisitedCells(
        int height, int width, boolean[][] visited,
        List<Edge> edgesToCheck, Coordinate cellCoordinate
    ) {
        int row = cellCoordinate.row();
        int col = cellCoordinate.col();

        if (row != 0 && !visited[row - 1][col]) {
            edgesToCheck.add(new Edge(cellCoordinate, new Coordinate(row - 1, col)));
        }
        if (row != height - 1 && !visited[row + 1][col]) {
            edgesToCheck.add(new Edge(cellCoordinate, new Coordinate(row + 1, col)));
        }
        if (col != 0 && !visited[row][col - 1]) {
            edgesToCheck.add(new Edge(cellCoordinate, new Coordinate(row, col - 1)));
        }
        if (col != width - 1 && !visited[row][col + 1]) {
            edgesToCheck.add(new Edge(cellCoordinate, new Coordinate(row, col + 1)));
        }
    }

    @Override
    public PrimMazeGenerator setRandom(Random random) {
        this.random = random;
        return this;
    }
}
