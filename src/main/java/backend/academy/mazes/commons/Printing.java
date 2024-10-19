package backend.academy.mazes.commons;

import java.io.PrintStream;

public interface Printing<T> {
    T setPrintStream(PrintStream printStream);
    <R> void println(R object);
    <R> void print(R object);
}
