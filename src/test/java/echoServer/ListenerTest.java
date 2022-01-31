////SCHEDULED FOR DEMOLATION
//
//package echoServer;
//
//import echoServer.socketManagement.ServerSocketInterface;
//import echoServer.socketManagement.ServerSocketWrapper;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.net.Socket;
//
//import static echoServer.MockClientWriterFactory.socket;
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;
//
//public class ListenerTest {
//    @Test
//    void testStartServerReturnsAClientSocket() throws IOException {
//
//        ServerSocketInterface serverSocket = new ServerSocketWrapper();
//        Socket clientSocket = serverSocket.accept();
//        assertInstanceOf(Socket.class, clientSocket);
//    }
//
//    @Test
//    void testCloseClosesTheServerSocket() throws IOException {
////        Listenable listener = new Listener();
////        Socket clientSocket = listener.startServer();
////        clientSocket.close();
////        assertTrue(clientSocket.isClosed());
//    }
//}
