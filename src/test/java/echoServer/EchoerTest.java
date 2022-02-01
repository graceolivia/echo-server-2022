package echoServer;
import echoServer.outputManagement.ClientWriteable;
import echoServer.outputManagement.ClientWriterInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class EchoerTest {
    // a mock for ClientSocket
    // a mock for ClientWriter
    @Test
    void testReadClientInputCallsBufferedReaderReadLine() throws IOException {
//        Socket socket = new Socket();
//        MockServerSocketWrapper mockServerSocket = new MockServerSocketWrapper(socket);
//        MockClientWriterFactory mockClientWriterFactory = new MockClientWriterFactory();
//        MockClientReaderFactory mockClientReader = new MockClientReaderFactory();
//        Echoer echoer = new Echoer(mockServerSocket, mockClientReader, mockClientWriterFactory);
//
//
//        Socket clientSocket = echoer.startServer();
//        assertEquals(socket, clientSocket);
//        assert(mockServerSocket.acceptHasBeenCalled);

    }

    @Test
    void testReadClientInputCallsPrintWriterPrintLn() throws IOException {
    String input = "string";
    Socket socket = new Socket();
    MockServerSocketWrapper mockServerSocket = new MockServerSocketWrapper(socket);
    MockPrintWriterWrapper mockPrintWriter =  new MockPrintWriterWrapper();
    MockBufferedReaderWrapper mockBufferedReader = new MockBufferedReaderWrapper(input);
    ClientWriteable mockClientWriterFactory = new MockClientWriterFactory(mockPrintWriter);
    ClientReadable mockClientReaderFactory = new MockClientReaderFactory(mockBufferedReader);
    Echoer echoer = new Echoer(mockServerSocket, mockClientReaderFactory, mockClientWriterFactory);

    echoer.readClientInput(socket);

    assertTrue(mockBufferedReader.readLineWasCalled);
    assertEquals(mockPrintWriter.printlnWasCalledWith, input);
//        Socket socket = new Socket();
//        MockServerSocketWrapper mockServerSocket = new MockServerSocketWrapper(socket);
//        MockClientWriterFactory mockClientWriterFactory = new MockClientWriterFactory();
//        MockClientReader mockClientReader = new MockClientReader();
//
//        Echoer echoer = new Echoer(mockServerSocket, mockClientReader, mockClientWriterFactory);
//        //Socket clientSocket = echoer.startServer();
//        ServerSocket server = new ServerSocket(8080);
//        Socket theSocket = new Socket("localhost", 8080);
//        echoer.readClientInput(theSocket);
//        // assertEquals(socket, clientSocket);
//
//        assert(mockClientWriterFactory.printWriterWasCalled);


    }
}
