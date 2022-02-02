package echoServer;

import echoServer.socketManagement.ServerSocketInterface;
import echoServer.socketManagement.ServerSocketWrapper;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {

    @Test
    void testClientSocketClosesWhenCloseSocketIsCalled() throws IOException {
        Socket clientSocket = new Socket();
        ServerSocketInterface serverSocket = new ServerSocketWrapper();
        EchoServer echoServer = new EchoServer();

        echoServer.closeSockets(clientSocket, serverSocket);

       assertTrue(clientSocket.isClosed());

    }


}

