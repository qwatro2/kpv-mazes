package backend.academy.mazes.apps.receivers;

import backend.academy.mazes.apps.MazeAppState;

public interface Receiver {
    void receive(MazeAppState state);
}
