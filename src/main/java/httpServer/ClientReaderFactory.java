package httpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReaderFactory implements ClientReadableFactory {

    public ClientReadable makeReader(Socket clientSocket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ClientReadable bufferedReaderWrapper = new BufferedReaderWrapper(bufferedReader);
        return bufferedReaderWrapper;
    }

}
