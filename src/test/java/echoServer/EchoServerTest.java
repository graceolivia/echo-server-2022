package echoServer;

import echoServer.outputManagement.ClientWriterFactory;
import echoServer.socketManagement.Listenable;
import echoServer.socketManagement.Listener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {

    @Test
    void testClientSocketClosesWhenCloseSocketIsCalled() throws IOException {
        Socket clientSocket = new Socket();
        Listenable serverSocket = new Listener();
        EchoServer echoServer = new EchoServer();

        echoServer.closeSockets(clientSocket, serverSocket);

       assertTrue(clientSocket.isClosed());

    }


}

