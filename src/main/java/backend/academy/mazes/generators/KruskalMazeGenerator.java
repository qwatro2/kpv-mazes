package backend.academy.mazes.generators;

import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.commons.Randomizable;
import backend.academy.mazes.entities.Cell;
import backend.academy.mazes.entities.Edge;
import backend.academy.mazes.entities.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class KruskalMazeGenerator extends AbstractMazeGenerator
    implements MazeGenerator, Randomizable<KruskalMazeGenerator> {
    private Random random;

    @Override
    public Maze generate(int height, int width) {
        Cell[][] cells = makeCells(height, width);
        List<Edge> startEdges = getBaseEdges(height, width);
        List<Edge> edges = kruskal(height, width, startEdges);
        boolean[][] grid = getGridByEdges(height, width, edges);
        return new Maze(height, width, cells, grid);
    }

    private List<Edge> kruskal(int height, int width, List<Edge> startEdges) {
        CoordinateIndexConverter ciConverter = new CoordinateIndexConverter();
        int numberOfCells = height * width;
        List<Edge> result = new ArrayList<>();

        List<Integer> parent = initializeEmptyList(numberOfCells);
        List<Integer> rank = initializeEmptyList(numberOfCells);
        for (int i = 0; i < numberOfCells; ++i) {
            makeSet(parent, rank, i);
        }

        while (!startEdges.isEmpty()) {
            Edge randomEdge = popRandomEdge(startEdges);
            int fromCoordinate = ciConverter.coordinateToIndex(randomEdge.from(), width);
            int toCoordinate = ciConverter.coordinateToIndex(randomEdge.to(), width);
            if (findSet(parent, fromCoordinate) != findSet(parent, toCoordinate)) {
                result.add(randomEdge);
                unionSets(parent, rank, fromCoordinate, toCoordinate);
            }
        }

        return result;
    }

    private List<Integer> initializeEmptyList(int size) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(0);
        }
        return result;
    }

    private Edge popRandomEdge(List<Edge> edges) {
        int index = this.random.nextInt(0, edges.size());
        return edges.remove(index);
    }

    private void makeSet(List<Integer> parent, List<Integer> rank, int vertexIndex) {
        parent.set(vertexIndex, vertexIndex);
        rank.set(vertexIndex, 0);
    }

    private int findSet(List<Integer> parent, int vertexIndex) {
        if (vertexIndex == parent.get(vertexIndex)) {
            return vertexIndex;
        }
        parent.set(vertexIndex, findSet(parent, parent.get(vertexIndex)));
        return parent.get(vertexIndex);
    }

    private void unionSets(List<Integer> parent, List<Integer> rank, int setA, int setB) {
        setA = findSet(parent, setA);
        setB = findSet(parent, setB);
        if (setA != setB) {
            if (rank.get(setA) < rank.get(setB)) {
                int tmp = setA;
                setA = setB;
                setB = tmp;
            }
            parent.set(setB, setA);
            if (Objects.equals(rank.get(setA), rank.get(setB))) {
                rank.set(setA, 1+ rank.get(setA));
            }
        }
    }

    @Override
    public KruskalMazeGenerator setRandom(Random random) {
        this.random = random;
        return this;
    }
}
