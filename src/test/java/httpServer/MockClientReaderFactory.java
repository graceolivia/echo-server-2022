package httpServer;
import java.io.IOException;
import java.net.Socket;

class MockClientReaderFactory implements ClientReadableFactory {

    private final ClientReadable bufferedReader;

    public MockClientReaderFactory(ClientReadable bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public ClientReadable makeReader(Socket clientSocket) throws IOException {
        return bufferedReader;
    }
}