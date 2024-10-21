package backend.academy.mazes.app;

import backend.academy.mazes.analyzers.MazePathAnalyzer;
import backend.academy.mazes.analyzers.PathStatistics;
import backend.academy.mazes.analyzers.SimpleMazePathAnalyzer;
import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.commons.ParentsPathConverter;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.entities.Maze;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.types.SolverType;
import backend.academy.mazes.fillers.MazeFiller;
import backend.academy.mazes.fillers.RandomMazeFiller;
import backend.academy.mazes.generators.KruskalMazeGenerator;
import backend.academy.mazes.generators.MazeGenerator;
import backend.academy.mazes.generators.PrimMazeGenerator;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.renderers.ConsoleMazeRenderer;
import backend.academy.mazes.renderers.MazeRenderer;
import backend.academy.mazes.solvers.BfsMazeSolver;
import backend.academy.mazes.solvers.DijkstraMazeSolver;
import backend.academy.mazes.solvers.MazeSolver;
import backend.academy.mazes.waiters.Waiter;
import backend.academy.mazes.writers.Writer;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MazeApp implements App {
    private final Reader reader;
    private final Writer writer;
    private final Waiter waiter;

    private final Random appRandom;
    private final EnumRandomPicker picker;
    private final CoordinateIndexConverter coordinateIndexConverter;
    private final ParentsPathConverter parentsPathConverter;
    private final DirectionCoordinateConverter directionCoordinateConverter;

    private final Random generatorRandom;

    private MazeGenerator generator;
    private MazeRenderer renderer;
    private MazeSolver solver;

    private int height;
    private int width;
    private Maze maze;
    private Coordinate start;
    private Coordinate end;
    private List<Coordinate> solution;
    private PathStatistics pathStatistics;

    public MazeApp(Reader reader, Writer writer, Waiter waiter) {
        this.reader = reader;
        this.writer = writer;
        this.waiter = waiter;
        this.appRandom = new Random();
        this.picker = new EnumRandomPicker().setRandom(this.appRandom);
        this.coordinateIndexConverter = new CoordinateIndexConverter();
        this.parentsPathConverter = new ParentsPathConverter(this.coordinateIndexConverter);
        this.directionCoordinateConverter = new DirectionCoordinateConverter();
        this.generatorRandom = new Random(142857);
    }

    @Override
    public void run() {
        receiveMazeSize();
        receiveMazeGenerator();
        receiveMazeRenderer();

        generateMaze();
        fillMaze();
        sendEmptyMaze();
        waitEnteringButton();

        receiveCoordinates();
        sendMazeWithStartAndEnd();
        waitEnteringButton();

        receiveMazeSolver();
        solveMaze();
        analyzeSolution();
        sendSolvedMaze();
    }

    private void waitEnteringButton() {
        this.waiter.waiting("Enter any button to continue...");
    }

    private void receiveMazeSize() {
        this.height = receiveHeight();
        this.width = receiveWidth();
    }

    private void receiveMazeGenerator() {
        GeneratorType generatorType = reader.readGeneratorType();
        if (generatorType == null) {
            generatorType = picker.pick(GeneratorType.class);
        }

        this.generator = switch (generatorType) {
            case PRIM -> new PrimMazeGenerator().setRandom(generatorRandom);
            case KRUSKAL -> new KruskalMazeGenerator().setRandom(generatorRandom);
        };
    }

    private void receiveMazeRenderer() {
        RendererType rendererType = reader.readRendererType();
        if (rendererType == null) {
            rendererType = picker.pick(RendererType.class);
        }

        this.renderer = switch (rendererType) {
            case PLUSMINUS -> ConsoleMazeRenderer.getPlusMinusMazeRenderer();
            case COLORFUL -> ConsoleMazeRenderer.getColorfulMazeRenderer();
        };
    }

    private void receiveCoordinates() {
        Direction startDirection = receiveStartDirection();
        Direction endDirection = receiveEndDirection(startDirection);
        this.start = directionCoordinateConverter.directionToCoordinate(startDirection, this.height, this.width);
        this.end = directionCoordinateConverter.directionToCoordinate(endDirection, this.height, this.width);
    }

    private void receiveMazeSolver() {
        SolverType solverType = reader.readSolverType();
        if (solverType == null) {
            solverType = picker.pick(SolverType.class);
        }

        this.solver = switch (solverType) {
            case BFS -> new BfsMazeSolver(this.coordinateIndexConverter, this.parentsPathConverter);
            case DIJKSTRA -> new DijkstraMazeSolver(this.coordinateIndexConverter, this.parentsPathConverter);
        };
    }

    private void generateMaze() {
        this.maze = generator.generate(this.height, this.width);
    }

    private void fillMaze() {
        MazeFiller mazeFiller = new RandomMazeFiller().setRandom(new Random());
        mazeFiller.fill(this.maze);
    }

    private void solveMaze() {
        this.solution = this.solver.solve(this.maze, this.start, this.end);
    }

    private void analyzeSolution() {
        MazePathAnalyzer mazePathAnalyzer = new SimpleMazePathAnalyzer();
        this.pathStatistics = mazePathAnalyzer.analyze(this.maze, this.solution);
    }

    private void sendEmptyMaze() {
        this.writer.write(renderer.render(this.maze));
    }

    private void sendMazeWithStartAndEnd() {
        this.writer.write(renderer.render(this.maze, this.start, this.end));
    }

    private void sendSolvedMaze() {
        this.writer.write(renderer.render(this.maze, this.solution, this.start, this.end));
        this.writer.write(this.pathStatistics.constructString());
    }

    private int receiveHeight() {
        Integer height = reader.readHeight();
        while (height == null) {
            writer.write("Wrong height. It should be integer greater than 2.");
            height = reader.readHeight();
        }
        return height;
    }

    private int receiveWidth() {
        Integer width = reader.readWidth();
        while (width == null) {
            writer.write("Wrong width. It should be integer greater than 2.");
            width = reader.readWidth();
        }
        return width;
    }

    private Direction receiveStartDirection() {
        return receiveDirection(reader::readStartPlace, (_) -> false);
    }

    private Direction receiveEndDirection(Direction startDirection) {
        return receiveDirection(reader::readEndPlace, (direction) -> direction == startDirection);
    }

    private Direction receiveDirection(Supplier<Direction> supplier, Predicate<Direction>  predicate) {
        Direction direction = supplier.get();
        if (direction == null || predicate.test(direction)) {
            direction = picker.pick(Direction.class);
        }
        return direction;
    }

    private SolverType getRandomSolverType() {
        return appRandom.nextBoolean() ? SolverType.BFS : SolverType.DIJKSTRA;
    }
}
