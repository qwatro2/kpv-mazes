package backend.academy.mazes.app.receivers;

import backend.academy.mazes.app.MazeAppState;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.writers.Writer;

public class SizeReceiver implements Receiver {
    private final Reader reader;
    private final Writer writer;

    public SizeReceiver(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
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
