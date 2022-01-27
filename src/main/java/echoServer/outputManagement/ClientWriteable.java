package echoServer.outputManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public interface ClientWriteable {

    public PrintWriter makePrinter(Socket clientSocket) throws IOException;


}
