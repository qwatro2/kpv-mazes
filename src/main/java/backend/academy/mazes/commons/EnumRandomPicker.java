package backend.academy.mazes.commons;

import java.util.Random;

public class EnumRandomPicker implements Randomizable<EnumRandomPicker> {
    private Random random;

    public <E extends Enum<E>> E pick(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        int randomIndex = random.nextInt(0, values.length);
        return values[randomIndex];
    }

    @Override
    public EnumRandomPicker setRandom(Random random) {
        this.random = random;
        return this;
    }
}
