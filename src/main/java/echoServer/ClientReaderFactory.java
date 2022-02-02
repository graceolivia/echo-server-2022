package echoServer;
import java.io.IOException;
import java.net.Socket;

public class ClientReaderFactory implements ClientReadableFactory {
    public ClientReadable makeReader(Socket clientSocket) throws IOException {
        ClientReadable bufferedReaderWrapper = new BufferedReaderWrapper(clientSocket);
       return bufferedReaderWrapper;
    }
}
