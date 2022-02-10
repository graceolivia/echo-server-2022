package httpServer;

import httpServer.socketManagement.ServerSocketInterface;
import httpServer.socketManagement.ServerSocketWrapper;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void testClientSocketClosesWhenCloseSocketIsCalled() throws IOException {
        Socket clientSocket = new Socket();
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        HttpServer httpServer = new HttpServer();

        httpServer.closeSockets(clientSocket, serverSocket);

        assertTrue(clientSocket.isClosed());
    }
}

