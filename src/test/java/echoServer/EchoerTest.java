package echoServer;
import echoServer.http.StatusCode;
import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.routes.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class EchoerTest {

    Socket socket;
    Echoer echoer;
    MockServerSocketWrapper mockServerSocket;
    MockBufferedReaderWrapper mockBufferedReader;
    MockPrintWriterWrapper mockPrintWriter;

    @BeforeEach
    void setUp() throws IOException {
        String input = "Accept: */*" + CRLF.CRLF +
                "User-Agent: jUnit" + CRLF.CRLF +
                "Host: localhost:5000" + CRLF.CRLF +
                "GET / HTTP/1.1" + CRLF.CRLF + CRLF.CRLF;
        socket = new Socket();
        Router router = new Router(EchoServer.getRoutes());
        mockServerSocket = new MockServerSocketWrapper(socket);
        mockPrintWriter =  new MockPrintWriterWrapper();
        mockBufferedReader = new MockBufferedReaderWrapper(input);
        ClientWriteableFactory mockClientWriterFactory = new MockClientWriterFactory(mockPrintWriter);
        ClientReadableFactory mockClientReaderFactory = new MockClientReaderFactory(mockBufferedReader);
        echoer = new Echoer(mockServerSocket, mockClientReaderFactory, mockClientWriterFactory, router);
    }

    @Test
    void testThatStartServerReturnsASocket() throws IOException {

        Socket clientSocket = echoer.startServer();

        assertEquals(socket, clientSocket);
        assert(mockServerSocket.acceptHasBeenCalled);

    }

    @Test
    void testReadClientInputCallsPrintWriterPrintLn() throws IOException {
        String expectedResult = StatusCode.PAGE_NOT_FOUND.httpResponse;

        echoer.readClientInput(socket);

        assertTrue(mockBufferedReader.readLineWasCalled);
        assertEquals(expectedResult, mockPrintWriter.printlnWasCalledWith);
    }
}
