package backend.academy.mazes.writers;

import backend.academy.mazes.commons.Printing;
import java.io.PrintStream;

public class ConsoleWriter implements Writer, Printing<ConsoleWriter> {
    private PrintStream printStream;

    @Override
    public void write(String s) {
        println(s);
    }

    @Override
    public ConsoleWriter setPrintStream(PrintStream printStream) {
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
}
