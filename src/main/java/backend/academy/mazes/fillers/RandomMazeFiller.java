package backend.academy.mazes.fillers;

import backend.academy.mazes.commons.EnumRandomPicker;
import backend.academy.mazes.types.CellType;
import backend.academy.mazes.entities.Maze;

public class RandomMazeFiller implements MazeFiller {
    private final EnumRandomPicker picker;

    public RandomMazeFiller(EnumRandomPicker picker) {
        this.picker = picker;
    }

    @Override
    public void fill(Maze maze) {
        for (int row = 0; row < maze.height(); ++row) {
            for (int col = 0; col < maze.width(); ++col) {
                maze.cells()[row][col].cellType(picker.pick(CellType.class));
            }
        }
    }
}
