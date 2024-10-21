package backend.academy.mazes.fillers;

import backend.academy.mazes.commons.Randomizable;
import backend.academy.mazes.types.CellType;
import backend.academy.mazes.entities.Maze;
import java.util.Random;

public class RandomMazeFiller implements MazeFiller, Randomizable<RandomMazeFiller> {
    private Random random;

    @Override
    public void fill(Maze maze) {
        for (int row = 0; row < maze.height(); ++row) {
            for (int col = 0; col < maze.width(); ++col) {
                maze.cells()[row][col].cellType(getRandomCellType());
            }
        }
    }

    private CellType getRandomCellType() {
        CellType[] cellTypes = CellType.values();
        int cellTypeIndex = random.nextInt(0, cellTypes.length);
        return cellTypes[cellTypeIndex];
    }

    @Override
    public RandomMazeFiller setRandom(Random random) {
        this.random = random;
        return this;
    }
}
