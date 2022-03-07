package httpServer.outputManagement;

import java.io.IOException;
import java.net.Socket;

public interface ClientWriteableFactory {

    public ClientWriteable makePrinter(Socket clientSocket) throws IOException;

}
