package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.writers.Writer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SizeReceiverTest {

    @Test
    void receive() {
        Reader reader = mock(Reader.class);
        when(reader.readHeight()).thenReturn(10);
        when(reader.readWidth()).thenReturn(30);

        Writer writer = mock(Writer.class);

        MazeAppState state = new MazeAppState();
        Receiver receiver = new SizeReceiver(reader, writer);
        receiver.receive(state);

        assertEquals(10, state.height());
        assertEquals(30, state.width());
    }
}
