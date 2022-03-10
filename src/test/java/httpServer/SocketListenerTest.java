package httpServer;
import httpServer.http.response.HTTPResponseBuilder;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.routes.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import httpServer.RequestParseable;
import httpServer.RequestParser;
import static httpServer.http.Constants.CRLF;


import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import static httpServer.HttpServer.getRoutes;
import static org.junit.jupiter.api.Assertions.*;

public class SocketListenerTest {

    Socket socket;
    SocketListener socketListener;
    MockServerSocketWrapper mockServerSocket;
    MockBufferedReaderWrapper mockBufferedReader;
    MockPrintWriterWrapper mockPrintWriter;
    HTTPResponseBuilder responseBuilder;
    MockRequestParser mockRequestParser;
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
        RequestParser requestParser = new RequestParser();
        mockRequestParser = new MockRequestParser();
        ClientWriteableFactory mockClientWriterFactory = new MockClientWriterFactory(mockPrintWriter);
        ClientReadableFactory mockClientReaderFactory = new MockClientReaderFactory(mockBufferedReader);
        socketListener = new SocketListener(mockServerSocket, mockClientReaderFactory, mockClientWriterFactory, router, mockRequestParser);
    }

    @Test
    void testThatStartServerReturnsASocket() throws IOException {

        Socket clientSocket = socketListener.startServer();

        assertEquals(socket, clientSocket);
        assert(mockServerSocket.acceptHasBeenCalled);

    }

    @Test
    void testReadClientInputCallsPrintWriterPrint() throws IOException {
        String expectedResult = "HTTP/1.1 " + StatusCodes.PAGE_NOT_FOUND.httpResponse + CRLF + "Content-Length: 0" + CRLF + "Content-Type: text/plain" + CRLF + CRLF;

        socketListener.readClientInput(socket);

        assertEquals(expectedResult, mockPrintWriter.printWasCalledWith);
    }



    @Test
    void testKeepListeningReturnsServerSocket() throws IOException {
        Socket clientSocket = socketListener.keepListening();

        assertEquals(socket, clientSocket);
        assert(mockServerSocket.acceptHasBeenCalled);
    }


    @Test
    void testReadClientInputCallsClose() throws IOException {
        socketListener.readClientInput(socket);
        assertTrue(mockPrintWriter.closeWasCalled);

    }

    @Test
    void testSocketListenerCallsRequestParser() throws IOException {
        ClientWriteableFactory mockClientWriterFactory = new MockClientWriterFactory(mockPrintWriter);
        ClientReadableFactory mockClientReaderFactory = new MockClientReaderFactory(mockBufferedReader);
        routes = getRoutes();
        responseBuilder = new HTTPResponseBuilder();
        Router router = new Router(routes, responseBuilder);
        MockRequestParser mockRequestParser = new MockRequestParser();
        SocketListener listener = new SocketListener(mockServerSocket,  mockClientReaderFactory, mockClientWriterFactory, router, mockRequestParser);
        listener.readClientInput(socket);
        assertTrue(mockRequestParser.storeHttpRequestWasCalled);

    }

}
