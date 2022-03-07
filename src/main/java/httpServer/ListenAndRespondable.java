package httpServer;

import java.io.IOException;
import java.net.Socket;

public interface ListenAndRespondable {
    public Socket startServer() throws IOException;

    public void readClientInput(Socket clientSocket) throws IOException;

    public Socket keepListening() throws IOException;
}
