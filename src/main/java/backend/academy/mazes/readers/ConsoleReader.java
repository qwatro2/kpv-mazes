package backend.academy.mazes.readers;

import backend.academy.mazes.commons.Printing;
import backend.academy.mazes.commons.Reading;
import backend.academy.mazes.types.Direction;
import backend.academy.mazes.types.GeneratorType;
import backend.academy.mazes.types.RendererType;
import backend.academy.mazes.types.SolverType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.function.Predicate;

public class ConsoleReader implements Reader, Reading<ConsoleReader>, Printing<ConsoleReader> {
    private PrintStream printStream;
    private BufferedReader bufferedReader;

    @Override
    public Integer readHeight() {
        return readInteger("Enter the height of the maze: ", h -> h > 2);
    }

    @Override
    public Integer readWidth() {
        return readInteger("Enter the width of the maze: ", w -> w > 2);
    }

    @Override
    public GeneratorType readGeneratorType() {
        println("""
            Choose maze generator:
            [1] Prim
            [2] Kruskal
            [other] Random""");
        String answer = readLine();
        return processStringToGenerationType(answer);
    }

    @Override
    public RendererType readRendererType() {
        println("""
            Choose maze renderer:
            [1] PlusMinus
            [2] Colorful
            [other] Random""");
        String answer = readLine();
        return processStringToRendererType(answer);
    }

    @Override
    public Direction readStartPlace() {
        return readPlace("Choose place for start of maze:");
    }

    @Override
    public Direction readEndPlace() {
        return readPlace("Choose place for end of maze:");
    }

    @Override
    public SolverType readSolverType() {
        println("""
            Choose maze solver:
            [1] BFS
            [2] Dijkstra
            [other] Random""");
        String answer = readLine();
        return processStringToSolverType(answer);
    }

    private Integer readInteger(String prompt, Predicate<Integer> checkInteger) {
        print(prompt);
        String answer = readLine();
        return processStringToInteger(answer, checkInteger);
    }

    private Direction readPlace(String prompt) {
        println(prompt);
        println("""
            [1] North           +---+---+---+
            [2] North-East      | NW| N |NE |
            [3] East            +---+---+---+
            [4] South-East      | W | X | E |
            [5] South           +---+---+---+
            [6] South-West      | SW| S |SE |
            [7] West            +---+---+---+
            [8] North-West
            [other] Random""");
        String answer = readLine();
        return processStringToDirection(answer);
    }

    private Integer processStringToInteger(String s, Predicate<Integer> checkInteger) {
        if (s == null) {
            return null;
        }
        try {
            Integer converted = Integer.parseInt(s);
            return checkInteger.test(converted) ? converted : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private GeneratorType processStringToGenerationType(String s) {
        return switch (s) {
            case "1" -> GeneratorType.PRIM;
            case "2" -> GeneratorType.KRUSKAL;
            default -> null;
        };
    }

    private RendererType processStringToRendererType(String s) {
        return switch (s) {
            case "1" -> RendererType.PLUSMINUS;
            case "2" -> RendererType.COLORFUL;
            default -> null;
        };
    }

    private Direction processStringToDirection(String s) {
        return switch (s) {
            case "1" -> Direction.NORTH;
            case "2" -> Direction.NORTHEAST;
            case "3" -> Direction.EAST;
            case "4" -> Direction.SOUTHEAST;
            case "5" -> Direction.SOUTH;
            case "6" -> Direction.SOUTHWEST;
            case "7" -> Direction.WEST;
            case "8" -> Direction.NORTHWEST;
            default -> null;
        };
    }

    private SolverType processStringToSolverType(String s) {
        return switch (s) {
            case "1" -> SolverType.BFS;
            case "2" -> SolverType.DIJKSTRA;
            default -> null;
        };
    }

    @Override
    public ConsoleReader setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
        return this;
    }

    @Override
    public <R> void println(R object) {
        this.printStream.println(object);
    }

    @Override
    public <R> void print(R object) {
        this.printStream.print(object);
    }

    @Override
    public ConsoleReader setInputStream(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return this;
    }

    @Override
    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException exception) {
            return null;
        }
    }
}
