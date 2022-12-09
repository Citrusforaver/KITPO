import java.io.IOException;

public interface Action<Object> {
    void toDo(Object v) throws IOException;
}

