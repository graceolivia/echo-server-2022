package echoServer;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public interface Listenable {
    public Socket accept() throws IOException;

    public void bind(int port) throws IOException;

    public void close();
}

