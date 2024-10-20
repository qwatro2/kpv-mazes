package backend.academy;

import backend.academy.mazes.app.App;
import backend.academy.mazes.app.MazeApp;
import backend.academy.mazes.waiters.ConsoleWaiter;
import backend.academy.mazes.readers.ConsoleReader;
import backend.academy.mazes.writers.ConsoleWriter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        App mazeApp = new MazeApp(
            new ConsoleReader().setPrintStream(System.out).setInputStream(System.in),
            new ConsoleWriter().setPrintStream(System.out),
            new ConsoleWaiter().setPrintStream(System.out).setInputStream(System.in)
        );
        mazeApp.run();
    }
}
