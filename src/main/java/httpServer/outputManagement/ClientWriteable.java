package httpServer.outputManagement;

import java.io.IOException;

public interface ClientWriteable {

    public void print(String message) throws IOException;

    public void close() throws IOException;
}