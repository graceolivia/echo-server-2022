package echoServer;

import echoServer.HTTP.HTTPRequest;
import echoServer.HTTP.RequestParser;
import echoServer.HTTP.Router;
import echoServer.http.StatusCode;
import echoServer.outputManagement.ClientWriteableFactory;
import echoServer.outputManagement.ClientWriteable;
import echoServer.socketManagement.ServerSocketInterface;

import java.io.IOException;
import java.net.Socket;

public class Echoer implements Echoable {
    ServerSocketInterface serverSocket;
    ClientReadableFactory clientReaderFactory;
    ClientWriteableFactory clientWriterFactory;

    public Echoer(ServerSocketInterface serverSocket, ClientReadableFactory clientReaderFactory, ClientWriteableFactory clientWriterFactory) throws IOException {
        this.serverSocket = serverSocket;
        this.clientWriterFactory = clientWriterFactory;
        this.clientReaderFactory = clientReaderFactory;
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
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        String httpRequest;
        httpRequest = readAllLines(bufferedReader);
        if (httpRequest == null) {
            StatusCode statusCode = StatusCode.BAD_REQUEST;
            String responseText = statusCode.httpResponse;
            printServerResponse(responseText, printer);
        }
        if (!httpRequest.equals("")) {
            HTTPRequest request = RequestParser.parser(httpRequest);
            System.out.println(request.requestType);
            System.out.println(request.route);
                System.out.println(httpRequest);
            StatusCode statusCode = Router.router(request);
            System.out.println(statusCode.httpResponse);
            String responseText = statusCode.httpResponse;
            printServerResponse(responseText, printer);
        }

    }

    private void printServerResponse(String message, ClientWriteable printer) throws IOException {

            System.out.println(message);
            printer.println(message);

    }

    private String readAllLines(ClientReadable bufferedReader)  {
        String CRLF = "\r\n";
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
