package httpServer;

import httpServer.http.HTTPRequest;
import httpServer.http.RequestParser;
import httpServer.http.ResponseBuilder;
import httpServer.routes.Router;
import httpServer.http.StatusCodes;
import httpServer.outputManagement.ClientWriteableFactory;
import httpServer.outputManagement.ClientWriteable;
import httpServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;

import static httpServer.http.Constants.CRLF;

public class ListenerAndResponder implements ListenAndRespondable {
    ServerSocketInterface serverSocket;
    ClientReadableFactory clientReaderFactory;
    ClientWriteableFactory clientWriterFactory;
    Router router;

    public ListenerAndResponder(ServerSocketInterface serverSocket, ClientReadableFactory clientReaderFactory, ClientWriteableFactory clientWriterFactory, Router router) throws IOException {
        this.serverSocket = serverSocket;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
        this.router = router;
    }

    public Socket startServer() throws IOException {
        int port = 5000;
        serverSocket.bind(port);
        System.err.println("Started server on port " + port);
        return keepListening();
    }

    public Socket keepListening() throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public void readClientInput(Socket clientSocket) throws IOException {

        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);

        String httpRequest;
        httpRequest = readAllLines(bufferedReader);
        if (httpRequest == null) {
            StatusCodes statusCodes = StatusCodes.BAD_REQUEST;
            String responseText = statusCodes.httpResponse;
            printServerResponse(responseText, clientSocket);
        }
        if (!httpRequest.equals("")) {
            HTTPRequest request = RequestParser.parse(httpRequest);
            System.out.println(httpRequest);
            String httpResponse = router.getResponse(request);
            printServerResponse(httpResponse, clientSocket);
        }

    }

    private void printServerResponse(String message, Socket clientSocket) throws IOException {
            ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
            System.out.println(message);
            printer.println(message);

    }

    private String readAllLines(ClientReadable bufferedReader)  {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append(CRLF);
                if (line.equals("")) { break; }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return stringBuilder.toString();
        }
    }
}
