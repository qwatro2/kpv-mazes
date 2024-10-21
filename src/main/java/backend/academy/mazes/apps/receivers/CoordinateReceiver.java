package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;
import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.readers.Reader;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.writers.Writer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CoordinateReceiver extends AbstractPickerReceiver implements Receiver {
    public CoordinateReceiver(Reader reader, Writer writer, EnumRandomPicker picker) {
        super(reader, writer, picker);
    }

    @Override
    public void receive(MazeAppState state) {
        Direction startDirection = receiveStartDirection();
        Direction endDirection = receiveEndDirection(startDirection);
        state.start(state.diConverter().directionToCoordinate(startDirection,
            state.height(), state.width()));
        state.end(state.diConverter().directionToCoordinate(endDirection,
            state.height(), state.width()));
    }

    private Direction receiveStartDirection() {
        return receiveDirection(reader::readStartPlace, (_) -> false);
    }

    private Direction receiveEndDirection(Direction startDirection) {
        return receiveDirection(reader::readEndPlace, (direction) -> direction == startDirection);
    }

    private Direction receiveDirection(Supplier<Direction> supplier, Predicate<Direction> predicate) {
        Direction direction = supplier.get();
        if (direction == null || predicate.test(direction)) {
            direction = picker.pick(Direction.class);
        }
        return direction;
    }
}
