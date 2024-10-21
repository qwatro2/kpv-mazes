package backend.academy.mazes.app.receivers;

import backend.academy.mazes.app.MazeAppState;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.generators.PrimMazeGenerator;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.writers.Writer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeneratorReceiverTest {

    @Test
    void receive() {
        Reader reader = mock(Reader.class);
        when(reader.readGeneratorType()).thenReturn(GeneratorType.PRIM);

        Writer writer = mock(Writer.class);
        EnumRandomPicker picker = mock(EnumRandomPicker.class);

        MazeAppState state = new MazeAppState().height(3).width(3).diConverter(new DirectionCoordinateConverter());

        Receiver receiver = new GeneratorReceiver(reader, writer, picker);
        receiver.receive(state);

        assertInstanceOf(PrimMazeGenerator.class, state.generator());
    }
}
