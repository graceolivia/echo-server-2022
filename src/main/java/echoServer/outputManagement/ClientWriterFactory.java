package echoServer.outputManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWriterFactory implements ClientWriteable {

    public ClientWriterFactory() {
    }

    public PrintWriter makePrinter(Socket clientSocket) throws IOException {
        return new PrintWriter(clientSocket.getOutputStream(),true);
    }


}
