package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.DirectionCoordinateConverter;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.entities.Coordinate;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.writers.Writer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoordinateReceiverTest {
    @Test
    void receive() {
        Reader reader = mock(Reader.class);
        when(reader.readStartPlace()).thenReturn(Direction.NORTH);
        when(reader.readEndPlace()).thenReturn(Direction.SOUTH);

        Writer writer = mock(Writer.class);
        EnumRandomPicker picker = mock(EnumRandomPicker.class);

        MazeAppState state = new MazeAppState().height(3).width(3).diConverter(new DirectionCoordinateConverter());

        Receiver receiver = new CoordinateReceiver(reader, writer, picker);
        receiver.receive(state);

        assertEquals(new Coordinate(0, 1), state.start());
        assertEquals(new Coordinate(2, 1), state.end());
    }
}
