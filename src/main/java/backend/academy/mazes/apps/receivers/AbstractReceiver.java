package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.writers.Writer;

public abstract class AbstractReceiver {
    protected final Reader reader;
    protected final Writer writer;

    protected AbstractReceiver(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }
}
