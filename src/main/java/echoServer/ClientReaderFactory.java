package echoServer;
import java.io.IOException;
import java.net.Socket;

public class ClientReaderFactory implements ClientReadable {
    public ClientReaderInterface makeReader(Socket clientSocket) throws IOException {
        ClientReaderInterface  bufferedReaderWrapper = new BufferedReaderWrapper(clientSocket);
       return bufferedReaderWrapper;
    }
}
