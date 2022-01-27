package echoServer;

import echoServer.socketManagement.Listenable;
import echoServer.socketManagement.Listener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ListenerTest {
    @Test
    void testStartServerReturnsAClientSocket() throws IOException {
        Listenable listener = new Listener();
        Socket clientSocket = listener.startServer();
        assertEquals(socket, clientSocket);
    }

    @Test
    void testCloseClosesTheServerSocket() throws IOException {
        Listenable listener = new Listener();
        Socket clientSocket = listener.startServer();
        clientSocket.close();
        assertTrue(clientSocket.isClosed());
    }
}
