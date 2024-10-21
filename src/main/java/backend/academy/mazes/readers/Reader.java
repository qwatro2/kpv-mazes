package backend.academy.mazes.readers;

import backend.academy.mazes.types.Direction;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.types.SolverType;

public interface Reader {
    Integer readHeight();
    Integer readWidth();
    GeneratorType readGeneratorType();
    RendererType readRendererType();
    Direction readStartPlace();
    Direction readEndPlace();
    SolverType readSolverType();
}
