package echoServer;

import java.io.IOException;
import java.net.Socket;

class MockClientReaderFactory implements ClientReadable {

    private final ClientReaderInterface bufferedReader;

    public MockClientReaderFactory(ClientReaderInterface bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public ClientReaderInterface makeReader(Socket clientSocket) throws IOException {
        return null;
    }
}