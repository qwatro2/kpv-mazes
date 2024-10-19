package backend.academy.mazes.commons;

import java.io.InputStream;

public interface Reading<T> {
    T setInputStream(InputStream inputStream);
    String readLine();
}
