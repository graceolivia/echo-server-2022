package echoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReaderFactory implements ClientReadable {
    public ClientReaderInterface makeReader(Socket clientSocket) throws IOException {
        ClientReaderInterface  bufferedReaderWrapper = new BufferedReaderWrapper(clientSocket);
       return bufferedReaderWrapper;
    }
}
