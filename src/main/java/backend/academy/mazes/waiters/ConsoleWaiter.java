package backend.academy.mazes.waiters;

import backend.academy.mazes.commons.Printing;
import backend.academy.mazes.commons.Reading;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ConsoleWaiter implements Waiter, Reading<ConsoleWaiter>, Printing<ConsoleWaiter> {
    private PrintStream printStream;
    private BufferedReader bufferedReader;

    @Override
    public void waiting(String prompt) {
        printStream.print(prompt);
        try {
            int _ = this.bufferedReader.read();
        } catch (IOException _) {

        }
    }

    @Override
    public ConsoleWaiter setPrintStream(PrintStream printStream) {
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
    public ConsoleWaiter setInputStream(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return this;
    }

    @Override
    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException _) {
            return null;
        }
    }
}
