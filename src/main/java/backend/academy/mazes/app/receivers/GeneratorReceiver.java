package backend.academy.mazes.app.receivers;

import backend.academy.mazes.app.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.generators.KruskalMazeGenerator;
import backend.academy.mazes.generators.PrimMazeGenerator;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.writers.Writer;

public class GeneratorReceiver extends AbstractReceiver implements Receiver {
    private final EnumRandomPicker picker;
    public GeneratorReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer);
        this.picker = picker;
    }

    @Override
    public void receive(MazeAppState state) {
        GeneratorType generatorType = reader.readGeneratorType();
        if (generatorType == null) {
            generatorType = picker.pick(GeneratorType.class);
        }

        state.generator(switch (generatorType) {
            case PRIM -> new PrimMazeGenerator().setRandom(state.generatorRandom());
            case KRUSKAL -> new KruskalMazeGenerator().setRandom(state.generatorRandom());
        });
    }
}
