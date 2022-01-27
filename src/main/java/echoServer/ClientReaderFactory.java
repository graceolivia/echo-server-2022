package echoServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReaderFactory implements ClientReadable {
    public BufferedReader makeReader(Socket clientSocket) throws IOException {
       return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
}
