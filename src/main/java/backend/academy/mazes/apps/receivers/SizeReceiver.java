package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.writers.Writer;

public class SizeReceiver extends AbstractReceiver implements Receiver {
    public SizeReceiver(Reader reader, Writer writer) {
        super(reader, writer);
    }

    @Override
    public void receive(MazeAppState state) {
        state.height(receiveHeight());
        state.width(receiveWidth());
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
}
