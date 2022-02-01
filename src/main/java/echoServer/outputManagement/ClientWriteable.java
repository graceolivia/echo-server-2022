package echoServer.outputManagement;

import java.io.IOException;
import java.net.Socket;

public interface ClientWriteable {

    public ClientWriterInterface makePrinter(Socket clientSocket) throws IOException;


}
