package httpServer;

import httpServer.http.request.HTTPRequest;
import httpServer.http.response.HTTPResponse;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriteable;
import httpServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;

public class SocketListener implements SocketListenable {
    ServerSocketInterface serverSocket;
    ClientReadableFactory clientReaderFactory;
    ClientWriteableFactory clientWriterFactory;
    Router router;
    RequestParseable requestParser;

    public SocketListener(ServerSocketInterface serverSocket, ClientReadableFactory clientReaderFactory, ClientWriteableFactory clientWriterFactory, Router router, RequestParseable requestParser) throws IOException {
        this.serverSocket = serverSocket;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
        this.router = router;
        this.requestParser = requestParser;
    }

    public Socket startServer() throws IOException {
        int port = 5000;
        serverSocket.bind(port);
        System.err.println("Started server on port " + port);
        return keepListening();
    }

    public Socket keepListening() throws IOException {
        Socket clientSocket = serverSocket.accept();
        return clientSocket;
    }

    public void readClientInput(Socket clientSocket) throws IOException {
        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);
        HTTPRequest httpRequest = requestParser.storeHttpRequest(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {
            HTTPResponse httpResponse = router.getResponse(httpRequest);
            String responseString = httpResponse.toString();
            printServerResponse(responseString, clientSocket);
        }

    }

    private void printServerResponse(String httpResponse, Socket clientSocket) throws IOException {
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        System.out.println(httpResponse);
        printer.print(httpResponse);
        printer.close();
    }

}
