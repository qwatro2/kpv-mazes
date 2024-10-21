package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.CoordinateIndexConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.commons.ParentsPathConverter;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.solvers.BfsMazeSolver;
import backend.academy.mazes.solvers.DijkstraMazeSolver;
import backend.academy.mazes.types.SolverType;
import backend.academy.mazes.writers.Writer;

public class SolverReceiver extends AbstractPickerReceiver implements Receiver {
    public SolverReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer, picker);
    }

    @Override
    public void receive(MazeAppState state) {
        CoordinateIndexConverter coordinateIndexConverter = new CoordinateIndexConverter();
        ParentsPathConverter parentsPathConverter = new ParentsPathConverter(coordinateIndexConverter);

        SolverType solverType = reader.readSolverType();
        if (solverType == null) {
            solverType = picker.pick(SolverType.class);
        }

        state.solver(switch (solverType) {
            case BFS -> new BfsMazeSolver(coordinateIndexConverter, parentsPathConverter);
            case DIJKSTRA -> new DijkstraMazeSolver(coordinateIndexConverter, parentsPathConverter);
        });
    }
}
