package httpServer;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.routes.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static httpServer.http.Constants.CRLF;


import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import static httpServer.HttpServer.getRoutes;
import static org.junit.jupiter.api.Assertions.*;

public class ListenerAndResponderTest {

    Socket socket;
    ListenerAndResponder echoer;
    MockServerSocketWrapper mockServerSocket;
    MockBufferedReaderWrapper mockBufferedReader;
    MockPrintWriterWrapper mockPrintWriter;
    HTTPResponseBuilder responseBuilder;
    Map routes;

    @BeforeEach
    void setUp() throws IOException {
        String input = "GET / HTTP/1.1" + CRLF +
                "Accept: */*" + CRLF +
                "User-Agent: jUnit" + CRLF +
                "Host: localhost:5000" + CRLF + CRLF;
        socket = new Socket();
        routes = getRoutes();
        responseBuilder = new HTTPResponseBuilder();
        Router router = new Router(routes, responseBuilder);
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
    void testReadClientInputCallsPrintWriterPrint() throws IOException {
        String expectedResult = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + CRLF + "Content-Length: 0" + CRLF + "Content-Type: text/plain" + CRLF + CRLF;

        echoer.readClientInput(socket);

        assertTrue(mockBufferedReader.readWasCalled);
        System.out.println("RESULT: " + mockPrintWriter.printWasCalledWith);
        assertEquals(expectedResult, mockPrintWriter.printWasCalledWith);
    }

//    @Test
//    void testReadClientInputCallsPrintWriterPrint() throws IOException {
//        String expectedResult = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + CRLF + "Content-Length: 0" + CRLF + "Content-Type: text/plain" + CRLF + CRLF;
//
//        echoer.readClientInput(socket);
//
//        assertTrue(mockBufferedReader.readWasCalled);
//        System.out.println("RESULT: " + mockPrintWriter.printWasCalledWith);
//        assertEquals(expectedResult, mockPrintWriter.printWasCalledWith);
//    }

}
