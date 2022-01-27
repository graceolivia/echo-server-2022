package echoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public interface ClientReadable {

    public BufferedReader makeReader(Socket clientSocket) throws IOException;
}
