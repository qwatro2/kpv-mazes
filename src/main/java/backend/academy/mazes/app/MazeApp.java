package backend.academy.mazes.app;

import backend.academy.mazes.analyzers.MazePathAnalyzer;
import backend.academy.mazes.analyzers.SimpleMazePathAnalyzer;
import backend.academy.mazes.app.receivers.GeneratorReceiver;
import backend.academy.mazes.app.receivers.Receiver;
import backend.academy.mazes.app.receivers.RendererReceiver;
import backend.academy.mazes.app.receivers.SizeReceiver;
import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.commons.ParentsPathConverter;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.types.SolverType;
import backend.academy.mazes.fillers.MazeFiller;
import backend.academy.mazes.fillers.RandomMazeFiller;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.renderers.ConsoleMazeRenderer;
import backend.academy.mazes.solvers.BfsMazeSolver;
import backend.academy.mazes.solvers.DijkstraMazeSolver;
import backend.academy.mazes.waiters.Waiter;
import backend.academy.mazes.writers.Writer;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MazeApp implements App {
    private final Reader reader;
    private final Writer writer;
    private final Waiter waiter;

    private final EnumRandomPicker picker;
    private final CoordinateIndexConverter coordinateIndexConverter;
    private final ParentsPathConverter parentsPathConverter;
    private final DirectionCoordinateConverter directionCoordinateConverter;

    private final Receiver sizeReceiver;
    private final Receiver generatorReceiver;
    private final Receiver rendererReceiver;

    private final MazeAppState state;

    public MazeApp(Reader reader, Writer writer, Waiter waiter) {
        this.reader = reader;
        this.writer = writer;
        this.waiter = waiter;
        this.picker = new EnumRandomPicker().setRandom(new Random());
        this.coordinateIndexConverter = new CoordinateIndexConverter();
        this.parentsPathConverter = new ParentsPathConverter(this.coordinateIndexConverter);
        this.directionCoordinateConverter = new DirectionCoordinateConverter();
        this.state = new MazeAppState().generatorRandom(new Random(142857));
        this.sizeReceiver = new SizeReceiver(this.reader, this.writer);
        this.generatorReceiver = new GeneratorReceiver(this.reader, this.writer, this.picker);
        this.rendererReceiver = new RendererReceiver(this.reader, this.writer, this.picker);
    }

    @Override
    public void run() {
        sizeReceiver.receive(state);
        generatorReceiver.receive(state);
        rendererReceiver.receive(state);

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

    private void receiveCoordinates() {
        Direction startDirection = receiveStartDirection();
        Direction endDirection = receiveEndDirection(startDirection);
        this.state.start(directionCoordinateConverter.directionToCoordinate(startDirection,
            this.state.height(), this.state.width()));
        this.state.end(directionCoordinateConverter.directionToCoordinate(endDirection,
            this.state.height(), this.state.width()));
    }

    private void receiveMazeSolver() {
        SolverType solverType = reader.readSolverType();
        if (solverType == null) {
            solverType = picker.pick(SolverType.class);
        }

        this.state.solver(switch (solverType) {
            case BFS -> new BfsMazeSolver(this.coordinateIndexConverter, this.parentsPathConverter);
            case DIJKSTRA -> new DijkstraMazeSolver(this.coordinateIndexConverter, this.parentsPathConverter);
        });
    }

    private void generateMaze() {
        this.state.maze(this.state.generator().generate(this.state.height(), this.state.width()));
    }

    private void fillMaze() {
        MazeFiller mazeFiller = new RandomMazeFiller().setRandom(new Random());
        mazeFiller.fill(this.state.maze());
    }

    private void solveMaze() {
        this.state.solution(this.state.solver().solve(this.state.maze(), this.state.start(), this.state.end()));
    }

    private void analyzeSolution() {
        MazePathAnalyzer mazePathAnalyzer = new SimpleMazePathAnalyzer();
        this.state.pathStatistics(mazePathAnalyzer.analyze(this.state.maze(), this.state.solution()));
    }

    private void sendEmptyMaze() {
        this.writer.write(this.state.renderer().render(this.state.maze()));
    }

    private void sendMazeWithStartAndEnd() {
        this.writer.write(this.state.renderer().render(this.state.maze(), this.state.start(), this.state.end()));
    }

    private void sendSolvedMaze() {
        this.writer.write(this.state.renderer().render(this.state.maze(), this.state.solution(),
            this.state.start(), this.state.end()));
        this.writer.write(this.state.pathStatistics().constructString());
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
}
