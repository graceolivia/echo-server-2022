package echoServer;

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
        Socket clientSocket = serverSocket.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public Socket keepListening() throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.err.println("Connected to client.");
        return clientSocket;
    }

    public boolean readClientInput(Socket clientSocket) throws IOException {

        ClientReadable bufferedReader = clientReaderFactory.makeReader(clientSocket);
        ClientWriteable printer = clientWriterFactory.makePrinter(clientSocket);
        String message;
        message = bufferedReader.readAllLines();
        if (!message.equals("")) {
            StatusCode statusCode = StatusCode.PAGE_NOT_FOUND;
            String responseText = statusCode.httpResponse;
            return interpretClientMessage(responseText, printer);
        }
        else return false;
    }

    private boolean interpretClientMessage(String message, ClientWriteable printer) throws IOException {
        if (!message.equals("Connection: close\r\n")) {
            System.out.println(message);
            printer.println(message);
            return true;
        }
        else {
            System.out.println("Shutdown code received: "+message);
            return false;
        }
    }
}
