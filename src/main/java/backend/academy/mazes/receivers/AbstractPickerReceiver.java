package backend.academy.mazes.receivers;

import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.writers.Writer;

public class AbstractPickerReceiver extends AbstractReceiver {
    protected final EnumRandomPicker picker;
    protected AbstractPickerReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer);
        this.picker = picker;
    }
}
