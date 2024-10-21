package backend.academy.mazes.app.receivers;

import backend.academy.mazes.app.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.solvers.BfsMazeSolver;
import backend.academy.mazes.types.SolverType;
import backend.academy.mazes.writers.Writer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SolverReceiverTest {

    @Test
    void receive() {
        Reader reader = mock(Reader.class);
        when(reader.readSolverType()).thenReturn(SolverType.BFS);

        Writer writer = mock(Writer.class);
        EnumRandomPicker picker = mock(EnumRandomPicker.class);

        MazeAppState state = new MazeAppState();
        Receiver receiver = new SolverReceiver(reader, writer, picker);
        receiver.receive(state);

        assertInstanceOf(BfsMazeSolver.class, state.solver());
    }
}
