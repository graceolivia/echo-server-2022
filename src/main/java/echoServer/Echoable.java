package echoServer;
import java.io.IOException;
import java.net.Socket;

public interface Echoable {
    public Socket startServer() throws IOException;

    public boolean readClientInput() throws IOException;

}
