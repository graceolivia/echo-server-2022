package httpServer;

import java.io.IOException;
import java.net.Socket;

public interface ClientReadableFactory {

    public ClientReadable makeReader(Socket clientSocket) throws IOException;

}
