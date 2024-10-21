package backend.academy.mazes.apps;

import backend.academy.mazes.analyzers.MazePathAnalyzer;
import backend.academy.mazes.analyzers.SimpleMazePathAnalyzer;
import backend.academy.mazes.apps.receivers.CoordinateReceiver;
import backend.academy.mazes.apps.receivers.GeneratorReceiver;
import backend.academy.mazes.apps.receivers.Receiver;
import backend.academy.mazes.apps.receivers.RendererReceiver;
import backend.academy.mazes.apps.receivers.SizeReceiver;
import backend.academy.mazes.apps.receivers.SolverReceiver;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.fillers.RandomMazeFiller;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.waiters.Waiter;
import backend.academy.mazes.writers.Writer;
import java.util.Random;

public class MazeApp implements App {
    private final Writer writer;
    private final Waiter waiter;

    private final Receiver sizeReceiver;
    private final Receiver generatorReceiver;
    private final Receiver rendererReceiver;
    private final Receiver coordinateReceiver;
    private final Receiver solverReceiver;

    private final MazeAppState state;

    public MazeApp(Reader reader, Writer writer, Waiter waiter) {
        this.writer = writer;
        this.waiter = waiter;

        EnumRandomPicker picker = new EnumRandomPicker().setRandom(new Random());

        this.state = new MazeAppState()
            .generatorRandom(new Random())
            .diConverter(new DirectionCoordinateConverter())
            .filler(new RandomMazeFiller(picker));

        this.sizeReceiver = new SizeReceiver(reader, this.writer);
        this.generatorReceiver = new GeneratorReceiver(reader, this.writer, picker);
        this.rendererReceiver = new RendererReceiver(reader, this.writer, picker);
        this.coordinateReceiver = new CoordinateReceiver(reader, this.writer, picker);
        this.solverReceiver = new SolverReceiver(reader, this.writer, picker);
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

        coordinateReceiver.receive(state);
        sendMazeWithStartAndEnd();
        waitEnteringButton();

        solverReceiver.receive(state);
        solveMaze();
        analyzeSolution();
        sendSolvedMaze();
    }

    private void waitEnteringButton() {
        this.waiter.waiting("Enter any button to continue...");
    }

    private void generateMaze() {
        this.state.maze(this.state.generator().generate(this.state.height(), this.state.width()));
    }

    private void fillMaze() {
        state.filler().fill(this.state.maze());
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
        sendLegend();
    }

    private void sendMazeWithStartAndEnd() {
        this.writer.write(this.state.renderer().render(this.state.maze(), this.state.start(), this.state.end()));
        sendLegend();
    }

    private void sendSolvedMaze() {
        this.writer.write(this.state.renderer().render(this.state.maze(), this.state.solution(),
            this.state.start(), this.state.end()));
        sendLegend();
        this.writer.write(this.state.pathStatistics().constructString());
    }

    private void sendLegend() {
        this.writer.write(this.state.renderer().getLegend());
    }
}
