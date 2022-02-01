package echoServer.outputManagement;
import java.io.IOException;
import java.net.Socket;

public class ClientWriterFactory implements ClientWriteable {

    public ClientWriterFactory() {
    }

    public ClientWriterInterface makePrinter(Socket clientSocket) throws IOException {
        return new PrintWriterWrapper(clientSocket);

    }


}
