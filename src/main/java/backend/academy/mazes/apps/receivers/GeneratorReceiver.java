package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.generators.KruskalMazeGenerator;
import backend.academy.mazes.generators.PrimMazeGenerator;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.writers.Writer;

public class GeneratorReceiver extends AbstractPickerReceiver implements Receiver {
    public GeneratorReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer, picker);
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
