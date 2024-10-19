package backend.academy.mazes.receivers;

import backend.academy.mazes.entities.Direction;
import backend.academy.mazes.entities.GeneratorType;
import backend.academy.mazes.entities.RendererType;
import backend.academy.mazes.entities.SolverType;

public interface Reader {
    Integer readHeight();
    Integer readWidth();
    GeneratorType readGeneratorType();
    RendererType readRendererType();
    Direction readStartPlace();
    Direction readEndPlace();
    SolverType readSolverType();
}
