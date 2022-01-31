package echoServer;
import echoServer.outputManagement.ClientWriteable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class EchoerTest {
    // a mock for ClientSocket
    // a mock for ClientWriter
    @Test
    void testReadClientInputCallsBufferedReaderReadLine() throws IOException {
        Socket socket = new Socket();
        MockServerSocketWrapper mockServerSocket = new MockServerSocketWrapper(socket);
        MockClientWriterFactory mockClientWriterFactory = new MockClientWriterFactory();
        MockClientReader mockClientReader = new MockClientReader();
        Echoer echoer = new Echoer(mockServerSocket, mockClientReader, mockClientWriterFactory);


        Socket clientSocket = echoer.startServer();
        assertEquals(socket, clientSocket);
        assert(mockServerSocket.acceptHasBeenCalled);

    }

    @Test
    void testReadClientInputCallsPrintWriterPrintLn() throws IOException {

        Socket socket = new Socket();
        MockServerSocketWrapper mockServerSocket = new MockServerSocketWrapper(socket);
        MockClientWriterFactory mockClientWriterFactory = new MockClientWriterFactory();
        MockClientReader mockClientReader = new MockClientReader();

        Echoer echoer = new Echoer(mockServerSocket, mockClientReader, mockClientWriterFactory);
        //Socket clientSocket = echoer.startServer();
        ServerSocket server = new ServerSocket(8080);
        Socket theSocket = new Socket("localhost", 8080);
        echoer.readClientInput(theSocket);
        // assertEquals(socket, clientSocket);

        assert(mockClientWriterFactory.printWriterWasCalled);


    }
}
