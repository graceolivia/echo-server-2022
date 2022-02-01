package echoServer;
import java.io.IOException;
import java.net.Socket;

public interface ClientReadable {

    public ClientReaderInterface makeReader(Socket clientSocket) throws IOException;
}
