package backend.academy.mazes.app.receivers;

import backend.academy.mazes.app.MazeAppState;

public interface Receiver {
    void receive(MazeAppState state);
}
