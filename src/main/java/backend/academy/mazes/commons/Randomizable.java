package backend.academy.mazes.commons;

import java.util.Random;

public interface Randomizable<T> {
    Random getRandom();
    T setRandom(Random random);
}
