package echoServer.outputManagement;
import java.io.IOException;
import java.net.Socket;

public class ClientWriterFactory implements ClientWriteableFactory {

    public ClientWriteable makePrinter(Socket clientSocket) throws IOException {
        return new PrintWriterWrapper(clientSocket);
    }

}
