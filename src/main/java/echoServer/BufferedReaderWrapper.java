package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class BufferedReaderWrapper implements ClientReaderInterface {

    public Socket clientSocket;
    public BufferedReader bufferedReader;

    public BufferedReaderWrapper(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }
}
