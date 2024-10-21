package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.renderers.ConsoleMazeRenderer;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.writers.Writer;

public class RendererReceiver extends AbstractPickerReceiver implements Receiver{
    public RendererReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer, picker);
    }

    @Override
    public void receive(MazeAppState state) {
        RendererType rendererType = reader.readRendererType();
        if (rendererType == null) {
            rendererType = picker.pick(RendererType.class);
        }

        state.renderer(switch (rendererType) {
            case PLUSMINUS -> ConsoleMazeRenderer.getPlusMinusMazeRenderer();
            case COLORFUL -> ConsoleMazeRenderer.getColorfulMazeRenderer();
        });
    }
}
