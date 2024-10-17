package backend.academy.mazes.entities;

import backend.academy.mazes.commons.Direction;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter public class Cell {
    private final int row;
    private final int col;
    private boolean rightWall;
    private boolean bottomWall;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.rightWall = false;
        this.bottomWall = false;
    }

    public Cell setWallsAndPassages(int height, int width) {
       if (this.row == height - 1) {
           this.bottomWall = true;
       }
       if (this.col == width - 1) {
           this.rightWall = true;
       }
       return this;
    }
}
