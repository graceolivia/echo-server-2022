package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

class MockClientReader implements ClientReadable {
    @Override
    public BufferedReader makeReader(Socket clientSocket) throws IOException {
        return null;
    }
}