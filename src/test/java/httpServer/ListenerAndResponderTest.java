package httpServer;
import httpServer.http.constants;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.routes.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ListenerAndResponderTest {

    Socket socket;
    ListenerAndResponder echoer;
    MockServerSocketWrapper mockServerSocket;
    MockBufferedReaderWrapper mockBufferedReader;
    MockPrintWriterWrapper mockPrintWriter;

    @BeforeEach
    void setUp() throws IOException {
        String input = "Accept: */*" + constants.CRLF +
                "User-Agent: jUnit" + constants.CRLF +
                "Host: localhost:5000" + constants.CRLF +
                "GET / HTTP/1.1" + constants.CRLF + constants.CRLF;
        socket = new Socket();
        Router router = new Router(HttpServer.getRoutes());
        mockServerSocket = new MockServerSocketWrapper(socket);
        mockPrintWriter =  new MockPrintWriterWrapper();
        mockBufferedReader = new MockBufferedReaderWrapper(input);
        ClientWriteableFactory mockClientWriterFactory = new MockClientWriterFactory(mockPrintWriter);
        ClientReadableFactory mockClientReaderFactory = new MockClientReaderFactory(mockBufferedReader);
        echoer = new ListenerAndResponder(mockServerSocket, mockClientReaderFactory, mockClientWriterFactory, router);
    }

    @Test
    void testThatStartServerReturnsASocket() throws IOException {

        Socket clientSocket = echoer.startServer();

        assertEquals(socket, clientSocket);
        assert(mockServerSocket.acceptHasBeenCalled);

    }

    @Test
    void testReadClientInputCallsPrintWriterPrintLn() throws IOException {
        String expectedResult = StatusCodes.PAGE_NOT_FOUND.httpResponse;

        echoer.readClientInput(socket);

        assertTrue(mockBufferedReader.readLineWasCalled);
        assertEquals(expectedResult, mockPrintWriter.printlnWasCalledWith);
    }
}
