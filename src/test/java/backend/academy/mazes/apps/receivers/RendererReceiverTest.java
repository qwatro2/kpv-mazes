package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.receivers.Receiver;
import backend.academy.mazes.receivers.RendererReceiver;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.writers.Writer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RendererReceiverTest {

    @Test
    void receive() {
        Reader reader = mock(Reader.class);
        when(reader.readRendererType()).thenReturn(RendererType.COLORFUL);

        Writer writer = mock(Writer.class);
        EnumRandomPicker picker = mock(EnumRandomPicker.class);

        MazeAppState state = new MazeAppState();

        Receiver receiver = new RendererReceiver(reader, writer, picker);
        receiver.receive(state);

        assertTrue(state.renderer().getLegend().contains("\uD83D\uDFE9"));
    }
}
